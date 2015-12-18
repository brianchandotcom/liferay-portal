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

import com.liferay.portal.dao.orm.hibernate.event.MVCCSynchronizerPostUpdateEventListener;
import com.liferay.portal.dao.orm.hibernate.event.NestableAutoFlushEventListener;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.Converter;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import javassist.util.proxy.ProxyFactory;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.event.AutoFlushEventListener;
import org.hibernate.event.EventListeners;
import org.hibernate.event.PostUpdateEventListener;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class PortalHibernateConfiguration extends LocalSessionFactoryBean {

	@Override
	public SessionFactory buildSessionFactory() throws Exception {
		setBeanClassLoader(getConfigurationClassLoader());

		return super.buildSessionFactory();
	}

	@Override
	public void destroy() throws HibernateException {
		setBeanClassLoader(null);

		super.destroy();
	}

	public void setHibernateConfigurationConverter(
		Converter<String> hibernateConfigurationConverter) {

		_hibernateConfigurationConverter = hibernateConfigurationConverter;
	}

	public void setMvccEnabled(boolean mvccEnabled) {
		_mvccEnabled = mvccEnabled;
	}

	protected static Map<String, Class<?>> getPreloadClassLoaderClasses() {
		try {
			Map<String, Class<?>> classes = new HashMap<>();

			for (String className : _PRELOAD_CLASS_NAMES) {
				ClassLoader portalClassLoader =
					ClassLoaderUtil.getPortalClassLoader();

				Class<?> clazz = portalClassLoader.loadClass(className);

				classes.put(className, clazz);
			}

			return classes;
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}

	protected ClassLoader getConfigurationClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected String[] getConfigurationResources() {
		return PropsUtil.getArray(PropsKeys.HIBERNATE_CONFIGS);
	}

	@Override
	protected Configuration newConfiguration() {
		Configuration configuration = new Configuration();

		Properties properties = PropsUtil.getProperties();

		Properties hibernateProperties = getHibernateProperties();

		for (Map.Entry<Object, Object> entry : hibernateProperties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			properties.setProperty(key, value);
		}

		if (Validator.isNull(PropsValues.HIBERNATE_DIALECT)) {
			Dialect dialect = DialectDetector.getDialect(getDataSource());

			DBManagerUtil.setDB(dialect, getDataSource());

			Class<?> clazz = dialect.getClass();

			properties.setProperty("hibernate.dialect", clazz.getName());
		}

		properties.setProperty("hibernate.cache.use_query_cache", "false");
		properties.setProperty(
			"hibernate.cache.use_second_level_cache", "false");

		properties.remove("hibernate.cache.region.factory_class");

		configuration.setProperties(properties);

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

			if (_mvccEnabled) {
				EventListeners eventListeners =
					configuration.getEventListeners();

				eventListeners.setAutoFlushEventListeners(
					new AutoFlushEventListener[] {
						NestableAutoFlushEventListener.INSTANCE
					});
				eventListeners.setPostUpdateEventListeners(
					new PostUpdateEventListener[] {
						MVCCSynchronizerPostUpdateEventListener.INSTANCE
					});
			}
		}
		catch (Exception e1) {
			_log.error(e1, e1);
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

	protected void readResource(
			Configuration configuration, InputStream inputStream)
		throws Exception {

		if (inputStream == null) {
			return;
		}

		if (_hibernateConfigurationConverter != null) {
			String configurationString = StringUtil.read(inputStream);

			configurationString = _hibernateConfigurationConverter.convert(
				configurationString);

			inputStream = new UnsyncByteArrayInputStream(
				configurationString.getBytes());
		}

		configuration.addInputStream(inputStream);

		inputStream.close();
	}

	protected void readResource(Configuration configuration, String resource)
		throws Exception {

		ClassLoader classLoader = getConfigurationClassLoader();

		if (resource.startsWith("classpath*:")) {
			String name = resource.substring("classpath*:".length());

			Enumeration<URL> enu = classLoader.getResources(name);

			if (_log.isDebugEnabled() && !enu.hasMoreElements()) {
				_log.debug("No resources found for " + name);
			}

			while (enu.hasMoreElements()) {
				URL url = enu.nextElement();

				InputStream inputStream = url.openStream();

				readResource(configuration, inputStream);
			}
		}
		else {
			InputStream inputStream = classLoader.getResourceAsStream(resource);

			readResource(configuration, inputStream);
		}
	}

	private static final String[] _PRELOAD_CLASS_NAMES =
		PropsValues.SPRING_HIBERNATE_CONFIGURATION_PROXY_FACTORY_PRELOAD_CLASSLOADER_CLASSES;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalHibernateConfiguration.class);

	private static final Map<ProxyFactory, ClassLoader>
		_proxyFactoryClassLoaders = new WeakHashMap<>();

	static {
		ProxyFactory.classLoaderProvider =
			new ProxyFactory.ClassLoaderProvider() {

				@Override
				public ClassLoader get(ProxyFactory proxyFactory) {
					synchronized (_proxyFactoryClassLoaders) {
						ClassLoader classLoader = _proxyFactoryClassLoaders.get(
							proxyFactory);

						if (classLoader != null) {
							return classLoader;
						}

						classLoader = ClassLoaderUtil.getPortalClassLoader();

						ClassLoader contextClassLoader =
							ClassLoaderUtil.getContextClassLoader();

						if (classLoader != contextClassLoader) {
							classLoader = new PreloadClassLoader(
								contextClassLoader,
								getPreloadClassLoaderClasses());
						}

						_proxyFactoryClassLoaders.put(
							proxyFactory, classLoader);

						return classLoader;
					}
				}

			};
	}

	private Converter<String> _hibernateConfigurationConverter;
	private boolean _mvccEnabled = true;

}