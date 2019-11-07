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

package com.liferay.portal.kernel.internal.service.persistence.change.tracking;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.ParamSetter;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.internal.service.persistence.ReverseTableMapper;
import com.liferay.portal.kernel.internal.service.persistence.TableMapperImpl;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Preston Crary
 */
public class CTTableMapper<L extends BaseModel<L>, R extends BaseModel<R>>
	extends TableMapperImpl<L, R> {

	public CTTableMapper(
		String tableName, String companyColumnName, String leftColumnName,
		String rightColumnName, Class<L> leftModelClass,
		Class<R> rightModelClass, BasePersistence<L> leftBasePersistence,
		BasePersistence<R> rightBasePersistence, boolean cacheless) {

		super(
			tableName, companyColumnName, leftColumnName, rightColumnName,
			leftModelClass, rightModelClass, leftBasePersistence,
			rightBasePersistence, cacheless);
	}

	@Override
	public boolean addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.addTableMapping(
				companyId, leftPrimaryKey, rightPrimaryKey);
		}

		if (_containsTableMapping(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

			return false;
		}

		_addTableMapping(
			companyId, leftPrimaryKey, rightPrimaryKey, ctCollectionId);

		return true;
	}

	@Override
	public long[] addTableMappings(
		long companyId, long leftPrimaryKey, long[] rightPrimaryKeys) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.addTableMappings(
				companyId, leftPrimaryKey, rightPrimaryKeys);
		}

		List<Long> addedRightPrimaryKeys = new ArrayList<>();

		for (long rightPrimaryKey : rightPrimaryKeys) {
			if (!_containsTableMapping(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

				addedRightPrimaryKeys.add(rightPrimaryKey);

				_addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey, ctCollectionId);
			}
		}

		return ArrayUtil.toLongArray(addedRightPrimaryKeys);
	}

	@Override
	public long[] addTableMappings(
		long companyId, long[] leftPrimaryKeys, long rightPrimaryKey) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.addTableMappings(
				companyId, leftPrimaryKeys, rightPrimaryKey);
		}

		List<Long> addedLeftPrimaryKeys = new ArrayList<>();

		for (long leftPrimaryKey : leftPrimaryKeys) {
			if (!_containsTableMapping(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

				addedLeftPrimaryKeys.add(rightPrimaryKey);

				_addTableMapping(
					companyId, leftPrimaryKey, rightPrimaryKey, ctCollectionId);
			}
		}

		return ArrayUtil.toLongArray(addedLeftPrimaryKeys);
	}

	@Override
	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.deleteLeftPrimaryKeyTableMappings(leftPrimaryKey);
		}

		return _deleteTableMappings(
			leftModelClass, rightModelClass, getRightPrimaryKeysSqlQuery,
			leftPrimaryKey, ctCollectionId, true);
	}

	@Override
	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.deleteRightPrimaryKeyTableMappings(rightPrimaryKey);
		}

		return _deleteTableMappings(
			rightModelClass, leftModelClass, _getCTLeftPrimaryKeysSqlQuery,
			rightPrimaryKey, ctCollectionId, false);
	}

	@Override
	public boolean deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.deleteTableMapping(leftPrimaryKey, rightPrimaryKey);
		}

		if (_containsTableMapping(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId)) {

			_deleteTableMapping(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId);

			return true;
		}

		return false;
	}

	@Override
	public long[] deleteTableMappings(
		long leftPrimaryKey, long[] rightPrimaryKeys) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.deleteTableMappings(leftPrimaryKey, rightPrimaryKeys);
		}

		long[] currentRightPrimaryKeys = _getPrimaryKeys(
			_getCTRightPrimaryKeysSqlQuery, leftPrimaryKey, ctCollectionId);

		List<Long> deletedRightPrimaryKeys = new ArrayList<>();

		for (long rightPrimaryKey : rightPrimaryKeys) {
			if (Arrays.binarySearch(currentRightPrimaryKeys, rightPrimaryKey) >=
					0) {

				_deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId);

				deletedRightPrimaryKeys.add(rightPrimaryKey);
			}
		}

		return ArrayUtil.toLongArray(deletedRightPrimaryKeys);
	}

	@Override
	public long[] deleteTableMappings(
		long[] leftPrimaryKeys, long rightPrimaryKey) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.deleteTableMappings(leftPrimaryKeys, rightPrimaryKey);
		}

		long[] currentLeftPrimaryKeys = _getPrimaryKeys(
			_getCTLeftPrimaryKeysSqlQuery, rightPrimaryKey, ctCollectionId);

		List<Long> deletedLeftPrimaryKeys = new ArrayList<>();

		for (long leftPrimaryKey : leftPrimaryKeys) {
			if (Arrays.binarySearch(currentLeftPrimaryKeys, leftPrimaryKey) >=
					0) {

				_deleteTableMapping(
					leftPrimaryKey, rightPrimaryKey, ctCollectionId);

				deletedLeftPrimaryKeys.add(leftPrimaryKey);
			}
		}

		return ArrayUtil.toLongArray(deletedLeftPrimaryKeys);
	}

	@Override
	public List<L> getLeftBaseModels(
		long rightPrimaryKey, int start, int end, OrderByComparator<L> obc) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.getLeftBaseModels(rightPrimaryKey, start, end, obc);
		}

		return _getBaseModels(
			_getCTLeftPrimaryKeysSqlQuery, rightPrimaryKey, ctCollectionId,
			leftBasePersistence, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeys(long rightPrimaryKey) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.getLeftPrimaryKeys(rightPrimaryKey);
		}

		return _getPrimaryKeys(
			_getCTLeftPrimaryKeysSqlQuery, rightPrimaryKey, ctCollectionId);
	}

	@Override
	public List<R> getRightBaseModels(
		long leftPrimaryKey, int start, int end, OrderByComparator<R> obc) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.getRightBaseModels(leftPrimaryKey, start, end, obc);
		}

		return _getBaseModels(
			_getCTRightPrimaryKeysSqlQuery, leftPrimaryKey, ctCollectionId,
			rightBasePersistence, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeys(long leftPrimaryKey) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			return super.getRightPrimaryKeys(leftPrimaryKey);
		}

		return _getPrimaryKeys(
			_getCTRightPrimaryKeysSqlQuery, leftPrimaryKey, ctCollectionId);
	}

	@Override
	protected void init(
		String tableName, String companyColumnName, String leftColumnName,
		String rightColumnName) {

		DataSource dataSource = leftBasePersistence.getDataSource();

		addTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			StringBundler.concat(
				"INSERT INTO ", tableName, " (", companyColumnName, ", ",
				leftColumnName, ", ", rightColumnName,
				", ctCollectionId) VALUES (?, ?, ?, 0)"),
			ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT);

		if (cacheless) {
			containsTableMappingSQL =
				MappingSqlQueryFactoryUtil.getMappingSqlQuery(
					dataSource,
					StringBundler.concat(
						"SELECT * FROM ", tableName, " WHERE ", leftColumnName,
						" = ? AND ", rightColumnName,
						" = ? AND ctCollectionId = 0"),
					RowMapper.COUNT, ParamSetter.BIGINT, ParamSetter.BIGINT);
		}

		deleteLeftPrimaryKeyTableMappingsSqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource,
				StringBundler.concat(
					"DELETE FROM ", tableName, " WHERE ", leftColumnName,
					" = ? AND ctCollectionId = 0"),
				ParamSetter.BIGINT);
		deleteRightPrimaryKeyTableMappingsSqlUpdate =
			SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource,
				StringBundler.concat(
					"DELETE FROM ", tableName, " WHERE ", rightColumnName,
					" = ? AND ctCollectionId = 0"),
				ParamSetter.BIGINT);
		deleteTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			StringBundler.concat(
				"DELETE FROM ", tableName, " WHERE ", leftColumnName,
				" = ? AND ", rightColumnName, " = ? AND ctCollectionId = 0"),
			ParamSetter.BIGINT, ParamSetter.BIGINT);
		getLeftPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				StringBundler.concat(
					"SELECT ", leftColumnName, " FROM ", tableName, " WHERE ",
					rightColumnName, " = ? AND ctCollectionId = 0"),
				RowMapper.PRIMARY_KEY, ParamSetter.BIGINT);
		getRightPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				StringBundler.concat(
					"SELECT ", rightColumnName, " FROM ", tableName, " WHERE ",
					leftColumnName, " = ? AND ctCollectionId = 0"),
				RowMapper.PRIMARY_KEY, ParamSetter.BIGINT);

		reverseTableMapper = new ReverseTableMapper<>(this);

		_addCTTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			StringBundler.concat(
				"INSERT INTO ", tableName, " (", companyColumnName, ", ",
				leftColumnName, ", ", rightColumnName,
				", ctCollectionId, changeType) VALUES (?, ?, ?, ?, ?)"),
			ParamSetter.BIGINT, ParamSetter.BIGINT, ParamSetter.BIGINT,
			ParamSetter.BIGINT, _booleanParamSetter);

		DB db = DBManagerUtil.getDB();

		if (cacheless) {
			_containsCTTableMappingSQL =
				MappingSqlQueryFactoryUtil.getMappingSqlQuery(
					dataSource,
					StringBundler.concat(
						"SELECT * FROM ", tableName, " WHERE ", leftColumnName,
						" = ? AND ", rightColumnName,
						" = ? AND (ctCollectionId = 0 OR ctCollectionId = ?) ",
						"and (changeType is NULL or changeType = ",
						db.getTemplateTrue(), ")"),
					RowMapper.COUNT, ParamSetter.BIGINT, ParamSetter.BIGINT,
					ParamSetter.BIGINT);
		}

		_getCTLeftPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				StringBundler.concat(
					"SELECT DISTINCT (", leftColumnName, ") FROM ", tableName,
					" WHERE (", rightColumnName, " = ? AND ", leftColumnName,
					" NOT IN (SELECT ", leftColumnName, " FROM ", tableName,
					" WHERE ", rightColumnName, " = ? AND ctCollectionId = ? ",
					"AND changeType = ", db.getTemplateFalse(), ") AND ",
					"ctCollectionId = 0) OR (ctCollectionId = ? AND ",
					"changeType = ", db.getTemplateTrue(), ")"),
				RowMapper.PRIMARY_KEY, ParamSetter.BIGINT, ParamSetter.BIGINT,
				ParamSetter.BIGINT, ParamSetter.BIGINT);
		_getCTRightPrimaryKeysSqlQuery =
			MappingSqlQueryFactoryUtil.getMappingSqlQuery(
				dataSource,
				StringBundler.concat(
					"SELECT DISTINCT (", rightColumnName, ") FROM ", tableName,
					" WHERE (", leftColumnName, " = ? AND ", rightColumnName,
					" NOT IN (SELECT ", rightColumnName, " FROM ", tableName,
					" WHERE ", leftColumnName, " = ? AND ctCollectionId = ? ",
					"AND changeType = ", db.getTemplateFalse(), ") AND ",
					"ctCollectionId = 0) OR (ctCollectionId = ? AND ",
					"changeType = ", db.getTemplateTrue(), ")"),
				RowMapper.PRIMARY_KEY, ParamSetter.BIGINT, ParamSetter.BIGINT,
				ParamSetter.BIGINT, ParamSetter.BIGINT);

		_updateCTTableMappingSqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
			dataSource,
			StringBundler.concat(
				"UPDATE ", tableName, " SET changeType = ? WHERE ",
				leftColumnName, " = ? and ", rightColumnName,
				" = ? and ctCollectionId = ?"),
			_booleanParamSetter, ParamSetter.BIGINT, ParamSetter.BIGINT,
			ParamSetter.BIGINT);
	}

	private static <T extends BaseModel<T>> List<T> _getBaseModels(
		MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
		long ctCollectionId, BasePersistence<T> slaveBasePersistence, int start,
		int end, OrderByComparator<T> obc) {

		long[] slavePrimaryKeys = _getPrimaryKeys(
			mappingSqlQuery, masterPrimaryKey, ctCollectionId);

		if (slavePrimaryKeys.length == 0) {
			return Collections.emptyList();
		}

		List<T> slaveBaseModels = new ArrayList<>(slavePrimaryKeys.length);

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
			slaveBaseModels.sort(obc);
		}

		return ListUtil.subList(slaveBaseModels, start, end);
	}

	private static long[] _getPrimaryKeys(
		MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
		long ctCollectionId) {

		List<Long> primaryKeysList = null;

		try {
			primaryKeysList = mappingSqlQuery.execute(
				masterPrimaryKey, masterPrimaryKey, ctCollectionId,
				ctCollectionId);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		long[] primaryKeys = new long[primaryKeysList.size()];

		for (int i = 0; i < primaryKeys.length; i++) {
			primaryKeys[i] = primaryKeysList.get(i);
		}

		Arrays.sort(primaryKeys);

		return primaryKeys;
	}

	private void _addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey,
		long ctCollectionId) {

		ModelListener<L>[] leftModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(leftModelClass);

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeAddAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		ModelListener<R>[] rightModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(rightModelClass);

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeAddAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		_addTableMapping(
			companyId, leftPrimaryKey, rightPrimaryKey, ctCollectionId, true);

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onAfterAddAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onAfterAddAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}
	}

	private void _addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey,
		long ctCollectionId, boolean changeType) {

		try {
			int count = _updateCTTableMappingSqlUpdate.update(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId, changeType);

			if (count == 0) {
				_addCTTableMappingSqlUpdate.update(
					companyId, leftPrimaryKey, rightPrimaryKey, ctCollectionId,
					changeType);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private boolean _containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey, long ctCollectionId) {

		List<Integer> counts = null;

		try {
			counts = _containsCTTableMappingSQL.execute(
				leftPrimaryKey, rightPrimaryKey, ctCollectionId);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (counts.isEmpty()) {
			return false;
		}

		int count = counts.get(0);

		if (count == 0) {
			return false;
		}

		return true;
	}

	private void _deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey, long ctCollectionId) {

		ModelListener<L>[] leftModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(leftModelClass);

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onBeforeRemoveAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		ModelListener<R>[] rightModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(rightModelClass);

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onBeforeRemoveAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}

		_addTableMapping(
			CompanyThreadLocal.getCompanyId(), leftPrimaryKey, rightPrimaryKey,
			ctCollectionId, false);

		for (ModelListener<L> leftModelListener : leftModelListeners) {
			leftModelListener.onAfterRemoveAssociation(
				leftPrimaryKey, rightModelClass.getName(), rightPrimaryKey);
		}

		for (ModelListener<R> rightModelListener : rightModelListeners) {
			rightModelListener.onAfterRemoveAssociation(
				rightPrimaryKey, leftModelClass.getName(), leftPrimaryKey);
		}
	}

	private <M extends BaseModel<M>, S extends BaseModel<S>> int
		_deleteTableMappings(
			Class<M> masterModelClass, Class<S> slaveModelClass,
			MappingSqlQuery<Long> mappingSqlQuery, long masterPrimaryKey,
			long ctCollectionId, boolean leftToRight) {

		ModelListener<M>[] masterModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(masterModelClass);
		ModelListener<S>[] slaveModelListeners =
			ModelListenerRegistrationUtil.getModelListeners(slaveModelClass);

		long[] slavePrimaryKeys = _getPrimaryKeys(
			mappingSqlQuery, masterPrimaryKey, ctCollectionId);

		if ((masterModelListeners.length > 0) ||
			(slaveModelListeners.length > 0)) {

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

		long companyId = CompanyThreadLocal.getCompanyId();

		for (long slavePrimaryKey : slavePrimaryKeys) {
			if (leftToRight) {
				_addTableMapping(
					companyId, masterPrimaryKey, slavePrimaryKey,
					ctCollectionId, false);
			}
			else {
				_addTableMapping(
					companyId, slavePrimaryKey, masterPrimaryKey,
					ctCollectionId, false);
			}
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

		return slavePrimaryKeys.length;
	}

	private static final ParamSetter _booleanParamSetter =
		(ps, index, param) -> ps.setBoolean(index, (boolean)param);

	private SqlUpdate _addCTTableMappingSqlUpdate;
	private MappingSqlQuery<Integer> _containsCTTableMappingSQL;
	private MappingSqlQuery<Long> _getCTLeftPrimaryKeysSqlQuery;
	private MappingSqlQuery<Long> _getCTRightPrimaryKeysSqlQuery;
	private SqlUpdate _updateCTTableMappingSqlUpdate;

}