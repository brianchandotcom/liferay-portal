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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

/**
 * @author Shuyang Zhou
 * @author Alexander Chow
 */
@ProviderType
public class PortletSessionFactoryImpl extends SessionFactoryImpl {

	@Override
	public void closeSession(Session session) throws ORMException {
		if (session != null) {
			session.flush();

			if (!PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
				session.close();
			}
		}
	}

	@Override
	public Session openSession() throws ORMException {
		SessionFactory sessionFactory = getSessionFactoryImplementor();

		org.hibernate.Session session = null;

		if (PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
			Connection connection = CurrentConnectionUtil.getConnection(
				_dataSource);

			if (connection == null) {
				session = sessionFactory.getCurrentSession();
			}
			else {
				session = sessionFactory.openSession(connection);
			}
		}
		else {
			session = sessionFactory.openSession();
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

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected SessionFactory createSessionFactory(DataSource dataSource) {
		PortletHibernateConfiguration portletHibernateConfiguration =
			new PortletHibernateConfiguration(
				getSessionFactoryClassLoader(), dataSource);

		portletHibernateConfiguration.setDataSource(dataSource);

		SessionFactory sessionFactory = null;

		try {
			sessionFactory =
				portletHibernateConfiguration.buildSessionFactory();
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}

		return sessionFactory;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected DataSource getDataSource() {
		return _dataSource;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected SessionFactory getSessionFactory() {
		return getSessionFactoryImplementor();
	}

	@Override
	protected Session wrapSession(org.hibernate.Session session) {
		return DoPrivilegedUtil.wrapWhenActive(super.wrapSession(session));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSessionFactoryImpl.class);

	private DataSource _dataSource;

}