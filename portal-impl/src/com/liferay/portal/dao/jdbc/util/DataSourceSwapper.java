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

package com.liferay.portal.dao.jdbc.util;

import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.PortalHibernateConfiguration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class DataSourceSwapper implements BeanFactoryAware {

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

		if (_log.isInfoEnabled()) {
			_log.info("Reinitialize Hibernate for new counter data source");
		}

		_reinitializeHibernate("counterSessionFactory", newDataSource);
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

		if (_log.isInfoEnabled()) {
			_log.info("Reinitialize Hibernate for new liferay data source");
		}

		_reinitializeHibernate("liferaySessionFactory", newDataSource);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
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

		portalHibernateConfiguration.setBeanFactory(_beanFactory);
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

	private static final Log _log = LogFactoryUtil.getLog(
		DataSourceSwapper.class);

	private static BeanFactory _beanFactory;
	private static DataSourceWrapper _counterDataSourceWrapper;
	private static DataSourceWrapper _liferayDataSourceWrapper;

}