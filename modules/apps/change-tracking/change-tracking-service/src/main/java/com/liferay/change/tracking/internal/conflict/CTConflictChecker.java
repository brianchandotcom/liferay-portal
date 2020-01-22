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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
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
		CTService<T> ctService,
		ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
			serviceTrackerMap,
		long sourceCTCollectionId, long targetCTCollectionId) {

		_ctService = ctService;
		_serviceTrackerMap = serviceTrackerMap;
		_sourceCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void addCTEntry(CTEntry ctEntry) {
		if (ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_ignorablePrimaryKeys.add(ctEntry.getModelClassPK());
		}
	}

	public List<ConflictInfo> check() throws PortalException {
		return _ctService.updateWithUnsafeFunction(this::_check);
	}

	private List<ConflictInfo> _check(CTPersistence<T> ctPersistence)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		List<String[]> uniqueIndexColumnNames =
			ctPersistence.getUniqueIndexColumnNames();

		List<ConflictInfo> conflictInfos = new ArrayList<>();

		if (!uniqueIndexColumnNames.isEmpty()) {
			for (String[] columnNames : uniqueIndexColumnNames) {
				_checkConstraint(
					connection, ctPersistence, conflictInfos, columnNames);
			}
		}

		return conflictInfos;
	}

	private void _checkConstraint(
			Connection connection, CTPersistence<T> ctPersistence,
			List<ConflictInfo> conflictInfos, String[] columnNames)
		throws PortalException {

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

		String constraintConflictsSQL = CTRowUtil.getConstraintConflictsSQL(
			ctPersistence.getTableName(), primaryKeyName, columnNames,
			_sourceCollectionId, _targetCTCollectionId, true);

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

	private final CTService<T> _ctService;
	private final Set<Long> _ignorablePrimaryKeys = new HashSet<>();
	private final ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
		_serviceTrackerMap;
	private final long _sourceCollectionId;
	private final long _targetCTCollectionId;

}