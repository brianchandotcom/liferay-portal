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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * @author Brian Wing Shun Chan
 */
public class TransactionManagerFactory {

	public static AbstractPlatformTransactionManager createTransactionManager(
			DataSource dataSource, SessionFactory sessionFactory)
		throws Exception {

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		Class<?> clazz = classLoader.loadClass(
			PropsValues.TRANSACTION_MANAGER_IMPL);

		AbstractPlatformTransactionManager abstractPlatformTransactionManager =
			(AbstractPlatformTransactionManager)clazz.newInstance();

		Properties properties = PropsUtil.getProperties(
			"transaction.manager.property.", true);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			BeanUtil.setProperty(
				abstractPlatformTransactionManager, key, value);
		}

		if (abstractPlatformTransactionManager instanceof
				HibernateTransactionManager) {

			HibernateTransactionManager hibernateTransactionManager =
				(HibernateTransactionManager)abstractPlatformTransactionManager;

			hibernateTransactionManager.setDataSource(dataSource);
			hibernateTransactionManager.setSessionFactory(sessionFactory);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Created transaction manager " +
					abstractPlatformTransactionManager.getClass().getName());

			SortedProperties sortedProperties = new SortedProperties(
				properties);

			sortedProperties.list(System.out);
		}

		return abstractPlatformTransactionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionManagerFactory.class);

}