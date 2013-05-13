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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.JarUtil;
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

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class DataSourceFactoryImpl implements DataSourceFactory {

	@Override
	public void destroyDataSource(DataSource dataSource) throws Exception {
		while (dataSource instanceof DataSourceWrapper) {
			DataSourceWrapper dataSourceWrapper = (DataSourceWrapper)dataSource;

			dataSource = dataSourceWrapper.getWrappedDataSource();
		}

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

	@Override
	public DataSource initDataSource(Properties properties) throws Exception {
		Properties defaultProperties = PropsUtil.getProperties(
			"jdbc.default.", true);

		PropertiesUtil.merge(defaultProperties, properties);

		properties = defaultProperties;

		String jndiName = properties.getProperty("jndi.name");

		if (Validator.isNotNull(jndiName)) {
			try {
				Properties jndiEnvironmentProperties = PropsUtil.getProperties(
					PropsKeys.JNDI_ENVIRONMENT, true);

				Context context = new InitialContext(jndiEnvironmentProperties);

				return (DataSource)JNDIUtil.lookup(context, jndiName);
			}
			catch (Exception e) {
				_log.error("Unable to lookup " + jndiName, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Data source properties:\n");

			SortedProperties sortedProperties = new SortedProperties(
				properties);

			_log.debug(PropertiesUtil.toString(sortedProperties));
		}

		testClassForName(properties);

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
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Tomcat data source");
			}

			dataSource = initDataSourceTomcat(properties);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Created data source " + dataSource.getClass());
		}

		return _pacl.getDataSource(dataSource);
	}

	@Override
	public DataSource initDataSource(
			String driverClassName, String url, String userName,
			String password, String jndiName)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("driverClassName", driverClassName);
		properties.setProperty("url", url);
		properties.setProperty("username", userName);
		properties.setProperty("password", password);
		properties.setProperty("jndi.name", jndiName);

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

	protected void testClassForName(Properties properties) throws Exception {
		String driverClassName = properties.getProperty("driverClassName");

		try {
			Class.forName(driverClassName);
		}
		catch (ClassNotFoundException cnfe) {
			if (!ServerDetector.isGeronimo() && !ServerDetector.isJetty() &&
				!ServerDetector.isTomcat()) {

				throw cnfe;
			}

			String url = PropsUtil.get(
				PropsKeys.SETUP_DATABASE_JAR_URL, new Filter(driverClassName));
			String name = PropsUtil.get(
				PropsKeys.SETUP_DATABASE_JAR_NAME, new Filter(driverClassName));

			if (Validator.isNull(url) || Validator.isNull(name)) {
				throw cnfe;
			}

			if (HttpUtil.getHttp() == null) {
				HttpUtil httpUtil = new HttpUtil();

				httpUtil.setHttp(new HttpImpl());
			}

			if (FileUtil.getFile() == null) {
				FileUtil fileUtil = new FileUtil();

				fileUtil.setFile(new FileImpl());
			}

			JarUtil.downloadAndInstallJar(true, url, name, null);
		}
	}

	private static final String _TOMCAT_JDBC_POOL_OBJECT_NAME_PREFIX =
		"TomcatJDBCPool:type=ConnectionPool,name=";

	private static Log _log = LogFactoryUtil.getLog(
		DataSourceFactoryImpl.class);

	private static PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		public DataSource getDataSource(DataSource dataSource) {
			return dataSource;
		}

	}

	public static interface PACL {

		public DataSource getDataSource(DataSource dataSource);

	}

}