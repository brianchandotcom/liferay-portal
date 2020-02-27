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
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
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
		CTEntryLocalService ctEntryLocalService, CTService<T> ctService,
		long modelClassNameId,
		ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
			serviceTrackerMap,
		long sourceCTCollectionId, long targetCTCollectionId) {

		_ctEntryLocalService = ctEntryLocalService;
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

	private boolean _appendConflictsWhereClauseSQL(
		CTColumnResolutionType ctColumnResolutionType,
		CTPersistence<T> ctPersistence, StringBundler sb) {

		Set<String> conflictingColumnNames = ctPersistence.getCTColumnNames(
			ctColumnResolutionType);

		if (conflictingColumnNames.isEmpty()) {
			return false;
		}

		Map<String, Integer> columnsMap = new HashMap<>(
			ctPersistence.getTableColumnsMap());

		Set<String> columnNames = columnsMap.keySet();

		columnNames.retainAll(conflictingColumnNames);

		Collection<Integer> columnTypes = columnsMap.values();

		if ((ctColumnResolutionType == CTColumnResolutionType.STRICT) &&
			columnTypes.contains(Types.BLOB)) {

			return true;
		}

		sb.append(" where ");

		for (Map.Entry<String, Integer> entry : columnsMap.entrySet()) {
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

		sb.setIndex(sb.index() - 1);

		return true;
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

		StringBundler sb1 = new StringBundler();

		String tableName = ctPersistence.getTableName();

		sb1.append("select t1.");
		sb1.append(primaryKeyName);
		sb1.append(" from ");
		sb1.append(tableName);
		sb1.append(" t1 inner join ");
		sb1.append(tableName);
		sb1.append(" t2 on t1.");
		sb1.append(primaryKeyName);
		sb1.append(" = t2.");
		sb1.append(primaryKeyName);
		sb1.append(" and t1.ctCollectionId = ");
		sb1.append(_sourceCTCollectionId);
		sb1.append(" and t2.ctCollectionId = ");
		sb1.append(_targetCTCollectionId);
		sb1.append(" inner join CTEntry ctEntry on ctEntry.ctCollectionId = ");
		sb1.append(_sourceCTCollectionId);
		sb1.append(" and ctEntry.modelClassNameId = ");
		sb1.append(_modelClassNameId);
		sb1.append(" and ctEntry.modelClassPK = t2.");
		sb1.append(primaryKeyName);
		sb1.append(" and ctEntry.changeType = ");
		sb1.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		String selectSQL = sb1.toString();

		if (_appendConflictsWhereClauseSQL(
				CTColumnResolutionType.IGNORE, ctPersistence, sb1)) {

			List<Serializable> resolvedPrimaryKeys = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						SQLTransformer.transform(sb1.toString()));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long primaryKey = resultSet.getLong(1);

					conflictInfos.add(
						new ModificationConflictInfo(primaryKey, true));

					resolvedPrimaryKeys.add(primaryKey);
				}
			}
			catch (SQLException sqlException) {
				throw new ORMException(sqlException);
			}

			_resolveModificationConflicts(
				connection, ctPersistence, primaryKeyName, resolvedPrimaryKeys,
				tableName);
		}

		StringBundler sb2 = new StringBundler();

		sb2.append(selectSQL);
		sb2.append(" and ctEntry.modelMvccVersion != t2.mvccVersion");

		List<Serializable> unresolvedPrimaryKeys = new ArrayList<>();

		if (_appendConflictsWhereClauseSQL(
				CTColumnResolutionType.STRICT, ctPersistence, sb2)) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						SQLTransformer.transform(sb2.toString()));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long primaryKey = resultSet.getLong(1);

					conflictInfos.add(
						new ModificationConflictInfo(primaryKey, false));

					unresolvedPrimaryKeys.add(primaryKey);
				}
			}
			catch (SQLException sqlException) {
				throw new ORMException(sqlException);
			}
		}

		_updateModelMvccVersion(
			connection, primaryKeyName, tableName, unresolvedPrimaryKeys);
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
			throw new ORMException(sqlException);
		}
	}

	private void _resolveModificationConflicts(
		Connection connection, CTPersistence<T> ctPersistence,
		String primaryKeyName, List<Serializable> resolvedPrimaryKeys,
		String tableName) {

		if (resolvedPrimaryKeys.isEmpty()) {
			return;
		}

		long tempCTCollectionId = -_sourceCTCollectionId;

		StringBundler sb1 = new StringBundler();

		sb1.append("update ");
		sb1.append(tableName);
		sb1.append(" t1 set t1.ctCollectionId = ");
		sb1.append(tempCTCollectionId);
		sb1.append(" where t1.ctCollectionId = ");
		sb1.append(_sourceCTCollectionId);
		sb1.append(" and t1.");
		sb1.append(primaryKeyName);
		sb1.append(" in (");

		for (Serializable primaryKey : resolvedPrimaryKeys) {
			sb1.append(primaryKey);
			sb1.append(", ");
		}

		sb1.setStringAt(")", sb1.index() - 1);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb1.toString())) {

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}

		StringBundler sb2 = new StringBundler();

		Map<String, Integer> tableColumnsMap =
			ctPersistence.getTableColumnsMap();

		sb2.append("select ");

		Set<String> ignoredColumnNames = ctPersistence.getCTColumnNames(
			CTColumnResolutionType.IGNORE);

		for (String name : tableColumnsMap.keySet()) {
			if (name.equals("ctCollectionId")) {
				sb2.append(_sourceCTCollectionId);
				sb2.append(" as ");
			}
			else if (name.equals("mvccVersion")) {
				sb2.append("(t1.mvccVersion + 1) ");
			}
			else if (ignoredColumnNames.contains(name)) {
				sb2.append("t2.");
			}
			else {
				sb2.append("t1.");
			}

			sb2.append(name);
			sb2.append(", ");
		}

		sb2.setStringAt(" from ", sb2.index() - 1);

		sb2.append(tableName);
		sb2.append(" t1, ");
		sb2.append(tableName);
		sb2.append(" t2 where t1.");
		sb2.append(primaryKeyName);
		sb2.append(" = t2.");
		sb2.append(primaryKeyName);
		sb2.append(" and t1.ctCollectionId = ");
		sb2.append(tempCTCollectionId);
		sb2.append(" and t2.ctCollectionId = ");
		sb2.append(_targetCTCollectionId);

		try {
			CTRowUtil.copyCTRows(ctPersistence, connection, sb2.toString());
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}

		StringBundler sb3 = new StringBundler(4);

		sb3.append("delete from ");
		sb3.append(tableName);
		sb3.append(" where ctCollectionId = ");
		sb3.append(tempCTCollectionId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb3.toString())) {

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private void _updateModelMvccVersion(
		Connection connection, String primaryKeyName, String tableName,
		List<Serializable> unresolvedPrimaryKeys) {

		StringBundler sb = new StringBundler();

		sb.append("select t1.");
		sb.append(primaryKeyName);
		sb.append(", t1.mvccVersion from ");
		sb.append(tableName);
		sb.append(" t1 inner join CTEntry t2 on t1.");
		sb.append(primaryKeyName);
		sb.append(" = t2.modelClassPK and t2.changeType = ");
		sb.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		sb.append(" and t2.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and t2.modelClassNameId = ");
		sb.append(_modelClassNameId);
		sb.append(" and t1.mvccVersion != t2.modelMvccVersion");
		sb.append(" where t1.ctCollectionId = ");
		sb.append(_targetCTCollectionId);

		if (!unresolvedPrimaryKeys.isEmpty()) {
			sb.append(" and ");
			sb.append(primaryKeyName);
			sb.append(" not in (");

			for (Serializable serializable : unresolvedPrimaryKeys) {
				sb.append(serializable);
				sb.append(", ");
			}

			sb.setStringAt(")", sb.index() - 1);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long pk = resultSet.getLong(1);
				long mvccVersion = resultSet.getLong(2);

				CTEntry ctEntry = _modificationCTEntries.get(pk);

				ctEntry.setModifiedDate(ctEntry.getModifiedDate());

				ctEntry.setModelMvccVersion(mvccVersion);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private final Set<Long> _ignorablePrimaryKeys = new HashSet<>();
	private final long _modelClassNameId;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
		_serviceTrackerMap;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}