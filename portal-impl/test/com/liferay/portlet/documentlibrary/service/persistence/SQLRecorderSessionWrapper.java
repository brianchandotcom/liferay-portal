/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 */
public class SQLRecorderSessionWrapper implements Session {

	public SQLRecorderSessionWrapper(Session session) {
		_session = session;
	}

	public void clear() throws ORMException {
		_session.clear();
	}

	public Connection close() throws ORMException {
		return _session.close();
	}

	public boolean contains(Object object) throws ORMException {
		return _session.contains(object);
	}

	public Query createQuery(String queryString) throws ORMException {
		SQLRecorderThreadLocal.setSQL(queryString);
		return _session.createQuery(queryString);
	}

	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		SQLRecorderThreadLocal.setSQL(queryString);
		return _session.createQuery(queryString, strictName);
	}

	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		SQLRecorderThreadLocal.setSQL(queryString);
		return _session.createSQLQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		SQLRecorderThreadLocal.setSQL(queryString);
		return _session.createSQLQuery(queryString, strictName);
	}

	public void delete(Object object) throws ORMException {
		_session.delete(object);
	}

	public void evict(Object object) throws ORMException {
		_session.evict(object);
	}

	public void flush() throws ORMException {
		_session.flush();
	}

	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		return _session.get(clazz, id);
	}

	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		return _session.get(clazz, id, lockMode);
	}

	public Object getWrappedSession() throws ORMException {
		return _session.getWrappedSession();
	}

	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		return _session.load(clazz, id);
	}

	public Object merge(Object object) throws ORMException {
		return _session.merge(object);
	}

	public Serializable save(Object object) throws ORMException {
		return _session.save(object);
	}

	public void saveOrUpdate(Object object) throws ORMException {
		_session.saveOrUpdate(object);
	}

	private Session _session;

}