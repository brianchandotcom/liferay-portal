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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.PwdGenerator;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.lang.management.ManagementFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;

import uk.org.primrose.pool.datasource.GenericDataSourceFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DataSourceFactoryImpl implements DataSourceFactory {

	public void destroyDataSource(DataSource dataSource) throws Exception {
		if (dataSource instanceof ComboPooledDataSource) {
			ComboPooledDataSource comboPooledDataSource =
				(ComboPooledDataSource)dataSource;

			comboPooledDataSource.close();
		}
		else if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
			org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource =
				(org.apache.tomcat.jdbc.pool.DataSource)dataSource;

			tomcatDataSource.close();
		}
	}

	public DataSource initDataSource(Properties properties) throws Exception {
		Properties defaultProperties = PropsUtil.getProperties(
			"jdbc.default.", true);

		PropertiesUtil.merge(defaultProperties, properties);

		properties = defaultProperties;

		String jndiName = properties.getProperty("jndi.name");

		if (Validator.isNotNull(jndiName)) {
			try {
				return (DataSource)JNDIUtil.lookup(
					new InitialContext(), jndiName);
			}
			catch (Exception e) {
				_log.error("Unable to lookup " + jndiName, e);
			}
		}

		DataSource dataSource = null;

		String liferayPoolProvider =
			PropsValues.JDBC_DEFAULT_LIFERAY_POOL_PROVIDER;

		if (liferayPoolProvider.equalsIgnoreCase("c3p0") ||
			liferayPoolProvider.equalsIgnoreCase("c3po")) {

			if (_log.isDebugEnabled()) {
				_log.debug("Initializing C3P0 data source");
			}

			dataSource = initDataSourceC3PO(properties);
		}
		else if (liferayPoolProvider.equalsIgnoreCase("dbcp")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing DBCP data source");
			}

			dataSource = initDataSourceDBCP(properties);
		}
		else if (liferayPoolProvider.equalsIgnoreCase("primrose")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Primrose data source");
			}

			dataSource = initDataSourcePrimrose(properties);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Tomcat data source");
			}

			dataSource = initDataSourceTomcat(properties);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Created data source " + dataSource.getClass().getName());

			SortedProperties sortedProperties = new SortedProperties(
				properties);

			sortedProperties.list(System.out);
		}

		return dataSource;
	}

	public DataSource initDataSource(
			String driverClassName, String url, String userName,
			String password)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("driverClassName", driverClassName);
		properties.setProperty("url", url);
		properties.setProperty("username", userName);
		properties.setProperty("password", password);

		return initDataSource(properties);
	}

	protected DataSource initDataSourceC3PO(Properties properties)
		throws Exception {

		ComboPooledDataSource comboPooledDataSource =
			new ComboPooledDataSource();

		String identityToken = PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

		comboPooledDataSource.setIdentityToken(identityToken);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = properties.getProperty(key);

			// Map org.apache.commons.dbcp.BasicDataSource to C3PO

			if (key.equalsIgnoreCase("driverClassName")) {
				key = "driverClass";
			}
			else if (key.equalsIgnoreCase("url")) {
				key = "jdbcUrl";
			}
			else if (key.equalsIgnoreCase("username")) {
				key = "user";
			}

			// Ignore Liferay properties

			if (isPropertyLiferay(key)) {
				continue;
			}

			// Ignore DBCP properties

			if (isPropertyDBCP(key)) {
				continue;
			}

			// Ignore Primrose properties

			if (isPropertyPrimrose(key)) {
				continue;
			}

			// Ignore Tomcat

			if (isPropertyTomcat(key)) {
				continue;
			}

			try {
				BeanUtil.setProperty(comboPooledDataSource, key, value);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Property " + key + " is not a valid C3PO property");
				}
			}
		}

		return comboPooledDataSource;
	}

	protected DataSource initDataSourceDBCP(Properties properties)
		throws Exception {

		return BasicDataSourceFactory.createDataSource(properties);
	}

	protected DataSource initDataSourcePrimrose(Properties properties)
		throws Exception {

		String poolName = PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

		properties.setProperty("poolName", poolName);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			// Map org.apache.commons.dbcp.BasicDataSource to Primrose

			if (key.equalsIgnoreCase("driverClassName")) {
				key = "driverClass";
			}
			else if (key.equalsIgnoreCase("url")) {
				key = "driverURL";
			}
			else if (key.equalsIgnoreCase("username")) {
				key = "user";
			}

			properties.setProperty(key, value);
		}

		GenericDataSourceFactory genericDataSourceFactory =
			new GenericDataSourceFactory();

		return genericDataSourceFactory.loadPool(poolName, properties);
	}

	protected DataSource initDataSourceTomcat(Properties properties)
		throws Exception {

		PoolProperties poolProperties = new PoolProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			// Ignore Liferay properties

			if (isPropertyLiferay(key)) {
				continue;
			}

			// Ignore C3P0 properties

			if (isPropertyC3PO(key)) {
				continue;
			}

			// Ignore Primrose properties

			if (isPropertyPrimrose(key)) {
				continue;
			}

			try {
				BeanUtil.setProperty(poolProperties, key, value);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Property " + key + " is not a valid Tomcat JDBC " +
							"Connection Pool property");
				}
			}
		}

		String poolName = PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

		poolProperties.setName(poolName);

		org.apache.tomcat.jdbc.pool.DataSource dataSource =
			new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);

		if (poolProperties.isJmxEnabled()) {
			org.apache.tomcat.jdbc.pool.ConnectionPool jdbcConnectionPool =
				dataSource.createPool();

			ConnectionPool jmxConnectionPool = jdbcConnectionPool.getJmxPool();

			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			ObjectName objectName = new ObjectName(
				_TOMCAT_JDBC_POOL_OBJECT_NAME_PREFIX + poolName);

			mBeanServer.registerMBean(jmxConnectionPool, objectName);
		}

		return dataSource;
	}

	protected boolean isPropertyC3PO(String key) {
		if (key.equalsIgnoreCase("acquireIncrement") ||
			key.equalsIgnoreCase("acquireRetryAttempts") ||
			key.equalsIgnoreCase("acquireRetryDelay") ||
			key.equalsIgnoreCase("connectionCustomizerClassName") ||
			key.equalsIgnoreCase("idleConnectionTestPeriod") ||
			key.equalsIgnoreCase("maxIdleTime") ||
			key.equalsIgnoreCase("maxPoolSize") ||
			key.equalsIgnoreCase("minPoolSize") ||
			key.equalsIgnoreCase("numHelperThreads") ||
			key.equalsIgnoreCase("preferredTestQuery")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPropertyDBCP(String key) {
		if (key.equalsIgnoreCase("defaultTransactionIsolation") ||
			key.equalsIgnoreCase("maxActive") ||
			key.equalsIgnoreCase("minIdle") ||
			key.equalsIgnoreCase("removeAbandonedTimeout")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPropertyLiferay(String key) {
		if (key.equalsIgnoreCase("jndi.name") ||
			key.equalsIgnoreCase("liferay.pool.provider")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPropertyPrimrose(String key) {
		if (key.equalsIgnoreCase("base") ||
			key.equalsIgnoreCase("connectionTransactionIsolation") ||
			key.equalsIgnoreCase("idleTime") ||
			key.equalsIgnoreCase("numberOfConnectionsToInitializeWith")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPropertyTomcat(String key) {
		if (key.equalsIgnoreCase("fairQueue") ||
			key.equalsIgnoreCase("jdbcInterceptors") ||
			key.equalsIgnoreCase("jmxEnabled") ||
			key.equalsIgnoreCase("timeBetweenEvictionRunsMillis") ||
			key.equalsIgnoreCase("useEquals")) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final String _TOMCAT_JDBC_POOL_OBJECT_NAME_PREFIX =
		"TomcatJDBCPool:type=ConnectionPool,name=";

	private static Log _log = LogFactoryUtil.getLog(
		DataSourceFactoryImpl.class);

}