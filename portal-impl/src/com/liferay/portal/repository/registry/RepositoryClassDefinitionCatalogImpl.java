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
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.repository.external.LegacyExternalRepositoryDefiner;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMapImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogImpl
	implements CacheRegistryItem, RepositoryClassDefinitionCatalog {

	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			RepositoryDefiner.class,
			new RepositoryDefinerServiceTrackerCustomizer());

		_serviceTracker.open();

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			ExternalRepositoryFactory externalRepositoryFactory =
				new ExternalRepositoryFactoryImpl(className, classLoader);

			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory,
				LanguageUtil.getPortalResourceBundleLoader());
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

		ServiceRegistration<RepositoryDefiner> serviceRegistration =
			_serviceRegistrations.remove(className);

		serviceRegistration.unregister();

		unregisterRepositoryDefiner(className);
	}

	protected ServiceRegistration<RepositoryDefiner> registerRepositoryDefiner(
		RepositoryDefiner repositoryDefiner) {

		Registry registry = RegistryUtil.getRegistry();

		return registry.registerService(
			RepositoryDefiner.class, repositoryDefiner);
	}

	protected void unregisterRepositoryDefiner(String className) {
		_externalRepositoryClassDefinitions.remove(className);

		_repositoryClassDefinitions.remove(className);
	}

	private final Map<String, RepositoryClassDefinition>
		_externalRepositoryClassDefinitions = new ConcurrentHashMap<>();
	private RepositoryFactory _legacyExternalRepositoryFactory;
	private final Map<String, RepositoryClassDefinition>
		_repositoryClassDefinitions = new ConcurrentHashMap<>();
	private final StringServiceRegistrationMap<RepositoryDefiner>
		_serviceRegistrations = new StringServiceRegistrationMapImpl<>();
	private ServiceTracker
		<RepositoryDefiner, ServiceRegistration<RepositoryFactory>>
			_serviceTracker;

	private class RepositoryDefinerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RepositoryDefiner, ServiceRegistration<RepositoryFactory>> {

		@Override
		public ServiceRegistration<RepositoryFactory> addingService(
			ServiceReference<RepositoryDefiner> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			RepositoryDefiner repositoryDefiner = registry.getService(
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

			Map<String, Object> properties = new HashMap<>();

			properties.put("class.name", className);

			return registry.registerService(
				RepositoryFactory.class, repositoryClassDefinition, properties);
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

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			ServiceReference<RepositoryFactory>
				repositoryFactoryServiceReference =
					serviceRegistration.getServiceReference();

			String className =
				(String)repositoryFactoryServiceReference.getProperty(
					"class.name");

			unregisterRepositoryDefiner(className);

			serviceRegistration.unregister();
		}

	}

}