/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class CTServicePublisher<T extends CTModel<T>> {

	public CTServicePublisher(
		CTEntryLocalService ctEntryLocalService, CTService<T> ctService,
		long modelClassNameId, long sourceCTCollectionId,
		long targetCTCollectionId) {

		_ctEntryLocalService = ctEntryLocalService;
		_ctService = ctService;
		_modelClassNameId = modelClassNameId;
		_sourceCTCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void addCTEntry(CTEntry ctEntry) {
		long modelClassPK = ctEntry.getModelClassPK();
		int changeType = ctEntry.getChangeType();

		if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
			if (_additionCTEntries == null) {
				_additionCTEntries = new HashMap<>();
			}

			_additionCTEntries.put(modelClassPK, ctEntry);
		}
		else if (changeType == CTConstants.CT_CHANGE_TYPE_DELETION) {
			if (_deletionCTEntries == null) {
				_deletionCTEntries = new HashMap<>();
			}

			_deletionCTEntries.put(modelClassPK, ctEntry);
		}
		else {
			if (_modificationCTEntries == null) {
				_modificationCTEntries = new HashMap<>();
			}

			_modificationCTEntries.put(modelClassPK, ctEntry);
		}
	}

	public void publish() throws Exception {
		_ctService.updateWithUnsafeFunction(this::_publish);
	}

	private Void _publish(CTPersistence<T> ctPersistence) throws Exception {
		CTModelRegistration ctModelRegistration =
			CTModelRegistry.getCTModelRegistration(
				ctPersistence.getModelClass());

		if (ctModelRegistration == null) {
			throw new IllegalStateException(
				"Unable find CTModelRegistration for " +
					_ctService.getModelClass());
		}

		// Order matters to avoid causing constraint violations

		long tempCTCollectionId = -_sourceCTCollectionId;

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		if (_additionCTEntries != null) {
			_updateCTCollectionId(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _additionCTEntries.values(), _sourceCTCollectionId,
				tempCTCollectionId, false, false);
		}

		if (_modificationCTEntries != null) {
			_updateCTCollectionId(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _modificationCTEntries.values(),
				_sourceCTCollectionId, tempCTCollectionId, false, true);
		}

		if (_deletionCTEntries != null) {
			_updateCTCollectionId(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _deletionCTEntries.values(), _targetCTCollectionId,
				_sourceCTCollectionId, true, true);

			_updateModelMvccVersion(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _deletionCTEntries, _sourceCTCollectionId);
		}

		if (_modificationCTEntries != null) {
			int rowCount = _updateCTCollectionId(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _modificationCTEntries.values(),
				_targetCTCollectionId, _sourceCTCollectionId, true, false);

			if (rowCount != _modificationCTEntries.size()) {
				Map<String, Integer> conflictColumnsMap = new HashMap<>(
					ctModelRegistration.getTableColumnsMap());

				Set<String> conflictColumnNames = conflictColumnsMap.keySet();

				conflictColumnNames.remove(
					ctModelRegistration.getPrimaryColumnName());
				conflictColumnNames.removeAll(_ctControlColumnNames);
				conflictColumnNames.removeAll(
					ctPersistence.getCTIgnoredAttributeNames());
				conflictColumnNames.removeAll(
					ctPersistence.getCTMergeableAttributeNames());

				boolean hasBlobConflictColumn = false;

				for (Map.Entry<String, Integer> entry :
						conflictColumnsMap.entrySet()) {

					if (entry.getValue() == Types.BLOB) {
						hasBlobConflictColumn = true;

						break;
					}
				}

				StringBundler sb = new StringBundler();

				sb.append("select t1.");
				sb.append(ctModelRegistration.getPrimaryColumnName());
				sb.append(" from ");
				sb.append(ctModelRegistration.getTableName());
				sb.append(" t1 inner join ");
				sb.append(ctModelRegistration.getTableName());
				sb.append(" t2 on t1.");
				sb.append(ctModelRegistration.getPrimaryColumnName());
				sb.append(" = t2.");
				sb.append(ctModelRegistration.getPrimaryColumnName());
				sb.append(" and t1.ctCollectionId = ");
				sb.append(tempCTCollectionId);
				sb.append(" and t2.ctCollectionId = ");
				sb.append(_targetCTCollectionId);

				if (!hasBlobConflictColumn) {
					sb.append(" and (");

					for (Map.Entry<String, Integer> entry :
							conflictColumnsMap.entrySet()) {

						String conflictColumnName = entry.getKey();

						if (entry.getValue() == Types.CLOB) {
							sb.append("CAST_CLOB_TEXT(t1.");
							sb.append(conflictColumnName);
							sb.append(") != CAST_CLOB_TEXT(t2.");
							sb.append(conflictColumnName);
							sb.append(")");
						}
						else {
							sb.append("t1.");
							sb.append(conflictColumnName);
							sb.append(" != t2.");
							sb.append(conflictColumnName);
						}

						sb.append(" or ");
					}

					sb.setStringAt(")", sb.index() - 1);
				}

				sb.append(" inner join CTEntry ctEntry on ");
				sb.append("ctEntry.ctCollectionId = ");
				sb.append(_sourceCTCollectionId);
				sb.append(" and ctEntry.modelClassNameId = ");
				sb.append(_modelClassNameId);
				sb.append(" and ctEntry.modelClassPK = t2.");
				sb.append(ctModelRegistration.getPrimaryColumnName());
				sb.append(" and ctEntry.changeType = ");
				sb.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);
				sb.append(" and ctEntry.modelMvccVersion != t2.mvccVersion");

				List<Long> conflictPrimaryKeys = new ArrayList<>();

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							SQLTransformer.transform(sb.toString()));
					ResultSet resultSet = preparedStatement.executeQuery()) {

					while (resultSet.next()) {
						conflictPrimaryKeys.add(resultSet.getLong(1));
					}
				}

				if (!conflictPrimaryKeys.isEmpty()) {
					conflictPrimaryKeys.sort(null);

					throw new SystemException(
						StringBundler.concat(
							"Unable to auto resolve publication conflict for ",
							_ctService.getModelClass(), " with primary keys ",
							conflictPrimaryKeys));
				}

				rowCount += _updateCTCollectionId(
					ctModelRegistration.getTableName(), ctModelRegistration,
					connection, _modificationCTEntries.values(),
					_targetCTCollectionId, _sourceCTCollectionId, false, false);

				if (rowCount != _modificationCTEntries.size()) {
					throw new SystemException(
						StringBundler.concat(
							"Size mismatch expected ",
							_modificationCTEntries.size(), " but was ",
							rowCount));
				}
			}

			_updateModelMvccVersion(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _modificationCTEntries, _sourceCTCollectionId);
		}

		if (_additionCTEntries != null) {
			_updateCTCollectionId(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _additionCTEntries.values(), tempCTCollectionId,
				_targetCTCollectionId, false, false);

			_updateModelMvccVersion(
				ctModelRegistration.getTableName(), ctModelRegistration,
				connection, _additionCTEntries, _targetCTCollectionId);
		}

		if (_modificationCTEntries != null) {
			StringBundler sb = new StringBundler();

			sb.append("insert into ");
			sb.append(ctModelRegistration.getTableName());
			sb.append(" (");

			Map<String, Integer> tableColumnsMap =
				ctModelRegistration.getTableColumnsMap();

			for (String name : tableColumnsMap.keySet()) {
				sb.append(name);
				sb.append(", ");
			}

			sb.setStringAt(") select ", sb.index() - 1);

			Set<String> ignoredAttributeNames =
				ctPersistence.getCTIgnoredAttributeNames();

			for (String name : tableColumnsMap.keySet()) {
				if (name.equals("ctCollectionId")) {
					sb.append(_targetCTCollectionId);
					sb.append(" as ");
				}
				else if (ignoredAttributeNames.contains(name)) {
					sb.append("t2.");
				}
				else {
					sb.append("t1.");
				}

				sb.append(name);
				sb.append(", ");
			}

			sb.setStringAt(" from ", sb.index() - 1);

			sb.append(ctModelRegistration.getTableName());
			sb.append(" t1, ");
			sb.append(ctModelRegistration.getTableName());
			sb.append(" t2 where t1.");
			sb.append(ctModelRegistration.getPrimaryColumnName());
			sb.append(" = t2.");
			sb.append(ctModelRegistration.getPrimaryColumnName());
			sb.append(" and t1.ctCollectionId = ");
			sb.append(tempCTCollectionId);
			sb.append(" and t2.ctCollectionId = ");
			sb.append(_sourceCTCollectionId);

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sb.toString())) {

				preparedStatement.executeUpdate();
			}

			sb.setIndex(0);

			sb.append("delete from ");
			sb.append(ctModelRegistration.getTableName());
			sb.append(" where ctCollectionId = ");
			sb.append(tempCTCollectionId);

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sb.toString())) {

				preparedStatement.executeUpdate();
			}
		}

		if (_additionCTEntries != null) {
			ctPersistence.clearCache(_additionCTEntries.keySet());
		}

		if (_deletionCTEntries != null) {
			ctPersistence.clearCache(_deletionCTEntries.keySet());
		}

		if (_modificationCTEntries != null) {
			ctPersistence.clearCache(_modificationCTEntries.keySet());
		}

		return null;
	}

	private int _updateCTCollectionId(
			String tableName, CTModelRegistration ctModelRegistration,
			Connection connection, Collection<CTEntry> ctEntries,
			long fromCTCollectionId, long toCTCollectionId,
			boolean includeMvccVersion, boolean checkRowCount)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ctCollectionId = ");
		sb.append(toCTCollectionId);
		sb.append(" where ");
		sb.append(tableName);
		sb.append(".ctCollectionId = ");
		sb.append(fromCTCollectionId);
		sb.append(" and ");

		if (includeMvccVersion) {
			sb.append("(");

			for (CTEntry ctEntry : ctEntries) {
				sb.append("(");
				sb.append(tableName);
				sb.append(".");
				sb.append(ctModelRegistration.getPrimaryColumnName());
				sb.append(" = ");
				sb.append(ctEntry.getModelClassPK());
				sb.append(" and ");
				sb.append(tableName);
				sb.append(".mvccVersion = ");
				sb.append(ctEntry.getModelMvccVersion());
				sb.append(")");
				sb.append(" or ");
			}

			sb.setStringAt(")", sb.index() - 1);
		}
		else {
			sb.append(tableName);
			sb.append(".");
			sb.append(ctModelRegistration.getPrimaryColumnName());
			sb.append(" in (");

			for (CTEntry ctEntry : ctEntries) {
				sb.append(ctEntry.getModelClassPK());
				sb.append(", ");
			}

			sb.setStringAt(")", sb.index() - 1);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			int rowCount = preparedStatement.executeUpdate();

			if (checkRowCount && (rowCount != ctEntries.size())) {
				throw new SystemException(
					StringBundler.concat(
						"Size mismatch expected ", ctEntries.size(),
						" but was ", rowCount));
			}

			return rowCount;
		}
	}

	private void _updateModelMvccVersion(
			String tableName, CTModelRegistration ctModelRegistration,
			Connection connection, Map<Serializable, CTEntry> ctEntries,
			long ctCollectionId)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("select ");
		sb.append(ctModelRegistration.getPrimaryColumnName());
		sb.append(", mvccVersion from ");
		sb.append(tableName);
		sb.append(" where ctCollectionId = ");
		sb.append(ctCollectionId);
		sb.append(" and ");
		sb.append(ctModelRegistration.getPrimaryColumnName());
		sb.append(" in (");

		for (Serializable serializable : ctEntries.keySet()) {
			sb.append((long)serializable);
			sb.append(", ");
		}

		sb.setStringAt(")", sb.index() - 1);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long pk = resultSet.getLong(1);
				long mvccVersion = resultSet.getLong(2);

				CTEntry ctEntry = ctEntries.get(pk);

				ctEntry.setModelMvccVersion(mvccVersion);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}
	}

	private static final Set<String> _ctControlColumnNames = new HashSet<>(
		Arrays.asList("ctCollectionId", "mvccVersion"));

	private Map<Serializable, CTEntry> _additionCTEntries;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private Map<Serializable, CTEntry> _deletionCTEntries;
	private final long _modelClassNameId;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}