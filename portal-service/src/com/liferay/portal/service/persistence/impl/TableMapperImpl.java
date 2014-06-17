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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class TableMapperImpl<L extends BaseModel<L>, R extends BaseModel<R>>
	implements TableMapper<L, R> {

	public TableMapperImpl(
		String tableName, String leftColumnName, String rightColumnName,
		BasePersistence<L> leftBasePersistence,
		BasePersistence<R> rightBasePersistence) {

		this.leftColumnName = leftColumnName;
		this.rightColumnName = rightColumnName;
		this.leftBasePersistence = leftBasePersistence;
		this.rightBasePersistence = rightBasePersistence;

		DataSource dataSource = leftBasePersistence.getDataSource();

		addTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			"INSERT INTO " + tableName + " (" + leftColumnName + ", " +
				rightColumnName + ") VALUES (?, ?)",
			new int[] {Types.BIGINT, Types.BIGINT});
		deleteLeftPrimaryKeyTableMappingsSqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource,
				"DELETE FROM " + tableName + " WHERE " + leftColumnName +
					" = ?",
				new int[] {Types.BIGINT});
		deleteRightPrimaryKeyTableMappingsSqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource,
				"DELETE FROM " + tableName + " WHERE " + rightColumnName +
					" = ?",
				new int[] {Types.BIGINT});
		deleteTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			"DELETE FROM " + tableName + " WHERE " + leftColumnName +
				" = ? AND " + rightColumnName + " = ?",
			new int[] {Types.BIGINT, Types.BIGINT});
		getLeftPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				"SELECT " + leftColumnName + " FROM " + tableName + " WHERE " +
					rightColumnName + " = ?",
				new int[] {Types.BIGINT}, RowMapper.PRIMARY_KEY);
		getRightPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				"SELECT " + rightColumnName + " FROM " + tableName + " WHERE " +
					leftColumnName + " = ?",
				new int[] {Types.BIGINT}, RowMapper.PRIMARY_KEY);
		leftToRightPortalCache = MultiVMPoolUtil.getCache(
			TableMapper.class.getName() + "-" + tableName + "-LeftToRight");
		rightToLeftPortalCache = MultiVMPoolUtil.getCache(
			TableMapper.class.getName() + "-" + tableName + "-RightToLeft");
	}

	@Override
	public boolean addTableMapping(long leftPrimaryKey, long rightPrimaryKey) {
		if (containsTableMapping(leftPrimaryKey, rightPrimaryKey, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPrimaryKey);
		rightToLeftPortalCache.remove(rightPrimaryKey);

		Class<R> rightModelClass = rightBasePersistence.getModelClass();

		ModelListener<L>[] leftModelListeners =
			leftBasePersistence.getListeners();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeAddAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		Class<L> leftModelClass = leftBasePersistence.getModelClass();

		ModelListener<R>[] rightModelListeners =
			rightBasePersistence.getListeners();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeAddAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		try {
			addTableMappingSqlUpdate.update(leftPrimaryKey, rightPrimaryKey);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onAfterAddAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onAfterAddAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		return true;
	}

	@Override
	public boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey) {

		return containsTableMapping(leftPrimaryKey, rightPrimaryKey, true);
	}

	@Override
	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey) {
		return deleteTableMappings(
			leftBasePersistence, rightBasePersistence, leftToRightPortalCache,
			rightToLeftPortalCache, getRightPrimaryKeysSqlQuery,
			deleteLeftPrimaryKeyTableMappingsSqlUpdate, leftPrimaryKey);
	}

	@Override
	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey) {
		return deleteTableMappings(
			rightBasePersistence, leftBasePersistence, rightToLeftPortalCache,
			leftToRightPortalCache, getLeftPrimaryKeysSqlQuery,
			deleteRightPrimaryKeyTableMappingsSqlUpdate, rightPrimaryKey);
	}

	@Override
	public boolean deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey) {

		if (!containsTableMapping(leftPrimaryKey, rightPrimaryKey, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPrimaryKey);
		rightToLeftPortalCache.remove(rightPrimaryKey);

		Class<R> rightModelClass = rightBasePersistence.getModelClass();

		ModelListener<L>[] leftModelListeners =
			leftBasePersistence.getListeners();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeRemoveAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		Class<L> leftModelClass = leftBasePersistence.getModelClass();

		ModelListener<R>[] rightModelListeners =
			rightBasePersistence.getListeners();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeRemoveAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		int rowCount = 0;

		try {
			rowCount = deleteTableMappingSqlUpdate.update(
				leftPrimaryKey, rightPrimaryKey);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (rowCount > 0) {
			for (ModelListener<L> leftModelListener : leftModelListeners) {
				leftModelListener.onAfterRemoveAssociation(
					leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
			}

			for (ModelListener<R> rightModelListener : rightModelListeners) {
				rightModelListener.onAfterRemoveAssociation(
					rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
			}

			return true;
		}

		return false;
	}

	@Override
	public void destroy() {
		MultiVMPoolUtil.removeCache(leftToRightPortalCache.getName());
		MultiVMPoolUtil.removeCache(rightToLeftPortalCache.getName());
	}

	@Override
	public List<L> getLeftBaseModels(
		long rightPrimaryKey, int start, int end, OrderByComparator obc) {

		return getBaseModels(
			rightToLeftPortalCache, getLeftPrimaryKeysSqlQuery, rightPrimaryKey,
			leftBasePersistence, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeys(long rightPrimaryKey) {
		return getPrimaryKeys(
			rightToLeftPortalCache, getLeftPrimaryKeysSqlQuery, rightPrimaryKey,
			true);
	}

	@Override
	public TableMapper<R, L> getReverseTableMapper() {
		return reverseTableMapper;
	}

	@Override
	public List<R> getRightBaseModels(
		long leftPrimaryKey, int start, int end, OrderByComparator obc) {

		return getBaseModels(
			leftToRightPortalCache, getRightPrimaryKeysSqlQuery, leftPrimaryKey,
			rightBasePersistence, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeys(long leftPrimaryKey) {
		return getPrimaryKeys(
			leftToRightPortalCache, getRightPrimaryKeysSqlQuery, leftPrimaryKey,
			true);
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		if (this.leftColumnName.equals(leftColumnName) &&
			this.rightColumnName.equals(rightColumnName)) {

			return true;
		}

		return false;
	}

	public void setReverseTableMapper(TableMapper<R, L> reverseTableMapper) {
		this.reverseTableMapper = reverseTableMapper;
	}

	protected static <M extends BaseModel<M>, S extends BaseModel<S>> int
		deleteTableMappings(
			BasePersistence<M> masterBasePersistence,
			BasePersistence<S> slaveBasePersistence,
			PortalCache<Long, long[]> masterToSlavePortalCache,
			PortalCache<Long, long[]> slaveToMasterPortalCache,
			MappingSqlQuery<Long> mappingSqlQuery, SqlUpdate deleteSqlUpdate,
			long masterPrimaryKey) {

		ModelListener<M>[] masterModelListeners =
			masterBasePersistence.getListeners();
		ModelListener<S>[] slaveModelListeners =
			slaveBasePersistence.getListeners();

		long[] slavePrimaryKeys = getPrimaryKeys(
			masterToSlavePortalCache, mappingSqlQuery, masterPrimaryKey, false);

		Class<M> masterModelClass = null;
		Class<S> slaveModelClass = null;

		if ((masterModelListeners.length > 0) ||
			(slaveModelListeners.length > 0)) {

			masterModelClass = masterBasePersistence.getModelClass();
			slaveModelClass = slaveBasePersistence.getModelClass();

			for (long slavePrimaryKey : slavePrimaryKeys) {
				for (ModelListener<M> masterModelListener :
						masterModelListeners) {

					masterModelListener.onBeforeRemoveAssociation(
						masterPrimaryKey, slaveModelClass.getName(),
						slavePrimaryKey);
				}

				for (ModelListener<S> slaveModelListener :
						slaveModelListeners) {

					slaveModelListener.onBeforeRemoveAssociation(
						slavePrimaryKey, masterModelClass.getName(),
						masterPrimaryKey);
				}
			}
		}

		masterToSlavePortalCache.remove(masterPrimaryKey);

		for (long slavePrimaryKey : slavePrimaryKeys) {
			slaveToMasterPortalCache.remove(slavePrimaryKey);
		}

		int rowCount = 0;

		try {
			rowCount = deleteSqlUpdate.update(masterPrimaryKey);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if ((masterModelListeners.length > 0) ||
			(slaveModelListeners.length > 0)) {

			for (long slavePrimaryKey : slavePrimaryKeys) {
				for (ModelListener<M> masterModelListener :
						masterModelListeners) {

					masterModelListener.onAfterRemoveAssociation(
						masterPrimaryKey, slaveModelClass.getName(),
						slavePrimaryKey);
				}

				for (ModelListener<S> slaveModelListener :
						slaveModelListeners) {

					slaveModelListener.onAfterRemoveAssociation(
						slavePrimaryKey, masterModelClass.getName(),
						masterPrimaryKey);
				}
			}
		}

		return rowCount;
	}

	protected static <T extends BaseModel<T>> List<T>
		getBaseModels(
			PortalCache<Long, long[]> portalCache,
			MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
			BasePersistence<T> slaveBasePersistence, int start, int end,
			OrderByComparator obc) {

		long[] slavePrimaryKeys = getPrimaryKeys(
			portalCache, mappingSqlQuery, masterPrimaryKey, true);

		if (slavePrimaryKeys.length == 0) {
			return Collections.emptyList();
		}

		List<T> slaveBaseModels = new ArrayList<T>(slavePrimaryKeys.length);

		try {
			for (long slavePrimaryKey : slavePrimaryKeys) {
				slaveBaseModels.add(
					slaveBasePersistence.findByPrimaryKey(slavePrimaryKey));
			}
		}
		catch (NoSuchModelException nsme) {
			throw new SystemException(nsme);
		}

		if (obc != null) {
			Collections.sort(slaveBaseModels, obc);
		}

		return ListUtil.subList(slaveBaseModels, start, end);
	}

	protected static long[] getPrimaryKeys(
		PortalCache<Long, long[]> portalCache,
		MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
		boolean updateCache) {

		long[] primaryKeys = portalCache.get(masterPrimaryKey);

		if (primaryKeys == null) {
			List<Long> primaryKeysList = null;

			try {
				primaryKeysList = mappingSqlQuery.execute(masterPrimaryKey);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}

			primaryKeys = new long[primaryKeysList.size()];

			for (int i = 0; i < primaryKeys.length; i++) {
				primaryKeys[i] = primaryKeysList.get(i);
			}

			Arrays.sort(primaryKeys);

			if (updateCache) {
				portalCache.putQuiet(masterPrimaryKey, primaryKeys);
			}
		}

		return primaryKeys;
	}

	protected boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey, boolean updateCache) {

		long[] rightPrimaryKeys = getPrimaryKeys(
			leftToRightPortalCache, getRightPrimaryKeysSqlQuery, leftPrimaryKey,
			updateCache);

		if (Arrays.binarySearch(rightPrimaryKeys, rightPrimaryKey) < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected SqlUpdate addTableMappingSqlUpdate;
	protected SqlUpdate deleteLeftPrimaryKeyTableMappingsSqlUpdate;
	protected SqlUpdate deleteRightPrimaryKeyTableMappingsSqlUpdate;
	protected SqlUpdate deleteTableMappingSqlUpdate;
	protected MappingSqlQuery<Long> getLeftPrimaryKeysSqlQuery;
	protected MappingSqlQuery<Long> getRightPrimaryKeysSqlQuery;
	protected BasePersistence<L> leftBasePersistence;
	protected String leftColumnName;
	protected PortalCache<Long, long[]> leftToRightPortalCache;
	protected TableMapper<R, L> reverseTableMapper;
	protected BasePersistence<R> rightBasePersistence;
	protected String rightColumnName;
	protected PortalCache<Long, long[]> rightToLeftPortalCache;

}