/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.jdbc.pool.metrics.HikariConnectionPoolMetrics;
import com.liferay.portal.dao.jdbc.util.AntiTimeDriftDataSourceWrapper;
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

import com.zaxxer.hikari.HikariDataSource;

import java.io.Closeable;

import java.lang.reflect.Field;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

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

		if (dataSource instanceof Closeable) {
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
			try {
				testDatabaseClass(driverClassName);
			}
			catch (ClassNotFoundException classNotFoundException) {
				_log.error(
					StringBundler.concat(
						"Unable to find the JDBC driver class ",
						driverClassName, " in a JAR in the directory ",
						PropsValues.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR));

				throw classNotFoundException;
			}

			_waitForJDBCConnection(properties);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Data source properties:\n");

			_log.debug(PropertiesUtil.toString(properties));
		}

		DataSource dataSource = initDataSourceHikariCP(properties);

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

		if (Boolean.getBoolean("jdbc.data.source.anti.time.drift")) {
			DBType dbType = DBManagerUtil.getDBType(
				DialectDetector.getDialect(dataSource));

			if (dbType == DBType.DB2) {
				dataSource = new AntiTimeDriftDataSourceWrapper(dataSource);
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

			if (StringUtil.equalsIgnoreCase(key, "url")) {
				key = "jdbcUrl";
			}

			// Ignore Liferay property

			if (isPropertyLiferay(key)) {
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

	protected boolean isPropertyLiferay(String key) {
		if (StringUtil.equalsIgnoreCase(key, "jndi.name")) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		DataSourceFactoryImpl.class);

	private ServiceRegistration<?> _serviceRegistration;

}