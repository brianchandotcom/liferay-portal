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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Converter;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.Map;
import java.util.Properties;

import javassist.util.proxy.ProxyFactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Shuyang Zhou
 */
public class PortalHibernateConfiguration extends LocalSessionFactoryBean {

	@Override
	public SessionFactory buildSessionFactory() throws Exception {
		ProxyFactory.classLoaderProvider =
			new ProxyFactory.ClassLoaderProvider() {

				public ClassLoader get(ProxyFactory proxyFactory) {
					return Thread.currentThread().getContextClassLoader();
				}

			};

		return super.buildSessionFactory();
	}

	public void setHibernateConfigurationConverter(
		Converter<String> hibernateConfigurationConverter) {

		_hibernateConfigurationConverter = hibernateConfigurationConverter;
	}

	protected String determineDialect() {
		return DialectDetector.determineDialect(getDataSource());
	}

	protected ClassLoader getConfigurationClassLoader() {
		return getClass().getClassLoader();
	}

	protected String[] getConfigurationResources() {
		return PropsUtil.getArray(PropsKeys.HIBERNATE_CONFIGS);
	}

	@Override
	protected Configuration newConfiguration() {
		Configuration configuration = new Configuration();

		try {
			String[] resources = getConfigurationResources();

			for (String resource : resources) {
				try {
					readResource(configuration, resource);
				}
				catch (Exception e2) {
					if (_log.isWarnEnabled()) {
						_log.warn(e2, e2);
					}
				}
			}

			configuration.setProperties(PropsUtil.getProperties());

			if (Validator.isNull(PropsValues.HIBERNATE_DIALECT)) {
				String dialect = determineDialect();

				configuration.setProperty("hibernate.dialect", dialect);
			}

			DB db = DBFactoryUtil.getDB();

			String dbType = db.getType();

			if (dbType.equals(DB.TYPE_HYPERSONIC)) {
				//configuration.setProperty("hibernate.jdbc.batch_size", "0");
			}
		}
		catch (Exception e1) {
			_log.error(e1, e1);
		}

		Properties hibernateProperties = getHibernateProperties();

		if (hibernateProperties != null) {
			for (Map.Entry<Object, Object> entry :
					hibernateProperties.entrySet()) {

				String key = (String)entry.getKey();
				String value = (String)entry.getValue();

				configuration.setProperty(key, value);
			}
		}

		return configuration;
	}

	@Override
	protected void postProcessConfiguration(Configuration configuration) {

		// Make sure that the Hibernate settings from PropsUtil are set. See the
		// buildSessionFactory implementation in the LocalSessionFactoryBean
		// class to understand how Spring automates a lot of configuration for
		// Hibernate.

		String connectionReleaseMode = PropsUtil.get(
			Environment.RELEASE_CONNECTIONS);

		if (Validator.isNotNull(connectionReleaseMode)) {
			configuration.setProperty(
				Environment.RELEASE_CONNECTIONS, connectionReleaseMode);
		}
	}

	protected void readResource(Configuration configuration, String resource)
		throws Exception {

		ClassLoader classLoader = getConfigurationClassLoader();

		InputStream is = classLoader.getResourceAsStream(resource);

		if (is == null) {
			return;
		}

		if (_hibernateConfigurationConverter != null) {
			String configurationString = StringUtil.read(is);

			is.close();

			configurationString = _hibernateConfigurationConverter.convert(
				configurationString);

			is = new UnsyncByteArrayInputStream(configurationString.getBytes());
		}

		configuration = configuration.addInputStream(is);

		is.close();
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalHibernateConfiguration.class);

	private Converter<String> _hibernateConfigurationConverter;

}