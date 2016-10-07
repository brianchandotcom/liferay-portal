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

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NullSafeStringComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.Types;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * The base implementation for all persistence classes. This class should never
 * need to be used directly.
 *
 * <p>
 * Caching information and settings can be found in
 * <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class BasePersistenceImpl<T extends BaseModel<T>>
	implements BasePersistence<T>, SessionFactory {

	public static final String COUNT_COLUMN_NAME = "COUNT_VALUE";

	@Override
	public void clearCache() {
	}

	@Override
	public void clearCache(List<T> model) {
	}

	@Override
	public void clearCache(T model) {
	}

	@Override
	public void closeSession(Session session) {
		_sessionFactory.closeSession(session);
	}

	@Override
	public long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return countWithDynamicQuery(
			dynamicQuery, ProjectionFactoryUtil.rowCount());
	}

	@Override
	public long countWithDynamicQuery(
		DynamicQuery dynamicQuery, Projection projection) {

		if (projection == null) {
			projection = ProjectionFactoryUtil.rowCount();
		}

		dynamicQuery.setProjection(projection);

		List<Long> results = findWithDynamicQuery(dynamicQuery);

		if (results.isEmpty()) {
			return 0;
		}
		else {
			return (results.get(0)).longValue();
		}
	}

	@Override
	public T fetchByPrimaryKey(Serializable primaryKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<Serializable, T> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unused")
	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException {

		throw new UnsupportedOperationException();
	}

	@Override
	public <V> List<V> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public <V> List<V> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public <V> List<V> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<V> orderByComparator) {

		OrderFactoryUtil.addOrderByComparator(dynamicQuery, orderByComparator);

		return findWithDynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public void flush() {
		try {
			Session session = _sessionFactory.getCurrentSession();

			if (session != null) {
				session.flush();
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return Collections.emptySet();
	}

	@Override
	public Session getCurrentSession() throws ORMException {
		return _sessionFactory.getCurrentSession();
	}

	@Override
	public DataSource getDataSource() {
		return _dataSource;
	}

	public DB getDB() {
		return _db;
	}

	@Override
	public Dialect getDialect() {
		return _dialect;
	}

	@Override
	public ModelListener<T>[] getListeners() {
		return ModelListenerRegistrationUtil.getModelListeners(getModelClass());
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
	}

	public Object getTransactionManager() {
		return _transactionManager;
	}

	@Override
	public Session openNewSession(Connection connection) throws ORMException {
		return _sessionFactory.openNewSession(connection);
	}

	@Override
	public Session openSession() throws ORMException {
		return _sessionFactory.openSession();
	}

	@Override
	public SystemException processException(Exception e) {
		if (!(e instanceof ORMException)) {
			Class<?> clazz = e.getClass();

			_log.error("Caught unexpected exception " + clazz.getName());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(e, e);
		}

		return new SystemException(e);
	}

	@Override
	public void registerListener(ModelListener<T> listener) {
		ModelListenerRegistrationUtil.register(listener);
	}

	@Override
	@SuppressWarnings("unused")
	public T remove(Serializable primaryKey) throws NoSuchModelException {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(T model) {
		if (model instanceof ModelWrapper) {
			ModelWrapper<T> modelWrapper = (ModelWrapper<T>)model;

			model = modelWrapper.getWrappedModel();
		}

		ModelListener<T>[] listeners = getListeners();

		for (ModelListener<T> listener : listeners) {
			listener.onBeforeRemove(model);
		}

		model = removeImpl(model);

		for (ModelListener<T> listener : listeners) {
			listener.onAfterRemove(model);
		}

		return model;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;

		_dialect = _sessionFactory.getDialect();

		_db = DBManagerUtil.getDB(_dialect, getDataSource());

		DBType dbType = _db.getDBType();

		_databaseOrderByMaxColumns = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.DATABASE_ORDER_BY_MAX_COLUMNS,
				new Filter(dbType.getName())));
	}

	public void setTransactionManager(Object transactionManager) {
		_transactionManager = transactionManager;
	}

	@Override
	public void unregisterListener(ModelListener<T> listener) {
		ModelListenerRegistrationUtil.unregister(listener);
	}

	@Override
	public T update(T model) {
		if (model instanceof ModelWrapper) {
			ModelWrapper<T> modelWrapper = (ModelWrapper<T>)model;

			model = modelWrapper.getWrappedModel();
		}

		boolean isNew = model.isNew();

		ModelListener<T>[] listeners = getListeners();

		for (ModelListener<T> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(model);
			}
			else {
				listener.onBeforeUpdate(model);
			}
		}

		model = updateImpl(model);

		for (ModelListener<T> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(model);
			}
			else {
				listener.onAfterUpdate(model);
			}
		}

		return model;
	}

	@Override
	public T update(T model, ServiceContext serviceContext) {
		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			update(model);

			return model;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected static String removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	protected void appendOrderByComparator(
		StringBundler query, String entityAlias,
		OrderByComparator<T> orderByComparator) {

		appendOrderByComparator(query, entityAlias, orderByComparator, false);
	}

	protected void appendOrderByComparator(
		StringBundler query, String entityAlias,
		OrderByComparator<T> orderByComparator, boolean sqlQuery) {

		query.append(ORDER_BY_CLAUSE);

		String[] orderByFields = orderByComparator.getOrderByFields();

		int length = orderByFields.length;

		if ((_databaseOrderByMaxColumns > 0) &&
			(_databaseOrderByMaxColumns < length)) {

			length = _databaseOrderByMaxColumns;
		}

		for (int i = 0; i < length; i++) {
			query.append(
				getColumnName(entityAlias, orderByFields[i], sqlQuery));

			if ((i + 1) < length) {
				if (orderByComparator.isAscending(orderByFields[i])) {
					query.append(ORDER_BY_ASC_HAS_NEXT);
				}
				else {
					query.append(ORDER_BY_DESC_HAS_NEXT);
				}
			}
			else {
				if (orderByComparator.isAscending(orderByFields[i])) {
					query.append(ORDER_BY_ASC);
				}
				else {
					query.append(ORDER_BY_DESC);
				}
			}
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected String getColumnName(
		String entityAlias, String fieldName, boolean sqlQuery) {

		String columnName = fieldName;

		Set<String> badColumnNames = getBadColumnNames();

		if (badColumnNames.contains(fieldName)) {
			columnName = columnName.concat(StringPool.UNDERLINE);
		}

		if (sqlQuery) {
			fieldName = columnName;
		}

		fieldName = entityAlias.concat(fieldName);

		Map<String, Integer> tableColumnsMap = getTableColumnsMap();

		Integer type = tableColumnsMap.get(columnName);

		if (type == null) {
			throw new IllegalArgumentException(
				"Unknown column name " + columnName);
		}

		if (type == Types.CLOB) {
			fieldName = CAST_CLOB_TEXT_OPEN.concat(fieldName).concat(
				StringPool.CLOSE_PARENTHESIS);
		}

		return fieldName;
	}

	protected Map<String, Integer> getTableColumnsMap() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the model instance from the database. {@link #update(BaseModel,
	 * boolean)} depends on this method to implement the remove operation; it
	 * only notifies the model listeners.
	 *
	 * @param  model the model instance to remove
	 * @return the model instance that was removed
	 */
	protected T removeImpl(T model) {
		throw new UnsupportedOperationException();
	}

	protected void setModelClass(Class<T> modelClass) {
		_modelClass = modelClass;
	}

	/**
	 * Updates the model instance in the database or adds it if it does not yet
	 * exist. {@link #remove(BaseModel)} depends on this method to implement the
	 * update operation; it only notifies the model listeners.
	 *
	 * @param  model the model instance to update
	 * @return the model instance that was updated
	 */
	protected T updateImpl(T model) {
		throw new UnsupportedOperationException();
	}

	protected static final String CAST_CLOB_TEXT_OPEN = "CAST_CLOB_TEXT(";

	protected static final Object[] FINDER_ARGS_EMPTY = new Object[0];

	protected static final Comparator<String> NULL_SAFE_STRING_COMPARATOR =
		new NullSafeStringComparator();

	protected static final String ORDER_BY_ASC = " ASC";

	protected static final String ORDER_BY_ASC_HAS_NEXT = " ASC, ";

	protected static final String ORDER_BY_CLAUSE = " ORDER BY ";

	protected static final String ORDER_BY_DESC = " DESC";

	protected static final String ORDER_BY_DESC_HAS_NEXT = " DESC, ";

	protected static final String WHERE_AND = " AND ";

	protected static final String WHERE_GREATER_THAN = " >= ? ";

	protected static final String WHERE_GREATER_THAN_HAS_NEXT = " >= ? AND ";

	protected static final String WHERE_LESSER_THAN = " <= ? ";

	protected static final String WHERE_LESSER_THAN_HAS_NEXT = " <= ? AND ";

	protected static final String WHERE_OR = " OR ";

	protected static final NullModel nullModel = new NullModel();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected ModelListener<T>[] listeners = new ModelListener[0];

	private static final Log _log = LogFactoryUtil.getLog(
		BasePersistenceImpl.class);

	private int _databaseOrderByMaxColumns;
	private DataSource _dataSource;
	private DB _db;
	private Dialect _dialect;
	private Class<T> _modelClass;
	private SessionFactory _sessionFactory;
	private Object _transactionManager;

	private static class NullModel
		implements BaseModel<NullModel>, CacheModel<NullModel>, MVCCModel {

		@Override
		public Object clone() {
			return this;
		}

		@Override
		public int compareTo(NullModel nullModel) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ExpandoBridge getExpandoBridge() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Map<String, Object> getModelAttributes() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> getModelClass() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getModelClassName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isCachedModel() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEntityCacheEnabled() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEscapedModel() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isFinderCacheEnabled() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isNew() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void resetOriginalValues() {
		}

		@Override
		public void setCachedModel(boolean cachedModel) {
		}

		@Override
		public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setModelAttributes(Map<String, Object> attributes) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public void setNew(boolean n) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			throw new UnsupportedOperationException();
		}

		@Override
		public CacheModel<NullModel> toCacheModel() {
			return this;
		}

		@Override
		public NullModel toEntityModel() {
			return this;
		}

		@Override
		public NullModel toEscapedModel() {
			throw new UnsupportedOperationException();
		}

		@Override
		public NullModel toUnescapedModel() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toXmlString() {
			throw new UnsupportedOperationException();
		}

	}

}