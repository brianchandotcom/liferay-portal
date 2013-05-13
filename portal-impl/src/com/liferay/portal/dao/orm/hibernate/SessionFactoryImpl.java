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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.ClassLoaderSession;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SessionFactoryImpl implements SessionFactory {

	public static List<PortletSessionFactoryImpl> getPortletSessionFactories() {
		return portletSessionFactories;
	}

	@Override
	public void closeSession(Session session) throws ORMException {
		if ((session != null) &&
			!PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {

			session.flush();
			session.close();
		}
	}

	public void destroy() {
		portletSessionFactories.clear();
	}

	@Override
	public Session getCurrentSession() throws ORMException {
		return wrapSession(_sessionFactoryImplementor.getCurrentSession());
	}

	@Override
	public Dialect getDialect() throws ORMException {
		return new DialectImpl(_sessionFactoryImplementor.getDialect());
	}

	public ClassLoader getSessionFactoryClassLoader() {
		return _sessionFactoryClassLoader;
	}

	public SessionFactoryImplementor getSessionFactoryImplementor() {
		return _sessionFactoryImplementor;
	}

	@Override
	public Session openNewSession(Connection connection) throws ORMException {
		return wrapSession(_sessionFactoryImplementor.openSession(connection));
	}

	@Override
	public Session openSession() throws ORMException {
		org.hibernate.Session session = null;

		if (PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
			session = _sessionFactoryImplementor.getCurrentSession();
		}
		else {
			session = _sessionFactoryImplementor.openSession();
		}

		if (_log.isDebugEnabled()) {
			org.hibernate.impl.SessionImpl sessionImpl =
				(org.hibernate.impl.SessionImpl)session;

			_log.debug(
				"Session is using connection release mode " +
					sessionImpl.getConnectionReleaseMode());
		}

		return wrapSession(session);
	}

	public void setSessionFactoryClassLoader(
		ClassLoader sessionFactoryClassLoader) {

		ClassLoader portalClassLoader = ClassLoaderUtil.getPortalClassLoader();

		if (sessionFactoryClassLoader == portalClassLoader) {
			_sessionFactoryClassLoader = sessionFactoryClassLoader;
		}
		else {
			_sessionFactoryClassLoader = new PreloadClassLoader(
				sessionFactoryClassLoader, getPreloadClassLoaderClasses());
		}
	}

	public void setSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor) {

		_sessionFactoryImplementor = sessionFactoryImplementor;
	}

	protected Map<String, Class<?>> getPreloadClassLoaderClasses() {
		try {
			Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

			for (String className : _PRELOAD_CLASS_NAMES) {
				ClassLoader portalClassLoader =
					ClassLoaderUtil.getPortalClassLoader();

				Class<?> clazz = portalClassLoader.loadClass(className);

				classes.put(className, clazz);
			}

			return classes;
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}

	protected Session wrapSession(org.hibernate.Session session) {
		Session liferaySession = new SessionImpl(session);

		if (_sessionFactoryClassLoader != null) {

			// LPS-4190

			liferaySession = new ClassLoaderSession(
				liferaySession, _sessionFactoryClassLoader);
		}

		return liferaySession;
	}

	private static final String[] _PRELOAD_CLASS_NAMES =
		PropsValues.
			SPRING_HIBERNATE_SESSION_FACTORY_PRELOAD_CLASSLOADER_CLASSES;

	private static Log _log = LogFactoryUtil.getLog(SessionFactoryImpl.class);

	protected static final List<PortletSessionFactoryImpl>
		portletSessionFactories =
			new CopyOnWriteArrayList<PortletSessionFactoryImpl>();

	private ClassLoader _sessionFactoryClassLoader;
	private SessionFactoryImplementor _sessionFactoryImplementor;

}