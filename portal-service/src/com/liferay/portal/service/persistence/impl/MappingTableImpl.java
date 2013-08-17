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
public class MappingTableImpl<L extends BaseModel<L>, R extends BaseModel<R>>
	implements MappingTable<L, R> {

	public MappingTableImpl(
		String mappingTableName, String leftColumnName, String rightColumnName,
		BasePersistence<L> leftPersistence,
		BasePersistence<R> rightPersistence) {

		this.leftColumnName = leftColumnName;
		this.rightColumnName = rightColumnName;
		this.leftPersistence = leftPersistence;
		this.rightPersistence = rightPersistence;

		DataSource dataSource = leftPersistence.getDataSource();

		addSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "INSERT INTO " + mappingTableName + " (" +
				leftColumnName + ", " + rightColumnName+ ") VALUES (?, ?)",
			new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });

		deleteSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
				leftColumnName + " = ? AND " + rightColumnName+ " = ?",
			new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });

		deleteByLeftSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
				leftColumnName + " = ?",
			new int[] { java.sql.Types.BIGINT });

		deleteByRightSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource, "DELETE FROM " + mappingTableName + " WHERE " +
				rightColumnName + " = ?",
			new int[] { java.sql.Types.BIGINT });

		findByLeftMappingSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource, "SELECT " + rightColumnName + " FROM " +
					mappingTableName + " WHERE " + leftColumnName + "=?",
				new int[] { java.sql.Types.BIGINT }, RowMapper.ID);

		findByRightMappingSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource, "SELECT " + leftColumnName + " FROM " +
					mappingTableName + " WHERE " + rightColumnName + "=?",
				new int[] { java.sql.Types.BIGINT }, RowMapper.ID);

		leftToRightPortalCache = MultiVMPoolUtil.getCache(
			mappingTableName + "-Left->Right");
		rightToLeftPortalCache = MultiVMPoolUtil.getCache(
			mappingTableName + "-Right->Left");

		reverseMappingTable = new ReverseMappingTable<R, L>(this);
	}

	@Override
	public boolean addMapping(long leftPk, long rightPk)
		throws SystemException {

		if (doContainsMapping(leftPk, rightPk, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPk);
		rightToLeftPortalCache.remove(rightPk);

		ModelListener<L>[] leftModelListeners = leftPersistence.getListeners();

		Class<R> rightModelClass = rightPersistence.getModelClass();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeAddAssociation(
				leftPk, rightModelClass.getName(), rightPk);
		}

		ModelListener<R>[] rightModelListeners =
			rightPersistence.getListeners();

		Class<L> leftModelClass = leftPersistence.getModelClass();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeAddAssociation(
				rightPk, leftModelClass.getName(), leftPk);
		}

		try {
			addSqlUpdate.update(leftPk, rightPk);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onAfterAddAssociation(
				leftPk, rightModelClass.getName(), rightPk);
		}

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onAfterAddAssociation(
				rightPk, leftModelClass.getName(), leftPk);
		}

		return true;
	}

	@Override
	public boolean containsMapping(long leftPk, long rightPk)
		throws SystemException {

		return doContainsMapping(leftPk, rightPk, true);
	}

	@Override
	public int deleteByLeftPk(long leftPk) throws SystemException {
		return doDeleteByPk(
			leftPersistence, rightPersistence, leftToRightPortalCache,
			findByLeftMappingSqlQuery, deleteByLeftSqlUpdate, leftPk);
	}

	@Override
	public int deleteByRightPk(long rightPk) throws SystemException {
		return doDeleteByPk(
			rightPersistence, leftPersistence, rightToLeftPortalCache,
			findByRightMappingSqlQuery, deleteByRightSqlUpdate, rightPk);
	}

	@Override
	public boolean deleteMapping(long leftPk, long rightPk)
		throws SystemException {

		if (!doContainsMapping(leftPk, rightPk, false)) {
			return false;
		}

		leftToRightPortalCache.remove(leftPk);
		rightToLeftPortalCache.remove(rightPk);

		ModelListener<L>[] leftModelListeners = leftPersistence.getListeners();

		Class<R> rightModelClass = rightPersistence.getModelClass();

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeRemoveAssociation(
				leftPk, rightModelClass.getName(), rightPk);
		}

		ModelListener<R>[] rightModelListeners =
			rightPersistence.getListeners();

		Class<L> leftModelClass = leftPersistence.getModelClass();

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeRemoveAssociation(
				rightPk, leftModelClass.getName(), leftPk);
		}

		int affectRowCount = 0;

		try {
			affectRowCount = deleteSqlUpdate.update(leftPk, rightPk);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (affectRowCount > 0) {
			for (ModelListener<L> leftModelListener : leftModelListeners) {
				leftModelListener.onAfterRemoveAssociation(
					leftPk, rightModelClass.getName(), rightPk);
			}

			for (ModelListener<R> rightModelListener : rightModelListeners) {
				rightModelListener.onAfterRemoveAssociation(
					rightPk, leftModelClass.getName(), leftPk);
			}

			return true;
		}

		return false;
	}

	@Override
	public long[] findByLeftPk(long leftPk) throws SystemException {
		return doFindByPk(
			leftToRightPortalCache, findByLeftMappingSqlQuery, leftPk, true);
	}

	@Override
	public List<R> findByLeftPkAsEntities(
			long leftPk, int start, int end, OrderByComparator obc)
		throws SystemException {

		return doFindByPkAsEntities(
			leftToRightPortalCache, findByLeftMappingSqlQuery, leftPk,
			rightPersistence, start, end, obc);
	}

	@Override
	public long[] findByRightPk(long rightPk) throws SystemException {
		return doFindByPk(
			rightToLeftPortalCache, findByRightMappingSqlQuery, rightPk, true);
	}

	@Override
	public List<L> findByRightPkAsEntities(
			long rightPk, int start, int end, OrderByComparator obc)
		throws SystemException {

		return doFindByPkAsEntities(
			rightToLeftPortalCache, findByRightMappingSqlQuery, rightPk,
			leftPersistence, start, end, obc);
	}

	@Override
	public MappingTable<R, L> getReverseMappingTable() {
		return reverseMappingTable;
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		if (this.leftColumnName.equals(leftColumnName) &&
			this.rightColumnName.equals(rightColumnName)) {

			return true;
		}

		return false;
	}

	public void setReverseMappingTable(MappingTable<R, L> reverseMappingTable) {
		this.reverseMappingTable = reverseMappingTable;
	}

	protected static <M extends BaseModel<M>, S extends BaseModel<S>> int
		doDeleteByPk(
			BasePersistence<M> masterPersistence,
			BasePersistence<S> slavePersistence,
			PortalCache<Long, long[]> masterToSlavePortalCache,
			MappingSqlQuery<Long> findByMasterMappingSqlQuery,
			SqlUpdate deleteByMasterSqlUpdate, long masterPk)
		throws SystemException {

		ModelListener<M>[] masterModelListeners =
			masterPersistence.getListeners();
		ModelListener<S>[] slaveModelListeners =
			slavePersistence.getListeners();

		long[] slavePks = null;

		Class<M> masterModelClass = null;
		Class<S> slaveModelClass = null;

		if ((masterModelListeners.length > 0) ||
			(slaveModelListeners.length > 0)) {

			masterModelClass = masterPersistence.getModelClass();

			slaveModelClass = slavePersistence.getModelClass();

			slavePks = doFindByPk(
				masterToSlavePortalCache, findByMasterMappingSqlQuery, masterPk,
				false);

			for (long slavePk : slavePks) {
				for (ModelListener<M> masterModelListener :
						masterModelListeners) {

					masterModelListener.onBeforeRemoveAssociation(
						masterPk, slaveModelClass.getName(), slavePk);
				}

				for (ModelListener<S> slaveModelListener :
						slaveModelListeners) {

					slaveModelListener.onBeforeRemoveAssociation(
						slavePk, masterModelClass.getName(), masterPk);
				}
			}
		}

		masterToSlavePortalCache.remove(masterPk);

		int affectRowCount = 0;

		try {
			affectRowCount = deleteByMasterSqlUpdate.update(masterPk);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if ((masterModelListeners.length > 0) ||
			(slaveModelListeners.length > 0)) {

			for (long slavePk : slavePks) {
				for (ModelListener<M> masterModelListener :
						masterModelListeners) {

					masterModelListener.onAfterRemoveAssociation(
						masterPk, slaveModelClass.getName(), slavePk);
				}

				for (ModelListener<S> slaveModelListener :
						slaveModelListeners) {

					slaveModelListener.onAfterRemoveAssociation(
						slavePk, masterModelClass.getName(), masterPk);
				}
			}
		}

		return affectRowCount;
	}

	protected static long[] doFindByPk(
			PortalCache<Long, long[]> portalCache,
			MappingSqlQuery<Long> mappingSqlQuery, long pk, boolean updateCache)
		throws SystemException {

		long[] pks = portalCache.get(pk);

		if (pks == null) {
			List<Long> pkList = null;

			try {
				pkList = mappingSqlQuery.execute(pk);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}

			pks = new long[pkList.size()];

			for (int i = 0; i < pks.length; i++) {
				pks[i] = pkList.get(i);
			}

			Arrays.sort(pks);

			if (updateCache) {
				portalCache.put(pk, pks);
			}
		}

		return pks;
	}

	protected static <T extends BaseModel<T>> List<T> doFindByPkAsEntities(
			PortalCache<Long, long[]> portalCache,
			MappingSqlQuery<Long> mappingSqlQuery, long pk,
			BasePersistence<T> basePersistence, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] ids = doFindByPk(portalCache, mappingSqlQuery, pk, true);

		if (ids.length == 0) {
			return Collections.emptyList();
		}

		List<T> entities = new ArrayList<T>(ids.length);

		try {
			for (long id : ids) {
				entities.add(basePersistence.findByPrimaryKey(id));
			}
		}
		catch (NoSuchModelException nsme) {
			throw new SystemException(nsme);
		}

		if (obc != null) {
			Collections.sort(entities, obc);
		}

		return ListUtil.subList(entities, start, end);
	}

	protected boolean doContainsMapping(
			long leftPk, long rightPk, boolean updateCache)
		throws SystemException {

		long[] rightPks = doFindByPk(
			leftToRightPortalCache, findByLeftMappingSqlQuery, leftPk,
			updateCache);

		if (Arrays.binarySearch(rightPks, rightPk) < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected SqlUpdate addSqlUpdate;
	protected SqlUpdate deleteByLeftSqlUpdate;
	protected SqlUpdate deleteByRightSqlUpdate;
	protected SqlUpdate deleteSqlUpdate;
	protected MappingSqlQuery<Long> findByLeftMappingSqlQuery;
	protected MappingSqlQuery<Long> findByRightMappingSqlQuery;
	protected String leftColumnName;
	protected BasePersistence<L> leftPersistence;
	protected PortalCache<Long, long[]> leftToRightPortalCache;
	protected MappingTable<R, L> reverseMappingTable;
	protected String rightColumnName;
	protected BasePersistence<R> rightPersistence;
	protected PortalCache<Long, long[]> rightToLeftPortalCache;

}