/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class TableMappingImpl<L extends BaseModel<L>, R extends BaseModel<R>>
	implements TableMapping<L, R> {

	public TableMappingImpl(
		String mappingTableName, String leftColumnName, String rightColumnName,
		BasePersistence<L> leftBasePersistence,
		BasePersistence<R> rightBasePersistence) {

		this.leftColumnName = leftColumnName;
		this.rightColumnName = rightColumnName;
		this.leftBasePersistence = leftBasePersistence;
		this.rightBasePersistence = rightBasePersistence;

		DataSource dataSource = leftBasePersistence.getDataSource();

		addMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "INSERT INTO " + mappingTableName + " (" +
				leftColumnName + ", " + rightColumnName+ ") VALUES (?, ?)",
			new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });

		deleteMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
				leftColumnName + " = ? AND " + rightColumnName+ " = ?",
			new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });

		deleteMappingsByLeftPrimaryKeySqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
					leftColumnName + " = ?",
				new int[] { java.sql.Types.BIGINT });

		deleteMappingsByRightPrimaryKeySqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
					rightColumnName + " = ?",
				new int[] { java.sql.Types.BIGINT });

		getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource, "SELECT " + rightColumnName + " FROM " +
					mappingTableName + " WHERE " + leftColumnName + "=?",
				new int[] { java.sql.Types.BIGINT }, RowMapper.ID);

		getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource, "SELECT " + leftColumnName + " FROM " +
					mappingTableName + " WHERE " + rightColumnName + "=?",
				new int[] { java.sql.Types.BIGINT }, RowMapper.ID);

		leftToRightPortalCache = MultiVMPoolUtil.getCache(
			mappingTableName + "-Left->Right");

		rightToLeftPortalCache = MultiVMPoolUtil.getCache(
			mappingTableName + "-Right->Left");
	}

	@Override
	public boolean addMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		if (doContainsMapping(leftPrimaryKey, rightPrimaryKey, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPrimaryKey);
		rightToLeftPortalCache.remove(rightPrimaryKey);

		ModelListener<L>[] leftModelListeners =
			leftBasePersistence.getListeners();

		Class<R> rightModelClass = rightBasePersistence.getModelClass();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeAddAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		ModelListener<R>[] rightModelListeners =
			rightBasePersistence.getListeners();

		Class<L> leftModelClass = leftBasePersistence.getModelClass();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeAddAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		try {
			addMappingSqlUpdate.update(leftPrimaryKey, rightPrimaryKey);
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
	public boolean containsMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		return doContainsMapping(leftPrimaryKey, rightPrimaryKey, true);
	}

	@Override
	public boolean deleteMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		if (!doContainsMapping(leftPrimaryKey, rightPrimaryKey, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPrimaryKey);
		rightToLeftPortalCache.remove(rightPrimaryKey);

		ModelListener<L>[] leftModelListeners =
			leftBasePersistence.getListeners();

		Class<R> rightModelClass = rightBasePersistence.getModelClass();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeRemoveAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		ModelListener<R>[] rightModelListeners =
			rightBasePersistence.getListeners();

		Class<L> leftModelClass = leftBasePersistence.getModelClass();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeRemoveAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		int affectRowCount = 0;

		try {
			affectRowCount = deleteMappingSqlUpdate.update(
				leftPrimaryKey, rightPrimaryKey);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (affectRowCount > 0) {
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
	public int deleteMappingsByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException {

		return doDeleteMappingsByMasterPrimaryKey(
			leftBasePersistence, rightBasePersistence, leftToRightPortalCache,
			rightToLeftPortalCache,
			getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery,
			deleteMappingsByLeftPrimaryKeySqlUpdate, leftPrimaryKey);
	}

	@Override
	public int deleteMappingsByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException {

		return doDeleteMappingsByMasterPrimaryKey(
			rightBasePersistence, leftBasePersistence, rightToLeftPortalCache,
			leftToRightPortalCache,
			getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery,
			deleteMappingsByRightPrimaryKeySqlUpdate, rightPrimaryKey);
	}

	@Override
	public List<L> getLeftBaseModelsByRightPrimaryKey(
			long rightPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException {

		return doGetSlaveBaseModelsByMasterPrimaryKey(
			rightToLeftPortalCache,
			getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery, rightPrimaryKey,
			leftBasePersistence, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeysByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException {

		return doGetSlavePrimaryKeysByMasterPrimaryKey(
			rightToLeftPortalCache,
			getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery, rightPrimaryKey,
			true);
	}

	@Override
	public TableMapping<R, L> getReverseTableMapping() {
		return reverseTableMapping;
	}

	@Override
	public List<R> getRightBaseModelsByLeftPrimaryKey(
			long leftPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException {

		return doGetSlaveBaseModelsByMasterPrimaryKey(
			leftToRightPortalCache,
			getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery, leftPrimaryKey,
			rightBasePersistence, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeysByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException {

		return doGetSlavePrimaryKeysByMasterPrimaryKey(
			leftToRightPortalCache,
			getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery, leftPrimaryKey,
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

	public void setReverseTableMapping(TableMapping<R, L> reverseTableMapping) {
		this.reverseTableMapping = reverseTableMapping;
	}

	protected static <M extends BaseModel<M>, S extends BaseModel<S>> int
		doDeleteMappingsByMasterPrimaryKey(
			BasePersistence<M> masterBasePersistence,
			BasePersistence<S> slaveBasePersistence,
			PortalCache<Long, long[]> masterToSlavePortalCache,
			PortalCache<Long, long[]> slaveToMasterPortalCache,
			MappingSqlQuery<Long> mappingSqlQuery, SqlUpdate deleteSqlUpdate,
			long masterPrimaryKey)
		throws SystemException {

		ModelListener<M>[] masterModelListeners =
			masterBasePersistence.getListeners();
		ModelListener<S>[] slaveModelListeners =
			slaveBasePersistence.getListeners();

		long[] slavePrimaryKeys = doGetSlavePrimaryKeysByMasterPrimaryKey(
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

		int affectRowCount = 0;

		try {
			affectRowCount = deleteSqlUpdate.update(masterPrimaryKey);
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

		return affectRowCount;
	}

	protected static <T extends BaseModel<T>> List<T>
		doGetSlaveBaseModelsByMasterPrimaryKey(
			PortalCache<Long, long[]> portalCache,
			MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
			BasePersistence<T> slaveBasePersistence, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] slavePrimaryKeys = doGetSlavePrimaryKeysByMasterPrimaryKey(
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

	protected static long[] doGetSlavePrimaryKeysByMasterPrimaryKey(
			PortalCache<Long, long[]> portalCache,
			MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
			boolean updateCache)
		throws SystemException {

		long[] primaryKeys = portalCache.get(masterPrimaryKey);

		if (primaryKeys == null) {
			List<Long> primaryKeyList = null;

			try {
				primaryKeyList = mappingSqlQuery.execute(masterPrimaryKey);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}

			primaryKeys = new long[primaryKeyList.size()];

			for (int i = 0; i < primaryKeys.length; i++) {
				primaryKeys[i] = primaryKeyList.get(i);
			}

			Arrays.sort(primaryKeys);

			if (updateCache) {
				portalCache.put(masterPrimaryKey, primaryKeys);
			}
		}

		return primaryKeys;
	}

	protected boolean doContainsMapping(
			long leftPrimaryKey, long rightPrimaryKey, boolean updateCache)
		throws SystemException {

		long[] rightPrimaryKeys = doGetSlavePrimaryKeysByMasterPrimaryKey(
			leftToRightPortalCache,
			getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery, leftPrimaryKey,
			updateCache);

		if (Arrays.binarySearch(rightPrimaryKeys, rightPrimaryKey) < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected SqlUpdate addMappingSqlUpdate;
	protected SqlUpdate deleteMappingsByLeftPrimaryKeySqlUpdate;
	protected SqlUpdate deleteMappingsByRightPrimaryKeySqlUpdate;
	protected SqlUpdate deleteMappingSqlUpdate;
	protected MappingSqlQuery<Long>
		getRightPrimaryKeysByLeftPrimaryKeyMappingSqlQuery;
	protected MappingSqlQuery<Long>
		getLeftPrimaryKeysByRightPrimaryKeyMappingSqlQuery;
	protected BasePersistence<L> leftBasePersistence;
	protected String leftColumnName;
	protected PortalCache<Long, long[]> leftToRightPortalCache;
	protected TableMapping<R, L> reverseTableMapping;
	protected BasePersistence<R> rightBasePersistence;
	protected String rightColumnName;
	protected PortalCache<Long, long[]> rightToLeftPortalCache;

}