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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.event.EventSource;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SessionImpl implements Session {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #SessionImpl(org.hibernate.Session, ClassLoader)}
	 */
	@Deprecated
	public SessionImpl(org.hibernate.Session session) {
		this(session, null);
	}

	public SessionImpl(
		org.hibernate.Session session, ClassLoader sessionFactoryClassLoader) {

		_session = session;
		_sessionFactoryClassLoader = sessionFactoryClassLoader;
	}

	@Override
	public void apply(UnsafeConsumer<Connection, SQLException> unsafeConsumer)
		throws ORMException {

		try {
			_session.doWork(unsafeConsumer::accept);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public void clear() throws ORMException {
		try {
			_session.clear();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Connection close() throws ORMException {
		try {
			return _session.close();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public boolean contains(Object object) throws ORMException {
		try {
			return _session.contains(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Query createQuery(String queryString) throws ORMException {
		return createQuery(queryString, true);
	}

	@Override
	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		if (_sessionFactoryClassLoader == null) {
			return _createQuery(queryString, strictName);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_sessionFactoryClassLoader);

		try {
			return _createQuery(queryString, strictName);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		return createSQLQuery(queryString, true);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		try {
			queryString = SQLTransformer.transformFromJPQLToHQL(queryString);

			return new SQLQueryImpl(
				_session.createSQLQuery(queryString), strictName);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(DSLQuery dslQuery)
		throws ORMException {

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		SQLQuery sqlQuery = createSynchronizedSQLQuery(
			dslQuery.toSQL(defaultASTNodeListener), true,
			defaultASTNodeListener.getTableNames());

		List<Object> scalarValues = defaultASTNodeListener.getScalarValues();

		if (!scalarValues.isEmpty()) {
			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (Object value : scalarValues) {
				queryPos.add(value);
			}
		}

		return sqlQuery;
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(String queryString)
		throws ORMException {

		return createSynchronizedSQLQuery(queryString, true);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName)
		throws ORMException {

		return createSynchronizedSQLQuery(
			queryString, strictName,
			SQLQueryTableNamesUtil.getTableNames(queryString));
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName, String[] tableNames)
		throws ORMException {

		try {
			queryString = SQLTransformer.transformFromJPQLToHQL(queryString);

			SQLQuery sqlQuery = new SQLQueryImpl(
				_session.createSQLQuery(queryString), strictName);

			sqlQuery.addSynchronizedQuerySpaces(tableNames);

			return sqlQuery;
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public void delete(Object object) throws ORMException {
		try {
			_session.delete(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	public void evict(Class<?> clazz, Serializable id) throws ORMException {
		try {
			EventSource eventSource = (EventSource)_session;

			PersistenceContext persistenceContext =
				eventSource.getPersistenceContext();

			SessionFactoryImplementor sessionFactoryImplementor =
				eventSource.getFactory();

			Object object = persistenceContext.getEntity(
				new EntityKey(
					id,
					sessionFactoryImplementor.getEntityPersister(
						clazz.getName()),
					eventSource.getEntityMode()));

			if (object == null) {
				return;
			}

			eventSource.evict(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public void evict(Object object) throws ORMException {
		try {
			_session.evict(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public void flush() throws ORMException {
		try {
			_session.flush();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.get(clazz, id);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		LockOptions lockOptions = new LockOptions(
			LockModeTranslator.translate(lockMode));

		try {
			return _session.get(clazz, id, lockOptions);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object getWrappedSession() {
		return _session;
	}

	@Override
	public boolean isDirty() throws ORMException {
		try {
			return _session.isDirty();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.load(clazz, id);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object merge(Object object) throws ORMException {
		try {
			return _session.merge(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception, _session, object);
		}
	}

	@Override
	public Serializable save(Object object) throws ORMException {
		try {
			return _session.save(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public void saveOrUpdate(Object object) throws ORMException {
		try {
			_session.saveOrUpdate(object);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception, _session, object);
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{_session=");
		sb.append(String.valueOf(_session));
		sb.append("}");

		return sb.toString();
	}

	private Query _createQuery(String queryString, boolean strictName)
		throws ORMException {

		try {
			queryString = SQLTransformer.transformFromJPQLToHQL(queryString);

			return new QueryImpl(_session.createQuery(queryString), strictName);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	private final org.hibernate.Session _session;
	private final ClassLoader _sessionFactoryClassLoader;

}