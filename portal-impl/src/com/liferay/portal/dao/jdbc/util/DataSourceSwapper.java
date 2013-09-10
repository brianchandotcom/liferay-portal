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

package com.liferay.portal.dao.jdbc.util;

import com.liferay.portal.dao.orm.hibernate.PortletSessionFactoryImpl;
import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.PortalHibernateConfiguration;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;
import com.liferay.portal.spring.jpa.LocalContainerEntityManagerFactoryBean;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class DataSourceSwapper {

	public static void swapCounterDataSource(Properties properties)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Create new counter data source");
		}

		DataSource newDataSource = DataSourceFactoryUtil.initDataSource(
			properties);

		DataSource oldDataSource =
			_counterDataSourceWrapper.getWrappedDataSource();

		if (_log.isInfoEnabled()) {
			_log.info("Set new counter data source");
		}

		_counterDataSourceWrapper.setWrappedDataSource(newDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("Destroy old counter data source");
		}

		DataSourceFactoryUtil.destroyDataSource(oldDataSource);

		if (StringUtil.equalsIgnoreCase(
				PropsValues.PERSISTENCE_PROVIDER, "jpa")) {

			if (_log.isInfoEnabled()) {
				_log.info("Reinitialize Hibernate for new counter data source");
			}

			_reinitializeJPA("counterSessionFactory", newDataSource);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Reinitialize JPA for new counter data source");
			}

			_reinitializeHibernate("counterSessionFactory", newDataSource);
		}
	}

	public static void swapLiferayDataSource(Properties properties)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Create new liferay data source");
		}

		DataSource newDataSource = DataSourceFactoryUtil.initDataSource(
			properties);

		DataSource oldDataSource =
			_liferayDataSourceWrapper.getWrappedDataSource();

		if (_log.isInfoEnabled()) {
			_log.info("Set new liferay data source");
		}

		_liferayDataSourceWrapper.setWrappedDataSource(newDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("Destroy old liferay data source");
		}

		DataSourceFactoryUtil.destroyDataSource(oldDataSource);

		if (StringUtil.equalsIgnoreCase(
				PropsValues.PERSISTENCE_PROVIDER, "jpa")) {

			if (_log.isInfoEnabled()) {
				_log.info("Reinitialize Hibernate for new liferay data source");
			}

			_reinitializeJPA("liferaySessionFactory", newDataSource);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Reinitialize JPA for new liferay data source");
			}

			_reinitializeHibernate("liferaySessionFactory", newDataSource);
		}

		_reinitializePortletsHibernate(newDataSource);
	}

	public void setCounterDataSourceWrapper(
		DataSourceWrapper counterDataSourceWrapper) {

		_counterDataSourceWrapper = counterDataSourceWrapper;
	}

	public void setLiferayDataSourceWrapper(
		DataSourceWrapper liferayDataSourceWrapper) {

		_liferayDataSourceWrapper = liferayDataSourceWrapper;
	}

	private static void _reinitializeHibernate(
			String name, DataSource dataSource)
		throws Exception {

		PortalHibernateConfiguration portalHibernateConfiguration =
			new PortalHibernateConfiguration();

		portalHibernateConfiguration.setDataSource(dataSource);

		portalHibernateConfiguration.afterPropertiesSet();

		SessionFactoryImplementor sessionFactoryImplementor =
			(SessionFactoryImplementor)portalHibernateConfiguration.getObject();

		SessionFactoryImpl sessionFactoryImpl =
			(SessionFactoryImpl)PortalBeanLocatorUtil.locate(name);

		sessionFactoryImpl.setSessionFactoryImplementor(
			sessionFactoryImplementor);

		AbstractPlatformTransactionManager abstractPlatformTransactionManager =
			(AbstractPlatformTransactionManager)PortalBeanLocatorUtil.locate(
				"liferayTransactionManager");

		if (abstractPlatformTransactionManager instanceof
				HibernateTransactionManager) {

			HibernateTransactionManager hibernateTransactionManager =
				(HibernateTransactionManager)abstractPlatformTransactionManager;

			hibernateTransactionManager.setSessionFactory(
				sessionFactoryImplementor);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to swap to session factory for " +
					abstractPlatformTransactionManager.getClass() +
						" which may cause subsequent transaction failures");
		}
	}

	private static void _reinitializeJPA(String name, DataSource dataSource)
		throws Exception {

		LocalContainerEntityManagerFactoryBean
			localContainerEntityManagerFactoryBean =
				new LocalContainerEntityManagerFactoryBean();

		localContainerEntityManagerFactoryBean.setDataSource(dataSource);

		localContainerEntityManagerFactoryBean.afterPropertiesSet();

		EntityManagerFactory entityManagerFactory =
			localContainerEntityManagerFactoryBean.getObject();

		com.liferay.portal.dao.orm.jpa.SessionFactoryImpl sessionFactoryImpl =
			(com.liferay.portal.dao.orm.jpa.SessionFactoryImpl)
				PortalBeanLocatorUtil.locate(name);

		sessionFactoryImpl.setEntityManagerFactory(entityManagerFactory);
	}

	private static void _reinitializePortletsHibernate(DataSource newDataSource)
		throws Exception {

		List<PortletSessionFactoryImpl> portletSessionFactoryImpls =
			SessionFactoryImpl.getPortletSessionFactories();

		for (PortletSessionFactoryImpl portletSessionFactoryImpl :
				portletSessionFactoryImpls) {

			ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();
			ClassLoader contextClassLoader =
				ClassLoaderUtil.getContextClassLoader();

			try {
				ClassLoader sessionFactoryClassLoader =
					portletSessionFactoryImpl.getSessionFactoryClassLoader();

				PortletClassLoaderUtil.setClassLoader(
					sessionFactoryClassLoader);
				ClassLoaderUtil.setContextClassLoader(
					sessionFactoryClassLoader);

				PortletHibernateConfiguration portletHibernateConfiguration =
					new PortletHibernateConfiguration();

				portletHibernateConfiguration.setDataSource(newDataSource);

				portletHibernateConfiguration.afterPropertiesSet();

				SessionFactoryImplementor sessionFactoryImplementor =
					(SessionFactoryImplementor)
						portletHibernateConfiguration.getObject();

				portletSessionFactoryImpl.setSessionFactoryImplementor(
					sessionFactoryImplementor);
			}
			finally {
				PortletClassLoaderUtil.setClassLoader(classLoader);
				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DataSourceSwapper.class);

	private static DataSourceWrapper _counterDataSourceWrapper;
	private static DataSourceWrapper _liferayDataSourceWrapper;

}