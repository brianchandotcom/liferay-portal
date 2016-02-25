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

import java.io.Serializable;

import java.sql.Connection;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public interface Session {

	public void clear() throws ORMException;

	public Connection close() throws ORMException;

	public boolean contains(Object object) throws ORMException;

	public Query createQuery(String queryString) throws ORMException;

	public Query createQuery(String queryString, boolean strictName)
		throws ORMException;

	public SQLQuery createSQLQuery(String queryString) throws ORMException;

	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException;

	public SQLQuery createSynchronizedSQLQuery(String queryString)
		throws ORMException;

	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName)
		throws ORMException;

	public void delete(Object object) throws ORMException;

	public void evict(Object object) throws ORMException;

	public void flush() throws ORMException;

	public Object get(Class<?> clazz, Serializable id) throws ORMException;

	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException;

	public Object getWrappedSession() throws ORMException;

	public boolean isDirty() throws ORMException;

	public Object load(Class<?> clazz, Serializable id) throws ORMException;

	public Object merge(Object object) throws ORMException;

	public Serializable save(Object object) throws ORMException;

	public void saveOrUpdate(Object object) throws ORMException;

}