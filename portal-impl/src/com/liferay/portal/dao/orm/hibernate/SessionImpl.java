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

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.security.lang.DoPrivilegedUtil;

import java.io.Serializable;

import java.sql.Connection;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class SessionImpl implements Session {

	public SessionImpl(org.hibernate.Session session) {
		_session = session;
	}

	@NotPrivileged
	@Override
	public void clear() throws ORMException {
		try {
			_session.clear();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public Connection close() throws ORMException {
		try {
			return _session.close();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public boolean contains(Object object) throws ORMException {
		try {
			return _session.contains(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Query createQuery(String queryString) throws ORMException {
		return createQuery(queryString, true);
	}

	@Override
	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		try {
			queryString = SQLTransformer.transformFromJpqlToHql(queryString);

			return DoPrivilegedUtil.wrapWhenActive(
				new QueryImpl(_session.createQuery(queryString), strictName)
			);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
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
			queryString = SQLTransformer.transformFromJpqlToHql(queryString);

			return DoPrivilegedUtil.wrapWhenActive(
				new SQLQueryImpl(
					_session.createSQLQuery(queryString), strictName)
			);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
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

		try {
			queryString = SQLTransformer.transformFromJpqlToHql(queryString);

			SQLQuery sqlQuery = new SQLQueryImpl(
				_session.createSQLQuery(queryString), strictName);

			String[] tableNames = SQLQueryTableNamesUtil.getTableNames(
				queryString);

			sqlQuery.addSynchronizedQuerySpaces(tableNames);

			return DoPrivilegedUtil.wrapWhenActive(sqlQuery);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public void delete(Object object) throws ORMException {
		try {
			_session.delete(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public void evict(Object object) throws ORMException {
		try {
			_session.evict(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public void flush() throws ORMException {
		try {
			_session.flush();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.get(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@NotPrivileged
	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		try {
			return _session.get(
				clazz, id, LockModeTranslator.translate(lockMode));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public Object getWrappedSession() {
		return _session;
	}

	@NotPrivileged
	@Override
	public boolean isDirty() throws ORMException {
		try {
			return _session.isDirty();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return _session.load(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public Object merge(Object object) throws ORMException {
		try {
			return _session.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e, _session, object);
		}
	}

	@NotPrivileged
	@Override
	public Serializable save(Object object) throws ORMException {
		try {
			return _session.save(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@NotPrivileged
	@Override
	public void saveOrUpdate(Object object) throws ORMException {
		try {
			_session.saveOrUpdate(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e, _session, object);
		}
	}

	private final org.hibernate.Session _session;

}