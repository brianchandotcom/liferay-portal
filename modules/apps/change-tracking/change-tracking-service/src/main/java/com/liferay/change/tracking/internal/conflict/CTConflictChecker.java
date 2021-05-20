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
import com.liferay.change.tracking.internal.reference.TableJoinHolder;
import com.liferay.change.tracking.internal.reference.TableReferenceDefinitionManager;
import com.liferay.change.tracking.internal.reference.TableReferenceInfo;
import com.liferay.change.tracking.internal.resolver.ConstraintResolverContextImpl;
import com.liferay.change.tracking.internal.resolver.ConstraintResolverKey;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.WhereStep;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
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
		ClassNameLocalService classNameLocalService,
		ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver<?>>
			constraintResolverServiceTrackerMap,
		ServiceTrackerMap<String, CTDisplayRenderer<?>>
			ctDisplayRendererServiceTrackerMap,
		CTEntryLocalService ctEntryLocalService, CTService<T> ctService,
		long modelClassNameId, long sourceCTCollectionId,
		TableReferenceDefinitionManager tableReferenceDefinitionManager,
		long targetCTCollectionId) {

		_classNameLocalService = classNameLocalService;
		_constraintResolverServiceTrackerMap =
			constraintResolverServiceTrackerMap;
		_ctDisplayRendererServiceTrackerMap =
			ctDisplayRendererServiceTrackerMap;
		_ctEntryLocalService = ctEntryLocalService;
		_ctService = ctService;
		_modelClassNameId = modelClassNameId;
		_sourceCTCollectionId = sourceCTCollectionId;
		_tableReferenceDefinitionManager = tableReferenceDefinitionManager;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void addCTEntry(CTEntry ctEntry) {
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

		_checkAdditions(
			connection, ctPersistence, conflictInfos, primaryKeyName);

		if (_modificationCTEntries != null) {
			_checkModifications(
				connection, ctPersistence, conflictInfos, primaryKeyName);
		}

		List<String[]> uniqueIndexColumnNames =
			ctPersistence.getUniqueIndexColumnNames();

		if (!uniqueIndexColumnNames.isEmpty()) {
			for (String[] columnNames : uniqueIndexColumnNames) {
				_checkConstraint(
					connection, ctPersistence, conflictInfos, primaryKeyName,
					columnNames);
			}
		}

		_checkMissingRequirements(connection, ctPersistence, conflictInfos);

		return conflictInfos;
	}

	private void _checkAdditions(
		Connection connection, CTPersistence<T> ctPersistence,
		List<ConflictInfo> conflictInfos, String primaryKeyName) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", ctPersistence.getTableName(), ".",
					primaryKeyName, " from ", ctPersistence.getTableName(),
					" inner join CTEntry on CTEntry.modelClassPK = ",
					ctPersistence.getTableName(), ".", primaryKeyName,
					" where CTEntry.ctCollectionId = ", _sourceCTCollectionId,
					" and CTEntry.modelClassNameId = ", _modelClassNameId,
					" and CTEntry.changeType = ",
					CTConstants.CT_CHANGE_TYPE_ADDITION, " and ",
					ctPersistence.getTableName(), ".ctCollectionId = ",
					_targetCTCollectionId));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				conflictInfos.add(
					new AdditionConflictInfo(resultSet.getLong(1)));
			}
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private void _checkConstraint(
			Connection connection, CTPersistence<T> ctPersistence,
			List<ConflictInfo> conflictInfos, String primaryKeyName,
			String[] columnNames)
		throws PortalException {

		String constraintConflictsSQL = CTRowUtil.getConstraintConflictsSQL(
			ctPersistence.getTableName(), primaryKeyName, columnNames,
			_sourceCTCollectionId, _targetCTCollectionId);

		List<Map.Entry<Long, Long>> nextPrimaryKeys =
			_getConflictingPrimaryKeys(connection, constraintConflictsSQL);

		if (nextPrimaryKeys.isEmpty()) {
			return;
		}

		ConstraintResolver<T> constraintResolver =
			(ConstraintResolver<T>)
				_constraintResolverServiceTrackerMap.getService(
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

		ConstraintResolverContextImpl<T> constraintResolverContextImpl =
			new ConstraintResolverContextImpl<>(
				_ctService, _sourceCTCollectionId, _targetCTCollectionId);

		Set<Map.Entry<Long, Long>> attemptedPrimaryKeys = new HashSet<>();
		Set<Map.Entry<Long, Long>> resolvedPrimaryKeys = new HashSet<>(
			nextPrimaryKeys);

		while (!nextPrimaryKeys.isEmpty()) {
			Map.Entry<Long, Long> currentPrimaryKeys = nextPrimaryKeys.get(0);

			constraintResolverContextImpl.setPrimaryKeys(
				currentPrimaryKeys.getKey(), currentPrimaryKeys.getValue());

			constraintResolver.resolveConflict(constraintResolverContextImpl);

			Session session = ctPersistence.getCurrentSession();

			session.flush();

			session.clear();

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
					constraintResolver, true, currentPrimaryKeys.getKey(),
					currentPrimaryKeys.getValue()));
		}

		if (unresolvedPrimaryKeys.isEmpty()) {
			return;
		}

		for (Map.Entry<Long, Long> currentPrimaryKeys : unresolvedPrimaryKeys) {
			conflictInfos.add(
				new ConstraintResolverConflictInfo(
					constraintResolver, false, currentPrimaryKeys.getKey(),
					currentPrimaryKeys.getValue()));
		}
	}

	private void _checkMissingRequirements(
			Connection connection, CTPersistence<T> ctPersistence,
			List<ConflictInfo> conflictInfos)
		throws PortalException {

		Set<Long> primaryKeys = new HashSet<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					_sourceCTCollectionId, _modelClassNameId)) {

			if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

				primaryKeys.add(ctEntry.getModelClassPK());
			}
		}

		if (primaryKeys.isEmpty()) {
			return;
		}

		Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos =
			_tableReferenceDefinitionManager.getCombinedTableReferenceInfos();

		TableReferenceInfo<?> tableReferenceInfo =
			combinedTableReferenceInfos.get(_modelClassNameId);

		if (tableReferenceInfo == null) {
			throw new IllegalArgumentException(
				"No table reference definition for " +
					ctPersistence.getModelClass());
		}

		Long[] primaryKeysArray = primaryKeys.toArray(new Long[0]);

		DSLQuery dslQuery = null;

		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
			tableReferenceInfo.getParentTableJoinHoldersMap();

		for (List<TableJoinHolder> tableJoinHolders :
				parentTableJoinHoldersMap.values()) {

			for (TableJoinHolder tableJoinHolder : tableJoinHolders) {
				if (tableJoinHolder.isReversed()) {
					continue;
				}

				Column<?, Long> childPKColumn =
					tableJoinHolder.getChildPKColumn();

				Table<?> childTable = childPKColumn.getTable();

				Column<?, Long> ctCollectionIdColumn = childTable.getColumn(
					"ctCollectionId", Long.class);

				Predicate missingRequirementWherePredicate =
					tableJoinHolder.getMissingRequirementWherePredicate();

				missingRequirementWherePredicate =
					missingRequirementWherePredicate.and(
						childPKColumn.in(
							primaryKeysArray
						).and(
							ctCollectionIdColumn.eq(_sourceCTCollectionId)
						));

				Column<?, Long> parentPKColumn =
					tableJoinHolder.getParentPKColumn();

				Table<?> parentTable = parentPKColumn.getTable();

				ctCollectionIdColumn = parentTable.getColumn(
					"ctCollectionId", Long.class);

				if ((ctCollectionIdColumn != null) &&
					ctCollectionIdColumn.isPrimaryKey()) {

					missingRequirementWherePredicate =
						missingRequirementWherePredicate.and(
							ctCollectionIdColumn.neq(
								_sourceCTCollectionId
							).or(
								ctCollectionIdColumn.neq(_targetCTCollectionId)
							).or(
								parentPKColumn.isNull()
							).withParentheses());
				}

				WhereStep whereStep =
					tableJoinHolder.getMissingRequirementWhereStep();

				DSLQuery nextDSLQuery = whereStep.where(
					missingRequirementWherePredicate);

				if (dslQuery == null) {
					dslQuery = nextDSLQuery;
				}
				else {
					dslQuery = dslQuery.union(nextDSLQuery);
				}
			}
		}

		if (dslQuery != null) {
			try (PreparedStatement preparedStatement = _getPreparedStatement(
					connection, dslQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					long modelClassPK = resultSet.getLong(1);

					String tableName = resultSet.getString(2);

					ClassName className = _classNameLocalService.getClassName(
						_tableReferenceDefinitionManager.getClassNameId(
							tableName));

					String classNameValue = className.getValue();

					conflictInfos.add(
						new MissingRequirementConflictInfo(
							classNameValue, modelClassPK,
							_ctDisplayRendererServiceTrackerMap.getService(
								classNameValue)));
				}
			}
			catch (SQLException sqlException) {
				throw new ORMException(
					"Unable to execute query: " + dslQuery, sqlException);
			}
		}
	}

	private void _checkModifications(
		Connection connection, CTPersistence<T> ctPersistence,
		List<ConflictInfo> conflictInfos, String primaryKeyName) {

		String selectSQL = StringBundler.concat(
			"select t1.", primaryKeyName, " from ",
			ctPersistence.getTableName(), " t1 inner join ",
			ctPersistence.getTableName(), " t2 on t1.", primaryKeyName,
			" = t2.", primaryKeyName, " and t1.ctCollectionId = ",
			_sourceCTCollectionId, " and t2.ctCollectionId = ",
			_targetCTCollectionId,
			" inner join CTEntry ctEntry on ctEntry.ctCollectionId = ",
			_sourceCTCollectionId, " and ctEntry.modelClassNameId = ",
			_modelClassNameId, " and ctEntry.modelClassPK = t2.",
			primaryKeyName, " and ctEntry.changeType = ",
			CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		List<Long> resolvedPrimaryKeys = _getModifiedPrimaryKeys(
			connection, ctPersistence, CTColumnResolutionType.IGNORE,
			selectSQL);

		for (Long resolvedPrimaryKey : resolvedPrimaryKeys) {
			conflictInfos.add(
				new ModificationConflictInfo(resolvedPrimaryKey, true));
		}

		_resolveModificationConflicts(
			connection, ctPersistence, primaryKeyName, resolvedPrimaryKeys);

		List<Long> unresolvedPrimaryKeys = _getModifiedPrimaryKeys(
			connection, ctPersistence, CTColumnResolutionType.STRICT,
			selectSQL + " and ctEntry.modelMvccVersion != t2.mvccVersion");

		for (Long unresolvedPrimaryKey : unresolvedPrimaryKeys) {
			conflictInfos.add(
				new ModificationConflictInfo(unresolvedPrimaryKey, false));
		}

		_updateModelMvccVersion(
			connection, primaryKeyName, ctPersistence.getTableName(),
			unresolvedPrimaryKeys);

		List<Long> deletionModificationPKs = _getDeletionModificationPKs(
			connection, ctPersistence, primaryKeyName);

		for (long deletionModificationPK : deletionModificationPKs) {
			conflictInfos.add(
				new DeletionModificationConflictInfo(deletionModificationPK));
		}
	}

	private List<Map.Entry<Long, Long>> _getConflictingPrimaryKeys(
		Connection connection, String constraintConflictsSQL) {

		Set<Long> ignorablePrimaryKeys = new HashSet<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					_sourceCTCollectionId, _modelClassNameId)) {

			if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

				ignorablePrimaryKeys.add(ctEntry.getModelClassPK());
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				constraintConflictsSQL);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			List<Map.Entry<Long, Long>> primaryKeys = null;

			while (resultSet.next()) {
				long sourcePK = resultSet.getLong(1);
				long targetPK = resultSet.getLong(2);

				if (ignorablePrimaryKeys.contains(sourcePK) ||
					ignorablePrimaryKeys.contains(targetPK)) {

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

	private List<Long> _getDeletionModificationPKs(
		Connection connection, CTPersistence<T> ctPersistence,
		String primaryKeyName) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select CTEntry.modelClassPK from CTEntry left join ",
					ctPersistence.getTableName(), " on ",
					ctPersistence.getTableName(), ".", primaryKeyName,
					" = CTEntry.modelClassPK and ",
					ctPersistence.getTableName(), ".ctCollectionId = ",
					_targetCTCollectionId, " where CTEntry.ctCollectionId = ",
					_sourceCTCollectionId, " and CTEntry.modelClassNameId = ",
					_modelClassNameId, " and CTEntry.changeType = ",
					CTConstants.CT_CHANGE_TYPE_MODIFICATION, " and ",
					ctPersistence.getTableName(), ".", primaryKeyName,
					" is null"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			List<Long> primaryKeys = new ArrayList<>();

			while (resultSet.next()) {
				primaryKeys.add(resultSet.getLong(1));
			}

			return primaryKeys;
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private List<Long> _getModifiedPrimaryKeys(
		Connection connection, CTPersistence<T> ctPersistence,
		CTColumnResolutionType ctColumnResolutionType, String sql) {

		Set<String> conflictingColumnNames = ctPersistence.getCTColumnNames(
			ctColumnResolutionType);

		if (conflictingColumnNames.isEmpty()) {
			return Collections.emptyList();
		}

		Map<String, Integer> columnsMap = new HashMap<>(
			ctPersistence.getTableColumnsMap());

		Set<String> columnNames = columnsMap.keySet();

		columnNames.retainAll(conflictingColumnNames);

		Collection<Integer> columnTypes = columnsMap.values();

		if ((ctColumnResolutionType != CTColumnResolutionType.STRICT) ||
			!columnTypes.contains(Types.BLOB)) {

			StringBundler sb = new StringBundler(sql);

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

			sql = sb.toString();
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			List<Long> primaryKeys = new ArrayList<>();

			while (resultSet.next()) {
				long primaryKey = resultSet.getLong(1);

				primaryKeys.add(primaryKey);
			}

			return primaryKeys;
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private PreparedStatement _getPreparedStatement(
			Connection connection, DSLQuery dslQuery)
		throws SQLException {

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		PreparedStatement preparedStatement = connection.prepareStatement(
			SQLTransformer.transform(dslQuery.toSQL(defaultASTNodeListener)));

		List<Object> scalarValues = defaultASTNodeListener.getScalarValues();

		for (int i = 0; i < scalarValues.size(); i++) {
			preparedStatement.setObject(i + 1, scalarValues.get(i));
		}

		return preparedStatement;
	}

	private void _resolveModificationConflicts(
		Connection connection, CTPersistence<T> ctPersistence,
		String primaryKeyName, List<Long> resolvedPrimaryKeys) {

		if (resolvedPrimaryKeys.isEmpty()) {
			return;
		}

		long tempCTCollectionId = -_sourceCTCollectionId;

		StringBundler sb = new StringBundler(
			(2 * resolvedPrimaryKeys.size()) + 9);

		sb.append("update ");
		sb.append(ctPersistence.getTableName());
		sb.append(" set ctCollectionId = ");
		sb.append(tempCTCollectionId);
		sb.append(" where ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and ");
		sb.append(primaryKeyName);
		sb.append(" in (");

		for (Long primaryKey : resolvedPrimaryKeys) {
			sb.append(primaryKey);
			sb.append(", ");
		}

		sb.setStringAt(")", sb.index() - 1);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}

		sb = new StringBundler("select ");

		Map<String, Integer> tableColumnsMap =
			ctPersistence.getTableColumnsMap();

		Set<String> ignoredColumnNames = ctPersistence.getCTColumnNames(
			CTColumnResolutionType.IGNORE);

		for (String name : tableColumnsMap.keySet()) {
			if (name.equals("ctCollectionId")) {
				sb.append(_sourceCTCollectionId);
				sb.append(" as ");
			}
			else if (name.equals("mvccVersion")) {
				sb.append("(t1.mvccVersion + 1) ");
			}
			else if (ignoredColumnNames.contains(name)) {
				sb.append("t2.");
			}
			else {
				sb.append("t1.");
			}

			sb.append(name);
			sb.append(", ");
		}

		sb.setStringAt(" from ", sb.index() - 1);

		sb.append(ctPersistence.getTableName());
		sb.append(" t1, ");
		sb.append(ctPersistence.getTableName());
		sb.append(" t2 where t1.");
		sb.append(primaryKeyName);
		sb.append(" = t2.");
		sb.append(primaryKeyName);
		sb.append(" and t1.ctCollectionId = ");
		sb.append(tempCTCollectionId);
		sb.append(" and t2.ctCollectionId = ");
		sb.append(_targetCTCollectionId);

		try {
			CTRowUtil.copyCTRows(ctPersistence, connection, sb.toString());
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"delete from ", ctPersistence.getTableName(),
					" where ctCollectionId = ", tempCTCollectionId))) {

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private void _updateModelMvccVersion(
		Connection connection, String primaryKeyName, String tableName,
		List<Long> unresolvedPrimaryKeys) {

		StringBundler sb = new StringBundler(
			(2 * unresolvedPrimaryKeys.size()) + 18);

		sb.append("select t1.");
		sb.append(primaryKeyName);
		sb.append(", t1.mvccVersion from ");
		sb.append(tableName);
		sb.append(" t1 inner join CTEntry on t1.");
		sb.append(primaryKeyName);
		sb.append(" = CTEntry.modelClassPK and CTEntry.changeType = ");
		sb.append(CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		sb.append(" and CTEntry.ctCollectionId = ");
		sb.append(_sourceCTCollectionId);
		sb.append(" and CTEntry.modelClassNameId = ");
		sb.append(_modelClassNameId);
		sb.append(" and t1.mvccVersion != CTEntry.modelMvccVersion");
		sb.append(" where t1.ctCollectionId = ");
		sb.append(_targetCTCollectionId);

		if (!unresolvedPrimaryKeys.isEmpty()) {
			sb.append(" and t1.");
			sb.append(primaryKeyName);
			sb.append(" not in (");

			for (Long unresolvedPrimaryKey : unresolvedPrimaryKeys) {
				sb.append(unresolvedPrimaryKey);
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

	private final ClassNameLocalService _classNameLocalService;
	private final ServiceTrackerMap
		<ConstraintResolverKey, ConstraintResolver<?>>
			_constraintResolverServiceTrackerMap;
	private final ServiceTrackerMap<String, CTDisplayRenderer<?>>
		_ctDisplayRendererServiceTrackerMap;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private final long _modelClassNameId;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final long _sourceCTCollectionId;
	private final TableReferenceDefinitionManager
		_tableReferenceDefinitionManager;
	private final long _targetCTCollectionId;

}