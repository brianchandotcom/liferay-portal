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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.repository.external.LegacyExternalRepositoryDefiner;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogImpl
	implements CacheRegistryItem, RepositoryClassDefinitionCatalog {

	public void afterPropertiesSet() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, RepositoryDefiner.class,
			new RepositoryDefinerServiceTrackerCustomizer());

		_serviceTracker.open();

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			ExternalRepositoryFactory externalRepositoryFactory =
				new ExternalRepositoryFactoryImpl(className, classLoader);

			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory,
				LanguageUtil.getResourceBundleLoader());
		}
	}

	public void destroy() {
		_serviceTracker.close();
	}

	@Override
	public Iterable<RepositoryClassDefinition>
		getExternalRepositoryClassDefinitions() {

		return _externalRepositoryClassDefinitions.values();
	}

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return _externalRepositoryClassDefinitions.keySet();
	}

	@Override
	public String getRegistryName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public RepositoryClassDefinition getRepositoryClassDefinition(
		String className) {

		return _repositoryClassDefinitions.get(className);
	}

	@Override
	public void invalidate() {
		for (RepositoryClassDefinition repositoryClassDefinition :
				_repositoryClassDefinitions.values()) {

			repositoryClassDefinition.invalidateCache();
		}
	}

	@Override
	public void registerLegacyExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory,
		ResourceBundleLoader resourceBundleLoader) {

		ExternalRepositoryFactoryUtil.registerExternalRepositoryFactory(
			className, externalRepositoryFactory);

		RepositoryDefiner repositoryDefiner =
			new LegacyExternalRepositoryDefiner(
				className, _legacyExternalRepositoryFactory,
				resourceBundleLoader);

		ServiceRegistration<RepositoryDefiner> serviceRegistration =
			registerRepositoryDefiner(repositoryDefiner);

		_serviceRegistrations.put(className, serviceRegistration);
	}

	public void setLegacyExternalRepositoryFactory(
		RepositoryFactory legacyExternalRepositoryFactory) {

		_legacyExternalRepositoryFactory = legacyExternalRepositoryFactory;
	}

	@Override
	public void unregisterLegacyExternalRepositoryFactory(String className) {
		ExternalRepositoryFactoryUtil.unregisterExternalRepositoryFactory(
			className);

		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(className);

		serviceRegistration.unregister();

		unregisterRepositoryDefiner(className);
	}

	protected ServiceRegistration<RepositoryDefiner> registerRepositoryDefiner(
		RepositoryDefiner repositoryDefiner) {

		return _bundleContext.registerService(
			RepositoryDefiner.class, repositoryDefiner, null);
	}

	protected void unregisterRepositoryDefiner(String className) {
		_externalRepositoryClassDefinitions.remove(className);

		_repositoryClassDefinitions.remove(className);
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final Map<String, RepositoryClassDefinition>
		_externalRepositoryClassDefinitions = new ConcurrentHashMap<>();
	private RepositoryFactory _legacyExternalRepositoryFactory;
	private final Map<String, RepositoryClassDefinition>
		_repositoryClassDefinitions = new ConcurrentHashMap<>();
	private final Map<String, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<>();
	private ServiceTracker
		<RepositoryDefiner, ServiceRegistration<RepositoryFactory>>
			_serviceTracker;

	private class RepositoryDefinerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RepositoryDefiner, ServiceRegistration<RepositoryFactory>> {

		@Override
		public ServiceRegistration<RepositoryFactory> addingService(
			ServiceReference<RepositoryDefiner> serviceReference) {

			RepositoryDefiner repositoryDefiner = _bundleContext.getService(
				serviceReference);

			String className = repositoryDefiner.getClassName();
			RepositoryClassDefinition repositoryClassDefinition =
				RepositoryClassDefinition.fromRepositoryDefiner(
					repositoryDefiner);

			if (repositoryDefiner.isExternalRepository()) {
				_externalRepositoryClassDefinitions.put(
					className, repositoryClassDefinition);
			}

			_repositoryClassDefinitions.put(
				className, repositoryClassDefinition);

			return _bundleContext.registerService(
				RepositoryFactory.class, repositoryClassDefinition,
				MapUtil.singletonDictionary("class.name", className));
		}

		@Override
		public void modifiedService(
			ServiceReference<RepositoryDefiner> serviceReference,
			ServiceRegistration<RepositoryFactory> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<RepositoryDefiner> serviceReference,
			ServiceRegistration<RepositoryFactory> serviceRegistration) {

			_bundleContext.ungetService(serviceReference);

			ServiceReference<RepositoryFactory>
				repositoryFactoryServiceReference =
					serviceRegistration.getReference();

			String className =
				(String)repositoryFactoryServiceReference.getProperty(
					"class.name");

			unregisterRepositoryDefiner(className);

			serviceRegistration.unregister();
		}

	}

}