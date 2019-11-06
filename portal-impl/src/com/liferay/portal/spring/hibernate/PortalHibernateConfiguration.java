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
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.dao.orm.hibernate.event.MVCCSynchronizerPostUpdateEventListener;
import com.liferay.portal.dao.orm.hibernate.event.NestableAutoFlushEventListener;
import com.liferay.portal.dao.orm.hibernate.event.NestableFlushEventListener;
import com.liferay.portal.internal.change.tracking.hibernate.CTSQLInterceptor;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

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

import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.query.QueryPlanCache;
import org.hibernate.event.AutoFlushEventListener;
import org.hibernate.event.EventListeners;
import org.hibernate.event.FlushEventListener;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.OuterJoinLoadable;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

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
	public SessionFactory buildSessionFactory() throws Exception {
		setBeanClassLoader(getConfigurationClassLoader());

		SessionFactoryImplementor sessionFactoryImplementor =
			(SessionFactoryImplementor)super.buildSessionFactory();

		if (!_mvccEnabled) {
			return sessionFactoryImplementor;
		}

		boolean containCTModel = false;

		Map<String, ClassMetadata> classMetadatas =
			sessionFactoryImplementor.getAllClassMetadata();

		for (ClassMetadata classMetadata : classMetadatas.values()) {
			Class<?> mappedClass = classMetadata.getMappedClass(
				EntityMode.POJO);

			if (!CTModel.class.isAssignableFrom(mappedClass)) {
				continue;
			}

			Class<?> modelClass = _findCTModelClass(
				classMetadata, mappedClass.getSuperclass());

			if (modelClass == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find CT model class for " + mappedClass);
				}
			}
			else {
				containCTModel = true;
			}
		}

		CTSQLInterceptor ctSQLInterceptor =
			(CTSQLInterceptor)sessionFactoryImplementor.getInterceptor();

		ctSQLInterceptor.setEnabled(containCTModel);

		return sessionFactoryImplementor;
	}

	@Override
	public void destroy() throws HibernateException {
		SessionFactory sessionFactory = getSessionFactory();

		Map<String, ClassMetadata> classMetadatas =
			sessionFactory.getAllClassMetadata();

		for (ClassMetadata classMetadata : classMetadatas.values()) {
			Class<?> mappedClass = classMetadata.getMappedClass(
				EntityMode.POJO);

			if (!CTModel.class.isAssignableFrom(mappedClass)) {
				continue;
			}

			OuterJoinLoadable outerJoinLoadable =
				(OuterJoinLoadable)classMetadata;

			CTModelRegistry.unregisterCTModel(outerJoinLoadable.getTableName());
		}

		setBeanClassLoader(null);

		super.destroy();
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

		Dialect dialect = DialectDetector.getDialect(getDataSource());

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
				eventListeners.setFlushEventListeners(
					new FlushEventListener[] {
						NestableFlushEventListener.INSTANCE
					});
				eventListeners.setPostUpdateEventListeners(
					new PostUpdateEventListener[] {
						MVCCSynchronizerPostUpdateEventListener.INSTANCE
					});

				configuration.setInterceptor(new CTSQLInterceptor());
			}
		}
		catch (Exception e1) {
			_log.error(e1, e1);
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
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to inject optimized query plan cache", e);
			}
		}

		return sessionFactory;
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

	private Class<?> _findCTModelClass(
		ClassMetadata classMetadata, Class<?> modelClass) {

		while (BaseModelImpl.class != modelClass) {
			for (Class<?> interfaceClazz : modelClass.getInterfaces()) {
				if (BaseModel.class.isAssignableFrom(interfaceClazz)) {
					modelClass = interfaceClazz;

					OuterJoinLoadable outerJoinLoadable =
						(OuterJoinLoadable)classMetadata;

					String[] identifierColumnNames =
						outerJoinLoadable.getPropertyColumnNames(
							outerJoinLoadable.getIdentifierPropertyName());

					CTModelRegistry.registerCTModel(
						outerJoinLoadable.getTableName(),
						new CTModelRegistration(
							modelClass, identifierColumnNames[0]));

					return modelClass;
				}
			}

			modelClass = modelClass.getSuperclass();
		}

		return null;
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