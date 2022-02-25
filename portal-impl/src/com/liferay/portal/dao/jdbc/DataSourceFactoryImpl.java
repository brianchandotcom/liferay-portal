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

package com.liferay.portal.dao.jdbc;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.jdbc.pool.metrics.C3P0ConnectionPoolMetrics;
import com.liferay.portal.dao.jdbc.pool.metrics.DBCPConnectionPoolMetrics;
import com.liferay.portal.dao.jdbc.pool.metrics.HikariConnectionPoolMetrics;
import com.liferay.portal.dao.jdbc.pool.metrics.TomcatConnectionPoolMetrics;
import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.dao.jdbc.util.RetryDataSourceWrapper;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.dao.jdbc.pool.metrics.ConnectionPoolMetrics;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.JarUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.zaxxer.hikari.HikariDataSource;

import java.io.Closeable;

import java.lang.reflect.Field;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DataSourceFactoryImpl implements DataSourceFactory {

	@Override
	public void destroyDataSource(DataSource dataSource) throws Exception {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

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

			if (_serviceTracker != null) {
				_serviceTracker.close();
			}

			tomcatDataSource.close();
		}
		else if (dataSource instanceof BasicDataSource) {
			BasicDataSource basicDataSource = (BasicDataSource)dataSource;

			basicDataSource.close();
		}
		else if (dataSource instanceof Closeable) {
			Closeable closeable = (Closeable)dataSource;

			closeable.close();
		}
	}

	@Override
	public DataSource initDataSource(Properties properties) throws Exception {
		String jndiName = properties.getProperty("jndi.name");
		String driverClassName = properties.getProperty("driverClassName");

		if (JavaDetector.isIBM() &&
			(Validator.isNotNull(jndiName) ||
			 driverClassName.startsWith("com.mysql.cj"))) {

			// LPS-120753

			if (Validator.isNull(jndiName)) {
				testDatabaseClass(driverClassName);
			}

			try {
				_populateIBMCipherSuites(
					Class.forName("com.mysql.cj.protocol.ExportControlled"));
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}
		}

		if (Validator.isNotNull(jndiName)) {
			try {
				Properties jndiEnvironmentProperties = PropsUtil.getProperties(
					PropsKeys.JNDI_ENVIRONMENT, true);

				Context context = new InitialContext(jndiEnvironmentProperties);

				return (DataSource)JNDIUtil.lookup(context, jndiName);
			}
			catch (Exception exception) {
				_log.error("Unable to lookup " + jndiName, exception);
			}
		}
		else {
			testDatabaseClass(driverClassName);

			_waitForJDBCConnection(properties);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Data source properties:\n");

			_log.debug(PropertiesUtil.toString(properties));
		}

		DataSource dataSource = null;

		String liferayPoolProvider =
			PropsValues.JDBC_DEFAULT_LIFERAY_POOL_PROVIDER;

		if (StringUtil.equalsIgnoreCase(liferayPoolProvider, "c3p0") ||
			StringUtil.equalsIgnoreCase(liferayPoolProvider, "c3po")) {

			if (_log.isDebugEnabled()) {
				_log.debug("Initializing C3P0 data source");
			}

			dataSource = initDataSourceC3PO(properties);
		}
		else if (StringUtil.equalsIgnoreCase(liferayPoolProvider, "dbcp")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing DBCP data source");
			}

			dataSource = initDataSourceDBCP(properties);
		}
		else if (StringUtil.equalsIgnoreCase(liferayPoolProvider, "hikaricp")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing HikariCP data source");
			}

			dataSource = initDataSourceHikariCP(properties);
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

		if (PropsValues.RETRY_DATA_SOURCE_MAX_RETRIES > 0) {
			DBType dbType = DBManagerUtil.getDBType(
				DialectDetector.getDialect(dataSource));

			if (dbType == DBType.SYBASE) {
				dataSource = new RetryDataSourceWrapper(dataSource);
			}
		}

		return dataSource;
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

		comboPooledDataSource.setIdentityToken(StringUtil.randomString());

		String connectionPropertiesString = (String)properties.remove(
			"connectionProperties");

		if (connectionPropertiesString != null) {
			Properties connectionProperties = PropertiesUtil.load(
				StringUtil.replace(
					connectionPropertiesString, CharPool.SEMICOLON,
					CharPool.NEW_LINE));

			comboPooledDataSource.setProperties(connectionProperties);
		}

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = properties.getProperty(key);

			// Map org.apache.commons.dbcp.BasicDataSource to C3PO

			if (StringUtil.equalsIgnoreCase(key, "driverClassName")) {
				key = "driverClass";
			}
			else if (StringUtil.equalsIgnoreCase(key, "url")) {
				key = "jdbcUrl";
			}
			else if (StringUtil.equalsIgnoreCase(key, "username")) {
				key = "user";
			}

			// Ignore Liferay property

			if (isPropertyLiferay(key)) {
				continue;
			}

			// Ignore DBCP property

			if (isPropertyDBCP(key)) {
				continue;
			}

			// Ignore HikariCP property

			if (isPropertyHikariCP(key)) {
				continue;
			}

			// Ignore Tomcat JDBC property

			if (isPropertyTomcat(key)) {
				continue;
			}

			// Set C3PO property

			try {
				BeanUtil.pojo.setProperty(comboPooledDataSource, key, value);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Property " + key + " is an invalid C3PO property",
						exception);
				}
			}
		}

		registerConnectionPoolMetrics(
			new C3P0ConnectionPoolMetrics(comboPooledDataSource));

		return comboPooledDataSource;
	}

	protected DataSource initDataSourceDBCP(Properties properties)
		throws Exception {

		DataSource dataSource = BasicDataSourceFactory.createDataSource(
			properties);

		registerConnectionPoolMetrics(
			new DBCPConnectionPoolMetrics((BasicDataSource)dataSource));

		return dataSource;
	}

	protected DataSource initDataSourceHikariCP(Properties properties)
		throws Exception {

		HikariDataSource hikariDataSource = new HikariDataSource();

		String connectionPropertiesString = (String)properties.remove(
			"connectionProperties");

		if (connectionPropertiesString != null) {
			Properties connectionProperties = PropertiesUtil.load(
				StringUtil.replace(
					connectionPropertiesString, CharPool.SEMICOLON,
					CharPool.NEW_LINE));

			hikariDataSource.setDataSourceProperties(connectionProperties);
		}

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			// Map org.apache.commons.dbcp.BasicDataSource to Hikari CP

			if (StringUtil.equalsIgnoreCase(key, "url")) {
				key = "jdbcUrl";
			}

			// Ignore Liferay property

			if (isPropertyLiferay(key)) {
				continue;
			}

			// Ignore C3P0 property

			if (isPropertyC3PO(key)) {
				continue;
			}

			// Ignore DBCP property

			if (isPropertyDBCP(key)) {
				continue;
			}

			// Ignore Tomcat JDBC property

			if (isPropertyTomcat(key)) {
				continue;
			}

			// Set HikariCP property

			try {
				BeanUtil.pojo.setProperty(
					hikariDataSource, key, (String)entry.getValue());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Property " + key + " is an invalid HikariCP property",
						exception);
				}
			}
		}

		registerConnectionPoolMetrics(
			new HikariConnectionPoolMetrics(hikariDataSource));

		return hikariDataSource;
	}

	protected DataSource initDataSourceTomcat(Properties properties)
		throws Exception {

		PoolProperties poolProperties = new PoolProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			// Ignore Liferay property

			if (isPropertyLiferay(key)) {
				continue;
			}

			// Ignore C3P0 property

			if (isPropertyC3PO(key)) {
				continue;
			}

			// Ignore HikariCP property

			if (isPropertyHikariCP(key)) {
				continue;
			}

			// Set Tomcat JDBC property

			try {
				BeanUtil.pojo.setProperty(
					poolProperties, key, (String)entry.getValue());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Property ", key, " is an invalid Tomcat JDBC ",
							"property"),
						exception);
				}
			}
		}

		String poolName = StringUtil.randomString();

		poolProperties.setName(poolName);

		org.apache.tomcat.jdbc.pool.DataSource dataSource =
			new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);

		if (poolProperties.isJmxEnabled()) {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			_serviceTracker = new ServiceTracker<>(
				bundleContext, MBeanServer.class,
				new MBeanServerServiceTrackerCustomizer(
					bundleContext, dataSource, poolName));

			_serviceTracker.open();
		}

		registerConnectionPoolMetrics(
			new TomcatConnectionPoolMetrics(dataSource));

		return dataSource;
	}

	protected boolean isPropertyC3PO(String key) {
		if (StringUtil.equalsIgnoreCase(key, "acquireIncrement") ||
			StringUtil.equalsIgnoreCase(key, "acquireRetryAttempts") ||
			StringUtil.equalsIgnoreCase(key, "acquireRetryDelay") ||
			StringUtil.equalsIgnoreCase(key, "connectionCustomizerClassName") ||
			StringUtil.equalsIgnoreCase(key, "idleConnectionTestPeriod") ||
			StringUtil.equalsIgnoreCase(key, "initialPoolSize") ||
			StringUtil.equalsIgnoreCase(key, "maxIdleTime") ||
			StringUtil.equalsIgnoreCase(key, "maxPoolSize") ||
			StringUtil.equalsIgnoreCase(key, "minPoolSize") ||
			StringUtil.equalsIgnoreCase(key, "numHelperThreads") ||
			StringUtil.equalsIgnoreCase(key, "preferredTestQuery")) {

			return true;
		}

		return false;
	}

	protected boolean isPropertyDBCP(String key) {
		if (StringUtil.equalsIgnoreCase(key, "defaultTransactionIsolation") ||
			StringUtil.equalsIgnoreCase(key, "maxActive") ||
			StringUtil.equalsIgnoreCase(key, "minIdle") ||
			StringUtil.equalsIgnoreCase(key, "removeAbandonedTimeout")) {

			return true;
		}

		return false;
	}

	protected boolean isPropertyHikariCP(String key) {
		if (StringUtil.equalsIgnoreCase(key, "autoCommit") ||
			StringUtil.equalsIgnoreCase(key, "connectionTestQuery") ||
			StringUtil.equalsIgnoreCase(key, "connectionTimeout") ||
			StringUtil.equalsIgnoreCase(key, "idleTimeout") ||
			StringUtil.equalsIgnoreCase(key, "initializationFailFast") ||
			StringUtil.equalsIgnoreCase(key, "maximumPoolSize") ||
			StringUtil.equalsIgnoreCase(key, "maxLifetime") ||
			StringUtil.equalsIgnoreCase(key, "minimumIdle") ||
			StringUtil.equalsIgnoreCase(key, "registerMbeans")) {

			return true;
		}

		return false;
	}

	protected boolean isPropertyLiferay(String key) {
		if (StringUtil.equalsIgnoreCase(key, "jndi.name") ||
			StringUtil.equalsIgnoreCase(key, "liferay.pool.provider")) {

			return true;
		}

		return false;
	}

	protected boolean isPropertyTomcat(String key) {
		if (StringUtil.equalsIgnoreCase(key, "fairQueue") ||
			StringUtil.equalsIgnoreCase(key, "initialSize") ||
			StringUtil.equalsIgnoreCase(key, "jdbcInterceptors") ||
			StringUtil.equalsIgnoreCase(key, "jmxEnabled") ||
			StringUtil.equalsIgnoreCase(key, "maxIdle") ||
			StringUtil.equalsIgnoreCase(key, "testWhileIdle") ||
			StringUtil.equalsIgnoreCase(key, "timeBetweenEvictionRunsMillis") ||
			StringUtil.equalsIgnoreCase(key, "useEquals") ||
			StringUtil.equalsIgnoreCase(key, "validationQuery")) {

			return true;
		}

		return false;
	}

	protected void registerConnectionPoolMetrics(
		ConnectionPoolMetrics connectionPoolMetrics) {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			ConnectionPoolMetrics.class, connectionPoolMetrics, null);
	}

	protected void testDatabaseClass(String driverClassName) throws Exception {
		try {
			Class.forName(driverClassName);
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (!ServerDetector.isTomcat()) {
				throw classNotFoundException;
			}

			String url = PropsUtil.get(
				PropsKeys.SETUP_DATABASE_JAR_URL, new Filter(driverClassName));
			String name = PropsUtil.get(
				PropsKeys.SETUP_DATABASE_JAR_NAME, new Filter(driverClassName));
			String sha1 = PropsUtil.get(
				PropsKeys.SETUP_DATABASE_JAR_SHA1, new Filter(driverClassName));

			if (Validator.isNull(url) || Validator.isNull(name) ||
				Validator.isNull(sha1)) {

				throw classNotFoundException;
			}

			ClassLoader classLoader = SystemException.class.getClassLoader();

			if (!(classLoader instanceof URLClassLoader)) {
				_log.error(
					"Unable to install JAR because the system class loader " +
						"is not an instance of URLClassLoader");

				return;
			}

			try {
				DigesterUtil digesterUtil = new DigesterUtil();

				digesterUtil.setDigester(new DigesterImpl());

				JarUtil.downloadAndInstallJar(
					new URL(url),
					Paths.get(
						PropsValues.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR,
						name),
					(URLClassLoader)classLoader, sha1);
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to download and install ", name, " to ",
						PropsValues.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR,
						" from ", url),
					exception);

				throw classNotFoundException;
			}
		}
	}

	private void _populateIBMCipherSuites(Class<?> clazz) {
		try {
			SSLContext sslContext = SSLContext.getDefault();

			SSLEngine sslEngine = sslContext.createSSLEngine();

			String[] ibmSupportedCipherSuites =
				sslEngine.getSupportedCipherSuites();

			if ((ibmSupportedCipherSuites == null) ||
				(ibmSupportedCipherSuites.length == 0)) {

				return;
			}

			Field allowedCiphersField = ReflectionUtil.getDeclaredField(
				clazz, "ALLOWED_CIPHERS");

			List<String> allowedCiphers = (List<String>)allowedCiphersField.get(
				null);

			for (String ibmSupportedCipherSuite : ibmSupportedCipherSuites) {
				if (!allowedCiphers.contains(ibmSupportedCipherSuite)) {
					allowedCiphers.add(ibmSupportedCipherSuite);
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to populate IBM JDK TLS cipher suite into MySQL " +
					"Connector/J's allowed cipher list, consider disabling " +
						"SSL for the connection",
				exception);
		}
	}

	private void _waitForJDBCConnection(Properties properties) {
		int maxRetries = PropsValues.RETRY_JDBC_ON_STARTUP_MAX_RETRIES;

		if (maxRetries <= 0) {
			return;
		}

		int delay = PropsValues.RETRY_JDBC_ON_STARTUP_DELAY;

		if (delay < 0) {
			delay = 0;
		}

		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");

		int count = maxRetries;

		while (count-- > 0) {
			try (Connection connection = DriverManager.getConnection(
					url, username, password)) {

				if (connection != null) {
					if (_log.isInfoEnabled()) {
						_log.info("Successfully acquired JDBC connection");
					}

					return;
				}
			}
			catch (SQLException sqlException) {
				if (_log.isDebugEnabled()) {
					_log.error(
						"Unable to acquire JDBC connection", sqlException);
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"At attempt ", maxRetries - count, " of ", maxRetries,
						" in acquiring a JDBC connection after a ", delay,
						" second ", delay));
			}

			try {
				Thread.sleep(delay * Time.SECOND);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Interruptted acquiring a JDBC connection",
						interruptedException);
				}

				break;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to acquire a direct JDBC connection, proceeding to " +
					"use a data source instead");
		}
	}

	private static final String _TOMCAT_JDBC_POOL_OBJECT_NAME_PREFIX =
		"TomcatJDBCPool:type=ConnectionPool,name=";

	private static final Log _log = LogFactoryUtil.getLog(
		DataSourceFactoryImpl.class);

	private ServiceRegistration<?> _serviceRegistration;
	private ServiceTracker<MBeanServer, MBeanServer> _serviceTracker;

	private static class MBeanServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MBeanServer, MBeanServer> {

		public MBeanServerServiceTrackerCustomizer(
				BundleContext bundleContext,
				org.apache.tomcat.jdbc.pool.DataSource dataSource,
				String poolName)
			throws MalformedObjectNameException {

			_bundleContext = bundleContext;
			_dataSource = dataSource;

			_objectName = new ObjectName(
				_TOMCAT_JDBC_POOL_OBJECT_NAME_PREFIX + poolName);
		}

		@Override
		public MBeanServer addingService(
			ServiceReference<MBeanServer> serviceReference) {

			MBeanServer mBeanServer = _bundleContext.getService(
				serviceReference);

			try {
				org.apache.tomcat.jdbc.pool.ConnectionPool jdbcConnectionPool =
					_dataSource.createPool();

				ConnectionPool jmxConnectionPool =
					jdbcConnectionPool.getJmxPool();

				mBeanServer.registerMBean(jmxConnectionPool, _objectName);
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			return mBeanServer;
		}

		@Override
		public void modifiedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

		@Override
		public void removedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {

			_bundleContext.ungetService(serviceReference);

			try {
				mBeanServer.unregisterMBean(_objectName);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		private final BundleContext _bundleContext;
		private final org.apache.tomcat.jdbc.pool.DataSource _dataSource;
		private final ObjectName _objectName;

	}

}