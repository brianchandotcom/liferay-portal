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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryRegistryPlugin;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.repository.external.LegacyExternalRepositoryRegistryPlugin;
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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return _externalRepositoriesClassNames;
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration(
		String className) {

		return _repositoryConfigurations.get(className);
	}

	public void loadDefaultRepositoryRegistryPlugins() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			RepositoryRegistryPlugin.class,
			new RepositoryRegistryPluginServiceTrackerCustomizer());

		_serviceTracker.open();

		registerRepositoryRegistryPlugin(_liferayRepositoryRegistryPlugin);

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			ExternalRepositoryFactory externalRepositoryFactory =
				new ExternalRepositoryFactoryImpl(className, classLoader);

			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory);
		}
	}

	@Override
	public void registerLegacyExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory) {

		ExternalRepositoryFactoryUtil.registerExternalRepositoryFactory(
			className, externalRepositoryFactory);

		RepositoryRegistryPlugin repositoryRegistryPlugin =
			new LegacyExternalRepositoryRegistryPlugin(
				className, _legacyExternalRepositoryCreator);

		ServiceRegistration<RepositoryRegistryPlugin> serviceRegistration =
			registerRepositoryRegistryPlugin(repositoryRegistryPlugin);

		_serviceRegistrations.put(className, serviceRegistration);
	}

	public void setLegacyExternalRepositoryCreator(
		RepositoryCreator legacyExternalRepositoryCreator) {

		_legacyExternalRepositoryCreator = legacyExternalRepositoryCreator;
	}

	public void setLiferayRepositoryRegistryPlugin(
		RepositoryRegistryPlugin liferayRepositoryRegistryPlugin) {

		_liferayRepositoryRegistryPlugin = liferayRepositoryRegistryPlugin;
	}

	@Override
	public void unregisterLegacyExternalRepositoryFactory(String className) {
		ExternalRepositoryFactoryUtil.unregisterExternalRepositoryFactory(
			className);

		ServiceRegistration<RepositoryRegistryPlugin> serviceRegistration =
			_serviceRegistrations.remove(className);

		serviceRegistration.unregister();

		unregisterRepositoryRegistryPlugin(className);
	}

	protected RepositoryConfiguration createRepositoryConfiguration(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		DefaultRepositoryRegistry defaultRepositoryRegistry =
			new DefaultRepositoryRegistry();

		repositoryRegistryPlugin.registerCapabilities(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryCreator(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryEventListeners(
			defaultRepositoryRegistry);

		return defaultRepositoryRegistry;
	}

	protected ServiceRegistration<RepositoryRegistryPlugin>
		registerRepositoryRegistryPlugin(
			RepositoryRegistryPlugin repositoryRegistryPlugin) {

		Registry registry = RegistryUtil.getRegistry();

		return registry.registerService(
			RepositoryRegistryPlugin.class, repositoryRegistryPlugin);
	}

	protected void unregisterRepositoryRegistryPlugin(String className) {
		_externalRepositoriesClassNames.remove(className);

		_repositoryConfigurations.remove(className);
	}

	private Set<String> _externalRepositoriesClassNames =
		new ConcurrentHashSet<String>();
	private RepositoryCreator _legacyExternalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<String, RepositoryConfiguration> _repositoryConfigurations =
		new ConcurrentHashMap<String, RepositoryConfiguration>();
	private StringServiceRegistrationMap<RepositoryRegistryPlugin>
		_serviceRegistrations =
			new StringServiceRegistrationMap<RepositoryRegistryPlugin>();
	private ServiceTracker<RepositoryRegistryPlugin, RepositoryRegistryPlugin>
		_serviceTracker;

	private class RepositoryRegistryPluginServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RepositoryRegistryPlugin, RepositoryRegistryPlugin> {

		@Override
		public RepositoryRegistryPlugin addingService(
			ServiceReference<RepositoryRegistryPlugin> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			RepositoryRegistryPlugin repositoryRegistryPlugin =
				registry.getService(serviceReference);

			String className = repositoryRegistryPlugin.getClassName();

			if (repositoryRegistryPlugin.isExternalRepository()) {
				_externalRepositoriesClassNames.add(className);
			}

			_repositoryConfigurations.put(
				className,
				createRepositoryConfiguration(repositoryRegistryPlugin));

			return repositoryRegistryPlugin;
		}

		@Override
		public void modifiedService(
			ServiceReference<RepositoryRegistryPlugin> serviceReference,
			RepositoryRegistryPlugin service) {

			String className = service.getClassName();

			if (service.isExternalRepository()) {
				_externalRepositoriesClassNames.add(className);
			}
			else {
				_externalRepositoriesClassNames.remove(className);
			}

			_repositoryConfigurations.put(
				service.getClassName(), createRepositoryConfiguration(service));
		}

		@Override
		public void removedService(
			ServiceReference<RepositoryRegistryPlugin> serviceReference,
			RepositoryRegistryPlugin service) {

			unregisterRepositoryRegistryPlugin(service.getClassName());
		}

	}

}