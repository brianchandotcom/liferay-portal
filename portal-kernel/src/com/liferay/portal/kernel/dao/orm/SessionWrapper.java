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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Shuyang Zhou
 */
public class SessionWrapper implements Session {

	public SessionWrapper(Session session) {
		this.session = session;
	}

	@Override
	public void apply(UnsafeConsumer<Connection, SQLException> unsafeConsumer)
		throws ORMException {

		session.apply(unsafeConsumer);
	}

	@Override
	public void clear() throws ORMException {
		session.clear();
	}

	@Override
	public Connection close() throws ORMException {
		return session.close();
	}

	@Override
	public boolean contains(Object object) throws ORMException {
		return session.contains(object);
	}

	@Override
	public Query createQuery(String queryString) throws ORMException {
		return session.createQuery(queryString);
	}

	@Override
	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		return session.createQuery(queryString, strictName);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		return session.createSQLQuery(queryString);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		return session.createSQLQuery(queryString, strictName);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(DSLQuery dslQuery)
		throws ORMException {

		return session.createSynchronizedSQLQuery(dslQuery);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(String queryString)
		throws ORMException {

		return session.createSynchronizedSQLQuery(queryString);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName)
		throws ORMException {

		return session.createSynchronizedSQLQuery(queryString, strictName);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName, String[] tableNames)
		throws ORMException {

		return session.createSynchronizedSQLQuery(
			queryString, strictName, tableNames);
	}

	@Override
	public void delete(Object object) throws ORMException {
		session.delete(object);
	}

	@Override
	public void evict(Class<?> clazz, Serializable id) throws ORMException {
		session.evict(clazz, id);
	}

	@Override
	public void evict(Object object) throws ORMException {
		session.evict(object);
	}

	@Override
	public void flush() throws ORMException {
		session.flush();
	}

	@Override
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		return session.get(clazz, id);
	}

	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		return session.get(clazz, id, lockMode);
	}

	@Override
	public Object getWrappedSession() throws ORMException {
		return session.getWrappedSession();
	}

	@Override
	public boolean isDirty() throws ORMException {
		return session.isDirty();
	}

	@Override
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		return session.load(clazz, id);
	}

	@Override
	public Object merge(Object object) throws ORMException {
		return session.merge(object);
	}

	@Override
	public Serializable save(Object object) throws ORMException {
		return session.save(object);
	}

	@Override
	public void saveOrUpdate(Object object) throws ORMException {
		session.saveOrUpdate(object);
	}

	protected final Session session;

}