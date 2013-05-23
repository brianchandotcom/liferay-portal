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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;

import java.io.Serializable;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class ClassLoaderSession implements Session {

	public ClassLoaderSession(Session session, ClassLoader classLoader) {
		_session = session;
		_classLoader = classLoader;
	}

	@NotPrivileged
	public void clear() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			_session.clear();
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Connection close() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.close();
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public boolean contains(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.contains(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Query createQuery(String queryString) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.createQuery(queryString);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.createQuery(queryString, strictName);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.createSQLQuery(queryString);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.createSQLQuery(queryString, strictName);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public void delete(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			_session.delete(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public void evict(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			_session.evict(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public void flush() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			_session.flush();
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.get(clazz, id);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.get(clazz, id, lockMode);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Object getWrappedSession() throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.getWrappedSession();
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.load(clazz, id);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Object merge(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.merge(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public Serializable save(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			return _session.save(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@NotPrivileged
	public void saveOrUpdate(Object object) throws ORMException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			_session.saveOrUpdate(object);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private ClassLoader _classLoader;
	private Session _session;

}