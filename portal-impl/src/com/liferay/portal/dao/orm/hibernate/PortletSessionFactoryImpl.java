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

import com.liferay.portal.dao.shard.ShardDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Shuyang Zhou
 * @author Alexander Chow
 */
public class PortletSessionFactoryImpl
	extends SessionFactoryImpl implements BeanFactoryAware {

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
		SessionFactory sessionFactory = getSessionFactory();

		org.hibernate.Session session = null;

		if (PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
			Connection connection = CurrentConnectionUtil.getConnection(
				getDataSource());

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

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	protected SessionFactory createSessionFactory(DataSource dataSource) {
		String servletContextName =
			PortletClassLoaderUtil.getServletContextName();

		ClassLoader classLoader = getSessionFactoryClassLoader();

		PortletClassLoaderUtil.setServletContextName(
			ClassLoaderPool.getContextName(classLoader));

		try {
			PortletHibernateConfiguration portletHibernateConfiguration =
				new PortletHibernateConfiguration();

			portletHibernateConfiguration.setBeanFactory(_beanFactory);
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
		finally {
			PortletClassLoaderUtil.setServletContextName(servletContextName);
		}
	}

	protected BeanFactory getBeanFactory() {
		return _beanFactory;
	}

	protected DataSource getDataSource() {
		ShardDataSourceTargetSource shardDataSourceTargetSource =
			(ShardDataSourceTargetSource)
				InfrastructureUtil.getShardDataSourceTargetSource();

		if (shardDataSourceTargetSource != null) {
			return shardDataSourceTargetSource.getDataSource();
		}
		else {
			return _dataSource;
		}
	}

	protected SessionFactory getSessionFactory() {
		ShardDataSourceTargetSource shardDataSourceTargetSource =
			(ShardDataSourceTargetSource)
				InfrastructureUtil.getShardDataSourceTargetSource();

		if (shardDataSourceTargetSource == null) {
			return getSessionFactoryImplementor();
		}

		DataSource dataSource = shardDataSourceTargetSource.getDataSource();

		SessionFactory sessionFactory = getSessionFactory(dataSource);

		if (sessionFactory != null) {
			return sessionFactory;
		}

		sessionFactory = createSessionFactory(dataSource);

		if (sessionFactory != null) {
			putSessionFactory(dataSource, sessionFactory);
		}

		return sessionFactory;
	}

	protected SessionFactory getSessionFactory(DataSource dataSource) {
		return _sessionFactories.get(dataSource);
	}

	protected void putSessionFactory(
		DataSource dataSource, SessionFactory sessionFactory) {

		_sessionFactories.put(dataSource, sessionFactory);
	}

	@Override
	protected Session wrapSession(org.hibernate.Session session) {
		return DoPrivilegedUtil.wrapWhenActive(super.wrapSession(session));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSessionFactoryImpl.class);

	private BeanFactory _beanFactory;
	private DataSource _dataSource;
	private final Map<DataSource, SessionFactory> _sessionFactories =
		new HashMap<DataSource, SessionFactory>();

}