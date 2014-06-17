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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NullSafeStringComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.BasePersistence;

import java.io.Serializable;

import java.sql.Connection;

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
			Set<Serializable> primaryKeys)
		throws SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unused")
	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(DynamicQuery dynamicQuery) {
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
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(
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
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {

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
		return listeners;
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
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
			_log.error("Caught unexpected exception " + e.getClass().getName());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(e, e);
		}

		return new SystemException(e);
	}

	@Override
	public void registerListener(ModelListener<T> listener) {
		List<ModelListener<T>> listenersList = ListUtil.fromArray(listeners);

		listenersList.add(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	@Override
	@SuppressWarnings("unused")
	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(T model) {
		if (model instanceof ModelWrapper) {
			ModelWrapper<T> modelWrapper = (ModelWrapper<T>)model;

			model = modelWrapper.getWrappedModel();
		}

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
		_db = DBFactoryUtil.getDB(_dialect);
	}

	@Override
	public void unregisterListener(ModelListener<T> listener) {
		List<ModelListener<T>> listenersList = ListUtil.fromArray(listeners);

		listenersList.remove(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	@Override
	public T update(T model) {
		if (model instanceof ModelWrapper) {
			ModelWrapper<T> modelWrapper = (ModelWrapper<T>)model;

			model = modelWrapper.getWrappedModel();
		}

		boolean isNew = model.isNew();

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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #update(BaseModel)}}
	 */
	@Deprecated
	@Override
	public T update(T model, boolean merge) {
		if (model instanceof ModelWrapper) {
			ModelWrapper<T> modelWrapper = (ModelWrapper<T>)model;

			model = modelWrapper.getWrappedModel();
		}

		boolean isNew = model.isNew();

		for (ModelListener<T> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(model);
			}
			else {
				listener.onBeforeUpdate(model);
			}
		}

		model = updateImpl(model, merge);

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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #update(BaseModel,
	 *             ServiceContext)}}
	 */
	@Deprecated
	@Override
	public T update(T model, boolean merge, ServiceContext serviceContext) {
		return update(model, serviceContext);
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
		OrderByComparator orderByComparator) {

		appendOrderByComparator(query, entityAlias, orderByComparator, false);
	}

	protected void appendOrderByComparator(
		StringBundler query, String entityAlias,
		OrderByComparator orderByComparator, boolean sqlQuery) {

		query.append(ORDER_BY_CLAUSE);

		String[] orderByFields = orderByComparator.getOrderByFields();

		for (int i = 0; i < orderByFields.length; i++) {
			query.append(entityAlias);
			query.append(orderByFields[i]);

			if (sqlQuery) {
				Set<String> badColumnNames = getBadColumnNames();

				if (badColumnNames.contains(orderByFields[i])) {
					query.append(StringPool.UNDERLINE);
				}
			}

			if ((i + 1) < orderByFields.length) {
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

	protected Set<String> getBadColumnNames() {
		return Collections.emptySet();
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateImpl(BaseModel)}
	 */
	@Deprecated
	protected T updateImpl(T model, boolean merge) {
		return updateImpl(model);
	}

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

	protected ModelListener<T>[] listeners = new ModelListener[0];

	private static Log _log = LogFactoryUtil.getLog(BasePersistenceImpl.class);

	private DataSource _dataSource;
	private DB _db;
	private Dialect _dialect;
	private Class<T> _modelClass;
	private SessionFactory _sessionFactory;

}