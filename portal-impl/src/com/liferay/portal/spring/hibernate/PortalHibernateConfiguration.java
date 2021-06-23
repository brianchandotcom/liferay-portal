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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.asm.ASMWrapperUtil;
import com.liferay.portal.dao.orm.hibernate.event.MVCCSynchronizerPostUpdateEventListener;
import com.liferay.portal.dao.orm.hibernate.event.ResetOriginalValuesLoadEventListener;
import com.liferay.portal.dao.orm.hibernate.event.ResetOriginalValuesPostLoadEventListener;
import com.liferay.portal.internal.change.tracking.hibernate.CTSQLInterceptor;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.PropsKeys;
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
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.util.proxy.ProxyFactory;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.query.QueryPlanCache;
import org.hibernate.event.EventListeners;
import org.hibernate.event.LoadEventListener;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PostUpdateEventListener;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class PortalHibernateConfiguration extends LocalSessionFactoryBean {

	public PortalHibernateConfiguration() {
		Properties properties = new Properties();

		properties.put("javax.persistence.validation.mode", "none");

		setHibernateProperties(properties);
	}

	@Override
	public void afterPropertiesSet() throws IOException {
		BootstrapServiceRegistryBuilder bootstrapServiceRegistryBuilder =
			new BootstrapServiceRegistryBuilder();

		bootstrapServiceRegistryBuilder.applyClassLoader(
			getConfigurationClassLoader());

		if (_mvccEnabled) {
			bootstrapServiceRegistryBuilder.applyIntegrator(
				new CTModelIntegrator());
		}

		setMetadataSources(
			new MetadataSources(bootstrapServiceRegistryBuilder.build()));

		super.afterPropertiesSet();
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

	protected ClassLoader getConfigurationClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected String[] getConfigurationResources() {
		return PropsUtil.getArray(PropsKeys.HIBERNATE_CONFIGS);
	}

	@Override
	protected Configuration newConfiguration() {
		Dialect dialect = DialectDetector.getDialect(getDataSource());

		if (DBManagerUtil.getDBType(dialect) == DBType.ORACLE) {

			// This must be done before the instantiating Configuration to
			// ensure that org.hibernate.cfg.Environment's static init block can
			// see it

			System.setProperty(
				PropsKeys.HIBERNATE_JDBC_USE_STREAMS_FOR_BINARY, "true");
		}

		Configuration configuration = new Configuration();

		Properties properties = PropsUtil.getProperties();

		Properties hibernateProperties = getHibernateProperties();

		for (Map.Entry<Object, Object> entry : hibernateProperties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			properties.setProperty(key, value);
		}

		if (DBManagerUtil.getDBType(dialect) == DBType.SYBASE) {
			properties.setProperty(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE, "0");
		}

		if (Validator.isNull(PropsValues.HIBERNATE_DIALECT)) {
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
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}

			EventListeners eventListeners = configuration.getEventListeners();

			eventListeners.setLoadEventListeners(
				new LoadEventListener[] {
					ResetOriginalValuesLoadEventListener.INSTANCE
				});
			eventListeners.setPostLoadEventListeners(
				new PostLoadEventListener[] {
					ResetOriginalValuesPostLoadEventListener.INSTANCE
				});

			if (_mvccEnabled) {
				eventListeners.setPostUpdateEventListeners(
					new PostUpdateEventListener[] {
						MVCCSynchronizerPostUpdateEventListener.INSTANCE
					});

				configuration.setInterceptor(new CTSQLInterceptor());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return configuration;
	}

	@Override
	protected SessionFactory newSessionFactory(Configuration configuration)
		throws HibernateException {

		SessionFactory sessionFactory = super.newSessionFactory(configuration);

		if (Objects.equals(
				PropsValues.
					HIBERNATE_SESSION_FACTORY_IMPORTED_CLASS_NAME_REGEXP,
				".*")) {

			// For wildcard match, simply disable the optimization

			return sessionFactory;
		}

		try {
			Field queryPlanCacheField = ReflectionUtil.getDeclaredField(
				sessionFactory.getClass(), "queryPlanCache");

			QueryPlanCache queryPlanCache =
				(QueryPlanCache)queryPlanCacheField.get(sessionFactory);

			Field sessionFactoryField = ReflectionUtil.getDeclaredField(
				QueryPlanCache.class, "factory");

			sessionFactoryField.set(
				queryPlanCache,
				_wrapSessionFactoryImplementor(
					(SessionFactoryImplementor)sessionFactory,
					configuration.getImports()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to inject optimized query plan cache", exception);
			}
		}

		return sessionFactory;
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

	private SessionFactoryImplementor _wrapSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor,
		Map<String, String> imports) {

		Object sessionFactoryDelegate = null;

		if (Validator.isBlank(
				PropsValues.
					HIBERNATE_SESSION_FACTORY_IMPORTED_CLASS_NAME_REGEXP)) {

			sessionFactoryDelegate = new NoPatternSessionFactoryDelegate(
				imports);
		}
		else {
			sessionFactoryDelegate = new PatternedSessionFactoryDelegate(
				imports,
				PropsValues.
					HIBERNATE_SESSION_FACTORY_IMPORTED_CLASS_NAME_REGEXP,
				sessionFactoryImplementor);
		}

		return ASMWrapperUtil.createASMWrapper(
			SessionFactoryImplementor.class.getClassLoader(),
			SessionFactoryImplementor.class, sessionFactoryDelegate,
			sessionFactoryImplementor);
	}

	private static final String[] _PRELOAD_CLASS_NAMES =
		PropsValues.
			SPRING_HIBERNATE_CONFIGURATION_PROXY_FACTORY_PRELOAD_CLASSLOADER_CLASSES;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalHibernateConfiguration.class);

	private static final Map<ProxyFactory, ClassLoader>
		_proxyFactoryClassLoaders = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

	static {
		ProxyFactory.classLoaderProvider =
			new ProxyFactory.ClassLoaderProvider() {

				@Override
				public ClassLoader get(ProxyFactory proxyFactory) {
					return _proxyFactoryClassLoaders.computeIfAbsent(
						proxyFactory,
						(ProxyFactory pf) -> {
							ClassLoader classLoader =
								PortalClassLoaderUtil.getClassLoader();

							Thread currentThread = Thread.currentThread();

							ClassLoader contextClassLoader =
								currentThread.getContextClassLoader();

							if (classLoader != contextClassLoader) {
								classLoader = new PreloadClassLoader(
									contextClassLoader,
									getPreloadClassLoaderClasses());
							}

							return classLoader;
						});
				}

			};
	}

	private boolean _mvccEnabled = true;

	private static class NoPatternSessionFactoryDelegate {

		public String getImportedClassName(String className) {
			return _imports.get(className);
		}

		protected NoPatternSessionFactoryDelegate(Map<String, String> imports) {
			_imports = new HashMap<>(imports);
		}

		private final Map<String, String> _imports;

	}

	private static class PatternedSessionFactoryDelegate
		extends NoPatternSessionFactoryDelegate {

		@Override
		public String getImportedClassName(String className) {
			String importedClassName = super.getImportedClassName(className);

			if (importedClassName != null) {
				return importedClassName;
			}

			Matcher matcher = _importedClassNamePattern.matcher(className);

			if (!matcher.matches()) {
				return null;
			}

			return _sessionFactoryImplementor.getImportedClassName(className);
		}

		private PatternedSessionFactoryDelegate(
			Map<String, String> imports, String importedClassNameRegexp,
			SessionFactoryImplementor sessionFactoryImplementor) {

			super(imports);

			_importedClassNamePattern = Pattern.compile(
				importedClassNameRegexp);

			_sessionFactoryImplementor = sessionFactoryImplementor;
		}

		private final Pattern _importedClassNamePattern;
		private final SessionFactoryImplementor _sessionFactoryImplementor;

	}

}