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
import com.liferay.change.tracking.internal.CTRowUtil;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

	private int _getPreDeletedRowCount(
			Connection connection, String tableName, String primaryKeyName)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from CTEntry left join ", tableName,
					" on CTEntry.modelClassPK = ", tableName, ".",
					primaryKeyName, " and ", tableName, ".ctCollectionId = ",
					_targetCTCollectionId, " where CTEntry.changeType = ",
					CTConstants.CT_CHANGE_TYPE_DELETION,
					" and CTEntry.ctCollectionId = ", _sourceCTCollectionId,
					" and CTEntry.modelClassNameId = ", _modelClassNameId,
					" and ", tableName, ".", primaryKeyName, " is null"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		return 0;
	}

	private Void _publish(CTPersistence<T> ctPersistence) throws Exception {
		String tableName = ctPersistence.getTableName();

		Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
			CTColumnResolutionType.PK);

		if (primaryKeyNames.size() != 1) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"{primaryKeyNames=", primaryKeyNames, ", tableName=",
					tableName, "}"));
		}

		Iterator<String> iterator = primaryKeyNames.iterator();

		String primaryKeyName = iterator.next();

		// Order matters to avoid causing constraint violations

		long tempCTCollectionId = -_sourceCTCollectionId;

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		if (_additionCTEntries != null) {
			_updateCTCollectionId(
				connection, tableName, primaryKeyName,
				_additionCTEntries.values(), _sourceCTCollectionId,
				tempCTCollectionId, false, false);
		}

		if (_modificationCTEntries != null) {
			_updateCTCollectionId(
				connection, tableName, primaryKeyName,
				_modificationCTEntries.values(), _sourceCTCollectionId,
				tempCTCollectionId, false, true);
		}

		if (_deletionCTEntries != null) {
			int updatedRowCount = _updateCTCollectionId(
				connection, tableName, primaryKeyName,
				_deletionCTEntries.values(), _targetCTCollectionId,
				_sourceCTCollectionId, true, false);

			if (updatedRowCount != _deletionCTEntries.size()) {
				int preDeletedRowCount = _getPreDeletedRowCount(
					connection, tableName, primaryKeyName);

				if ((updatedRowCount + preDeletedRowCount) !=
						_deletionCTEntries.size()) {

					throw new SystemException(
						StringBundler.concat(
							"Size mismatch expected ",
							_deletionCTEntries.size(), " but was ",
							updatedRowCount));
				}
			}

			_updateModelMvccVersion(
				connection, tableName, primaryKeyName, _deletionCTEntries,
				_sourceCTCollectionId);
		}

		if (_modificationCTEntries != null) {
			_updateCTCollectionId(
				connection, tableName, primaryKeyName,
				_modificationCTEntries.values(), _targetCTCollectionId,
				_sourceCTCollectionId, true, true);
		}

		if (_additionCTEntries != null) {
			_updateCTCollectionId(
				connection, tableName, primaryKeyName,
				_additionCTEntries.values(), tempCTCollectionId,
				_targetCTCollectionId, false, false);

			_updateModelMvccVersion(
				connection, tableName, primaryKeyName, _additionCTEntries,
				_targetCTCollectionId);
		}

		if (_modificationCTEntries != null) {
			StringBundler sb = new StringBundler();

			Map<String, Integer> tableColumnsMap =
				ctPersistence.getTableColumnsMap();

			sb.append("select ");

			for (String name : tableColumnsMap.keySet()) {
				if (name.equals("ctCollectionId")) {
					sb.append(_targetCTCollectionId);
					sb.append(" as ");
				}
				else if (name.equals("mvccVersion")) {
					sb.append("(t1.mvccVersion + 1) ");
				}
				else {
					sb.append("t1.");
				}

				sb.append(name);
				sb.append(", ");
			}

			sb.setStringAt(" from ", sb.index() - 1);

			sb.append(tableName);
			sb.append(" t1, ");
			sb.append(tableName);
			sb.append(" t2 where t1.");
			sb.append(primaryKeyName);
			sb.append(" = t2.");
			sb.append(primaryKeyName);
			sb.append(" and t1.ctCollectionId = ");
			sb.append(tempCTCollectionId);
			sb.append(" and t2.ctCollectionId = ");
			sb.append(_sourceCTCollectionId);

			CTRowUtil.copyCTRows(ctPersistence, connection, sb.toString());

			sb.setIndex(0);

			sb.append("delete from ");
			sb.append(tableName);
			sb.append(" where ctCollectionId = ");
			sb.append(tempCTCollectionId);

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sb.toString())) {

				preparedStatement.executeUpdate();
			}

			_updateModelMvccVersion(
				connection, tableName, primaryKeyName, _modificationCTEntries,
				_targetCTCollectionId);
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
			Connection connection, String tableName, String primaryKeyName,
			Collection<CTEntry> ctEntries, long fromCTCollectionId,
			long toCTCollectionId, boolean includeMvccVersion,
			boolean checkRowCount)
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
				sb.append(primaryKeyName);
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
			sb.append(primaryKeyName);
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
			Connection connection, String tableName, String primaryKeyName,
			Map<Serializable, CTEntry> ctEntries, long ctCollectionId)
		throws Exception {

		StringBundler sb = new StringBundler((2 * ctEntries.size()) + 9);

		sb.append("select ");
		sb.append(primaryKeyName);
		sb.append(", mvccVersion from ");
		sb.append(tableName);
		sb.append(" where ctCollectionId = ");
		sb.append(ctCollectionId);
		sb.append(" and ");
		sb.append(primaryKeyName);
		sb.append(" in (");

		for (Serializable serializable : ctEntries.keySet()) {
			sb.append(serializable);
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

				ctEntry.setModifiedDate(ctEntry.getModifiedDate());
				ctEntry.setModelMvccVersion(mvccVersion);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}
	}

	private Map<Serializable, CTEntry> _additionCTEntries;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private Map<Serializable, CTEntry> _deletionCTEntries;
	private final long _modelClassNameId;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}