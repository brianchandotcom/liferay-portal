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

package com.liferay.portal.dao.jdbc.util;

import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.PortalHibernateConfiguration;
import com.liferay.portal.spring.jpa.LocalContainerEntityManagerFactoryBean;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Shuyang Zhou
 */
public class DataSourceSwapUtil {

	public static void swapCounterDataSource(Properties properties)
		throws Exception {
		DataSource newDataSource = DataSourceFactoryUtil.initDataSource(
			properties);

		if (_log.isInfoEnabled()) {
			_log.info("Create new Counter DataSource : " + newDataSource);
		}

		DataSource oldDataSource =
			_counterDataSourceWrapper.getWrappedDataSource();

		_counterDataSourceWrapper.setWrappedDataSource(newDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("New Counter DataSource installed.");
		}

		DataSourceFactoryUtil.destroyDataSource(oldDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("Destroyed old Counter DataSource.");
		}

		if (_isJPA) {
			rebuildJPAEntityManagerFactory("counterSessionFactory",
				newDataSource);
		}
		else {
			rebuildHibernateSessionFactory("counterSessionFactory",
				newDataSource);
		}
	}

	public static void swapPortalDataSource(Properties properties)
		throws Exception {
		DataSource newDataSource = DataSourceFactoryUtil.initDataSource(
			properties);

		if (_log.isInfoEnabled()) {
			_log.info("Create new Portal DataSource : " + newDataSource);
		}

		DataSource oldDataSource =
			_portalDataSourceWrapper.getWrappedDataSource();

		_portalDataSourceWrapper.setWrappedDataSource(newDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("New Portal DataSource installed.");
		}

		DataSourceFactoryUtil.destroyDataSource(oldDataSource);

		if (_log.isInfoEnabled()) {
			_log.info("Destroyed old Portal DataSource.");
		}

		if (_isJPA) {
			rebuildJPAEntityManagerFactory("liferaySessionFactory",
				newDataSource);
		}
		else {
			rebuildHibernateSessionFactory("liferaySessionFactory",
				newDataSource);
		}
	}

	public void setCounterDataSourceWrapper(
		DataSourceWrapper counterDataSourceWrapper) {

		_counterDataSourceWrapper = counterDataSourceWrapper;
	}

	public void setPortalDataSourceWrapper(
		DataSourceWrapper portalDataSourceWrapper) {

		_portalDataSourceWrapper = portalDataSourceWrapper;
	}

	private static void rebuildHibernateSessionFactory(
		String name, DataSource dataSource) throws Exception {

		PortalHibernateConfiguration newHibernateConfiguration =
			new PortalHibernateConfiguration();

		newHibernateConfiguration.setDataSource(dataSource);

		newHibernateConfiguration.afterPropertiesSet();

		SessionFactoryImplementor newSessionFactoryImplementor =
			(SessionFactoryImplementor)
				newHibernateConfiguration.getObject();

		SessionFactoryImpl sessionFactory =
			(SessionFactoryImpl)PortalBeanLocatorUtil.locate(name);

		sessionFactory.setSessionFactoryImplementor(
			newSessionFactoryImplementor);
	}

	private static void rebuildJPAEntityManagerFactory(
		String name, DataSource dataSource) throws Exception {

		LocalContainerEntityManagerFactoryBean newEntityManagerFactoryBean =
			new LocalContainerEntityManagerFactoryBean();

		newEntityManagerFactoryBean.setDataSource(dataSource);

		newEntityManagerFactoryBean.afterPropertiesSet();

		EntityManagerFactory entityManagerFactory =
			newEntityManagerFactoryBean.getObject();

		com.liferay.portal.dao.orm.jpa.SessionFactoryImpl sessionFactoryImpl =
			(com.liferay.portal.dao.orm.jpa.SessionFactoryImpl)
				PortalBeanLocatorUtil.locate(name);

		sessionFactoryImpl.setEntityManagerFactory(entityManagerFactory);
	}

	private static Log _log = LogFactoryUtil.getLog(DataSourceSwapUtil.class);

	private static boolean _isJPA =
		PropsValues.PERSISTENCE_PROVIDER.equalsIgnoreCase("jpa");

	private static DataSourceWrapper _counterDataSourceWrapper;

	private static DataSourceWrapper _portalDataSourceWrapper;

}