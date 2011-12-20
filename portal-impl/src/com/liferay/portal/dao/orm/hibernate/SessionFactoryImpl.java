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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.ClassLoaderSession;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SessionFactoryImpl implements SessionFactory {

	public static List<PortletSessionFactoryImpl> getPortletSessionFactorys() {
		return portletSessionFactories;
	}

	public void destroy() {
		portletSessionFactories.clear();
	}

	public void closeSession(Session session) throws ORMException {
		if (!PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
			session.close();
		}
	}

	public Dialect getDialect() throws ORMException {
		return new DialectImpl(_sessionFactoryImplementor.getDialect());
	}

	public ClassLoader getSessionFactoryClassLoader() {
		return _sessionFactoryClassLoader;
	}

	public SessionFactoryImplementor getSessionFactoryImplementor() {
		return _sessionFactoryImplementor;
	}

	public Session openNewSession(Connection connection) throws ORMException {
		return wrapSession(_sessionFactoryImplementor.openSession(connection));
	}

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

		_sessionFactoryClassLoader = sessionFactoryClassLoader;
	}

	public void setSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor) {

		_sessionFactoryImplementor = sessionFactoryImplementor;
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

	protected static final List<PortletSessionFactoryImpl>
		portletSessionFactories =
			new CopyOnWriteArrayList<PortletSessionFactoryImpl>();

	private static Log _log = LogFactoryUtil.getLog(SessionFactoryImpl.class);

	private ClassLoader _sessionFactoryClassLoader;
	private SessionFactoryImplementor _sessionFactoryImplementor;

}