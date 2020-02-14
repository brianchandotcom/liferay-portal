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

package com.liferay.change.tracking.internal.conflict;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.CTRowUtil;
import com.liferay.change.tracking.internal.resolver.ConstraintResolverHelperImpl;
import com.liferay.change.tracking.internal.resolver.ConstraintResolverKey;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.resolver.ConstraintResolver;
import com.liferay.change.tracking.service.CTEntryLocalServiceUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class CTConflictChecker<T extends CTModel<T>> {

	public CTConflictChecker(
		CTService<T> ctService, long modelClassNameId,
		ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
			serviceTrackerMap,
		long sourceCTCollectionId, long targetCTCollectionId) {

		_ctService = ctService;
		_modelClassNameId = modelClassNameId;
		_serviceTrackerMap = serviceTrackerMap;
		_sourceCTCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void addCTEntry(CTEntry ctEntry) {
		if (ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_ignorablePrimaryKeys.add(ctEntry.getModelClassPK());
		}

		if (ctEntry.getChangeType() ==
				CTConstants.CT_CHANGE_TYPE_MODIFICATION) {

			if (_modificationCTEntries == null) {
				_modificationCTEntries = new HashMap<>();
			}

			_modificationCTEntries.put(ctEntry.getModelClassPK(), ctEntry);
		}
	}

	public List<ConflictInfo> check() throws PortalException {
		return _ctService.updateWithUnsafeFunction(this::_check);
	}

	private List<ConflictInfo> _check(CTPersistence<T> ctPersistence)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
			CTColumnResolutionType.PK);

		if (primaryKeyNames.size() != 1) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"{ctPersistence=", ctPersistence, ", primaryKeyNames=",
					primaryKeyNames, "}"));
		}

		Iterator<String> iterator = primaryKeyNames.iterator();

		String primaryKeyName = iterator.next();

		List<ConflictInfo> conflictInfos = new ArrayList<>();

		if (_modificationCTEntries != null) {
			_checkModifications(
				connection, ctPersistence, conflictInfos, primaryKeyName);
		}

		List<String[]> uniqueIndexColumnNames =
			ctPersistence.getUniqueIndexColumnNames();

		if (!uniqueIndexColumnNames.isEmpty()) {
			for (String[] columnNames : uniqueIndexColumnNames) {
				_checkConstraint(
					connection, ctPersistence, conflictInfos, columnNames,
					primaryKeyName);
			}
		}

		return conflictInfos;
	}

	private void _checkConstraint(
			Connection connection, CTPersistence<T> ctPersistence,
			List<ConflictInfo> conflictInfos, String[] columnNames,
			String primaryKeyName)
		throws PortalException {

		String constraintConflictsSQL = CTRowUtil.getConstraintConflictsSQL(
			ctPersistence.getTableName(), primaryKeyName, columnNames,
			_sourceCTCollectionId, _targetCTCollectionId, true);

		List<Map.Entry<Long, Long>> nextPrimaryKeys =
			_getConflictingPrimaryKeys(connection, constraintConflictsSQL);

		if (nextPrimaryKeys.isEmpty()) {
			return;
		}

		ConstraintResolver<T> constraintResolver =
			_serviceTrackerMap.getService(
				new ConstraintResolverKey(
					ctPersistence.getModelClass(), columnNames));

		if (constraintResolver == null) {
			StringBundler sb = new StringBundler(2 * columnNames.length);

			for (String columnName : columnNames) {
				sb.append(columnName);
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			String columnNamesString = sb.toString();

			for (Map.Entry<Long, Long> currentPrimaryKeys : nextPrimaryKeys) {
				conflictInfos.add(
					new DefaultConstraintConflictInfo(
						currentPrimaryKeys.getKey(),
						currentPrimaryKeys.getValue(), columnNamesString));
			}

			return;
		}

		ConstraintResolverHelperImpl<T> constraintResolverHelperImpl =
			new ConstraintResolverHelperImpl<>(
				_ctService, _targetCTCollectionId);

		Set<Map.Entry<Long, Long>> attemptedPrimaryKeys = new HashSet<>();
		Set<Map.Entry<Long, Long>> resolvedPrimaryKeys = new HashSet<>(
			nextPrimaryKeys);

		while (!nextPrimaryKeys.isEmpty()) {
			Map.Entry<Long, Long> currentPrimaryKeys = nextPrimaryKeys.get(0);

			constraintResolverHelperImpl.setPrimaryKeys(
				currentPrimaryKeys.getKey(), currentPrimaryKeys.getValue());

			constraintResolver.resolveConflict(constraintResolverHelperImpl);

			attemptedPrimaryKeys.add(currentPrimaryKeys);

			nextPrimaryKeys = _getConflictingPrimaryKeys(
				connection, constraintConflictsSQL);

			resolvedPrimaryKeys.addAll(nextPrimaryKeys);

			nextPrimaryKeys.removeAll(attemptedPrimaryKeys);
		}

		List<Map.Entry<Long, Long>> unresolvedPrimaryKeys =
			_getConflictingPrimaryKeys(connection, constraintConflictsSQL);

		resolvedPrimaryKeys.removeAll(unresolvedPrimaryKeys);

		for (Map.Entry<Long, Long> currentPrimaryKeys : resolvedPrimaryKeys) {
			conflictInfos.add(
				new ConstraintResolverConflictInfo(
					constraintResolver, currentPrimaryKeys.getKey(),
					currentPrimaryKeys.getValue(), true));
		}

		if (unresolvedPrimaryKeys.isEmpty()) {
			return;
		}

		for (Map.Entry<Long, Long> currentPrimaryKeys : unresolvedPrimaryKeys) {
			conflictInfos.add(
				new ConstraintResolverConflictInfo(
					constraintResolver, currentPrimaryKeys.getKey(),
					currentPrimaryKeys.getValue(), false));
		}
	}

	private void _checkModifications(
		Connection connection, CTPersistence<T> ctPersistence,
		List<ConflictInfo> conflictInfos, String primaryKeyName) {

		StringBundler sb = new StringBundler();

		String tableName = ctPersistence.getTableName();

		sb.append("select t1.");
		sb.append(primaryKeyName);
		sb.append(" as sourcePrimarykey, t2.");
		sb.append(primaryKeyName);
		sb.append(" as targetPrimarykey from ");
		sb.append(tableName);
		sb.append(" t1 inner join ");
		sb.append(tableName);
		sb.append(" t2 on t1.");
		sb.append(primaryKeyName);
		sb.append(" = t2.");
		sb.append(primaryKeyName);
		sb.append(" and t1.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and t2.ctCollectionId = ");
		sb.append(_targetCTCollectionId);

		Map<String, Integer> strictColumnsMap = new HashMap<>(
			ctPersistence.getTableColumnsMap());

		Set<String> strictColumnNames = strictColumnsMap.keySet();

		strictColumnNames.retainAll(
			ctPersistence.getCTColumnNames(CTColumnResolutionType.STRICT));

		Collection<Integer> strictColumnTypes = strictColumnsMap.values();

		if (!strictColumnTypes.contains(Types.BLOB)) {
			sb.append(" and (");

			for (Map.Entry<String, Integer> entry :
					strictColumnsMap.entrySet()) {

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
		sb.append(primaryKeyName);
		sb.append(" and ctEntry.changeType = ");
		sb.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		sb.append(" and ctEntry.modelMvccVersion != t2.mvccVersion");

		List<ConflictInfo> modificationConflictInfos = new ArrayList<>();

		Map<Serializable, CTEntry> updateMvccCTEntries = new HashMap<>();

		updateMvccCTEntries.putAll(_modificationCTEntries);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long sourcePK = resultSet.getLong(1);
				long targetPK = resultSet.getLong(2);

				modificationConflictInfos.add(
					new StrictModificationConflictInfo(sourcePK, targetPK));

				updateMvccCTEntries.remove(sourcePK);
			}
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		conflictInfos.addAll(modificationConflictInfos);

		Map<String, Integer> tableColumnsMap =
			ctPersistence.getTableColumnsMap();

		sb.setIndex(0);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" t1 inner join ");
		sb.append(tableName);
		sb.append(" t2 inner join CTEntry ctEntry on ");
		sb.append("ctEntry.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and ctEntry.modelClassNameId = ");
		sb.append(_modelClassNameId);
		sb.append(" and ctEntry.modelClassPK = t1.");
		sb.append(primaryKeyName);
		sb.append(" and ctEntry.changeType = ");
		sb.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		sb.append(" set ");

		Set<String> ignoredColumnNames = ctPersistence.getCTColumnNames(
			CTColumnResolutionType.IGNORE);

		for (String name : tableColumnsMap.keySet()) {
			sb.append("t1.");
			sb.append(name);
			sb.append(" = ");

			if (name.equals("mvccVersion")) {
				sb.append("(t1.mvccVersion + 1)");
			}
			else if (ignoredColumnNames.contains(name)) {
				sb.append("t2.");
				sb.append(name);
			}
			else {
				sb.append("t1.");
				sb.append(name);
			}

			sb.append(", ");
		}

		sb.setStringAt(" where t1.", sb.index() - 1);

		sb.append(primaryKeyName);
		sb.append(" = t2.");
		sb.append(primaryKeyName);
		sb.append(" and t1.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and t2.ctCollectionId = ");
		sb.append(_targetCTCollectionId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		if (!updateMvccCTEntries.isEmpty()) {
			List<CTEntry> updatedCTEntries = _updateModelMvccVersion(
				connection, tableName, primaryKeyName, updateMvccCTEntries,
				_targetCTCollectionId);

			for (CTEntry ctEntry : updatedCTEntries) {
				conflictInfos.add(
					new ResolvedModificationConflictInfo(
						ctEntry.getModelClassPK(), ctEntry.getModelClassPK()));
			}
		}
	}

	private List<Map.Entry<Long, Long>> _getConflictingPrimaryKeys(
		Connection connection, String constraintConflictsSQL) {

		List<Map.Entry<Long, Long>> primaryKeys = null;

		try (PreparedStatement ps = connection.prepareStatement(
				constraintConflictsSQL);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long sourcePK = rs.getLong(1);
				long targetPK = rs.getLong(2);

				if (_ignorablePrimaryKeys.contains(sourcePK) ||
					_ignorablePrimaryKeys.contains(targetPK)) {

					continue;
				}

				if (primaryKeys == null) {
					primaryKeys = new ArrayList<>();
				}

				primaryKeys.add(
					new AbstractMap.SimpleImmutableEntry<>(sourcePK, targetPK));
			}

			if (primaryKeys == null) {
				primaryKeys = Collections.emptyList();
			}

			return primaryKeys;
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}
	}

	private List<CTEntry> _updateModelMvccVersion(
		Connection connection, String tableName, String primaryKeyName,
		Map<Serializable, CTEntry> ctEntries, long ctCollectionId) {

		StringBundler sb = new StringBundler(2 * ctEntries.size() + 9);

		sb.append("select t1.");
		sb.append(primaryKeyName);
		sb.append(", t1.mvccVersion from ");
		sb.append(tableName);
		sb.append(" t1 inner join CTEntry t2 on t2.modelClassNameId = ");
		sb.append(_modelClassNameId);
		sb.append(" and t1.");
		sb.append(primaryKeyName);
		sb.append(" = t2.modelClassPK and t1.mvccVersion != t2.mvccVersion");
		sb.append(" where t1.ctCollectionId = ");
		sb.append(ctCollectionId);
		sb.append(" and ");
		sb.append(primaryKeyName);
		sb.append(" in (");

		for (Serializable serializable : ctEntries.keySet()) {
			sb.append(serializable);
			sb.append(", ");
		}

		sb.setStringAt(")", sb.index() - 1);

		List<CTEntry> updatedCTEntries = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long pk = resultSet.getLong(1);
				long mvccVersion = resultSet.getLong(2);

				CTEntry ctEntry = ctEntries.get(pk);

				ctEntry.setModifiedDate(ctEntry.getModifiedDate());

				ctEntry.setModelMvccVersion(mvccVersion);

				CTEntryLocalServiceUtil.updateCTEntry(ctEntry);

				updatedCTEntries.add(ctEntry);
			}
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		return updatedCTEntries;
	}

	private final CTService<T> _ctService;
	private final Set<Long> _ignorablePrimaryKeys = new HashSet<>();
	private final long _modelClassNameId;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
		_serviceTrackerMap;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}