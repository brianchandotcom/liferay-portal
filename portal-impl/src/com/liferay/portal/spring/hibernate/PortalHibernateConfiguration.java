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

package com.liferay.portal.spring.hibernate;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.internal.change.tracking.hibernate.CTSQLInterceptor;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.type.spi.TypeConfiguration;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class PortalHibernateConfiguration extends LocalSessionFactoryBean {

	@Override
	public void afterPropertiesSet() throws IOException {
		Dialect dialect = DialectDetector.getDialect(_dataSource);

		if (DBManagerUtil.getDBType(dialect) == DBType.ORACLE) {

			// This must be done before the instantiating Configuration to
			// ensure that org.hibernate.cfg.Environment's static init block can
			// see it

			System.setProperty(
				PropsKeys.HIBERNATE_JDBC_USE_STREAMS_FOR_BINARY, "true");
		}

		Properties properties = PropsUtil.getProperties();

		if (DBManagerUtil.getDBType(dialect) == DBType.SYBASE) {
			properties.setProperty(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");
		}

		properties.put("javax.persistence.validation.mode", "none");

		if (Validator.isNull(PropsValues.HIBERNATE_DIALECT)) {
			Class<?> clazz = dialect.getClass();

			properties.setProperty("hibernate.dialect", clazz.getName());
		}

		properties.setProperty("hibernate.cache.use_query_cache", "false");
		properties.setProperty(
			"hibernate.cache.use_second_level_cache", "false");

		properties.remove("hibernate.cache.region.factory_class");

		properties.setProperty(
			"hibernate.query.sql.jdbc_style_params_base", "true");

		properties.setProperty(
			"hibernate.allow_update_outside_transaction", "true");

		setHibernateProperties(properties);

		BootstrapServiceRegistryBuilder bootstrapServiceRegistryBuilder =
			new BootstrapServiceRegistryBuilder();

		bootstrapServiceRegistryBuilder.applyClassLoader(
			getConfigurationClassLoader());

		bootstrapServiceRegistryBuilder.applyIntegrator(
			GlobalEventListenerIntegrator.INSTANCE);

		if (_mvccEnabled) {
			bootstrapServiceRegistryBuilder.applyIntegrator(
				new CTModelIntegrator());
			bootstrapServiceRegistryBuilder.applyIntegrator(
				MVCCEventListenerIntegrator.INSTANCE);

			setEntityInterceptor(new CTSQLInterceptor());
		}

		setMetadataSources(
			new MetadataSources(bootstrapServiceRegistryBuilder.build()));

		super.afterPropertiesSet();
	}

	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);

		_dataSource = dataSource;
	}

	public void setMvccEnabled(boolean mvccEnabled) {
		_mvccEnabled = mvccEnabled;
	}

	protected static Map<String, Class<?>> getPreloadClassLoaderClasses() {
		try {
			Map<String, Class<?>> classes = new HashMap<>();

			for (String className : _PRELOAD_CLASS_NAMES) {
				ClassLoader portalClassLoader =
					PortalClassLoaderUtil.getClassLoader();

				Class<?> clazz = portalClassLoader.loadClass(className);

				classes.put(className, clazz);
			}

			return classes;
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
	}

	@Override
	protected SessionFactory buildSessionFactory(
			LocalSessionFactoryBuilder localSessionFactoryBuilder)
		throws HibernateException {

		try {
			String[] resources = getConfigurationResources();

			for (String resource : resources) {
				try {
					readResource(localSessionFactoryBuilder, resource);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		SessionFactory sessionFactory = super.buildSessionFactory(
			localSessionFactoryBuilder);

		SessionFactoryImplementor sessionFactoryImplementor =
			(SessionFactoryImplementor)sessionFactory;

		MetamodelImplementor metamodelImplementor =
			sessionFactoryImplementor.getMetamodel();

		TypeConfiguration typeConfiguration =
			metamodelImplementor.getTypeConfiguration();

		try {
			_META_MODEL_FIELD.set(
				sessionFactory,
				ProxyUtil.newDelegateProxyInstance(
					MetamodelImplementor.class.getClassLoader(),
					MetamodelImplementor.class,
					new SessionFactoryDelegate(
						typeConfiguration.getImportMap()),
					metamodelImplementor));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to inject optimized query plan cache", exception);
			}
		}

		return sessionFactory;
	}

	protected ClassLoader getConfigurationClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected String[] getConfigurationResources() {
		return PropsUtil.getArray(PropsKeys.HIBERNATE_CONFIGS);
	}

	protected void readResource(
			Configuration configuration, InputStream inputStream)
		throws Exception {

		if (inputStream == null) {
			return;
		}

		configuration.addInputStream(inputStream);

		inputStream.close();
	}

	protected void readResource(Configuration configuration, String resource)
		throws Exception {

		ClassLoader classLoader = getConfigurationClassLoader();

		if (resource.startsWith("classpath*:")) {
			String name = resource.substring("classpath*:".length());

			Enumeration<URL> enumeration = classLoader.getResources(name);

			if (_log.isDebugEnabled() && !enumeration.hasMoreElements()) {
				_log.debug("No resources found for " + name);
			}

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				InputStream inputStream = url.openStream();

				readResource(configuration, inputStream);
			}
		}
		else {
			InputStream inputStream = classLoader.getResourceAsStream(resource);

			readResource(configuration, inputStream);
		}
	}

	private static final Field _META_MODEL_FIELD;

	private static final String[] _PRELOAD_CLASS_NAMES =
		PropsValues.
			SPRING_HIBERNATE_CONFIGURATION_PROXY_FACTORY_PRELOAD_CLASSLOADER_CLASSES;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalHibernateConfiguration.class);

	static {
		try {
			_META_MODEL_FIELD = ReflectionUtil.getDeclaredField(
				SessionFactoryImpl.class, "metamodel");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private DataSource _dataSource;
	private boolean _mvccEnabled = true;

	private static class SessionFactoryDelegate {

		public String getImportedClassName(String className) {
			return _imports.get(className);
		}

		private SessionFactoryDelegate(Map<String, String> imports) {
			_imports = new HashMap<>(imports);
		}

		private final Map<String, String> _imports;

	}

}