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
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogImpl
	implements RepositoryClassDefinitionCatalog {

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return _externalRepositoriesClassNames;
	}

	@Override
	public RepositoryClassDefinition getRepositoryClassDefinition(
		String className) {

		return _repositoryClassDefinitions.get(className);
	}

	public void loadDefaultRepositoryDefiners() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			RepositoryDefiner.class,
			new RepositoryDefinerServiceTrackerCustomizer());

		_serviceTracker.open();

		for (RepositoryDefiner repositoryDefiner : _repositoryDefiners) {
			registerRepositoryDefiner(repositoryDefiner);
		}

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

		RepositoryDefiner repositoryDefiner =
			new LegacyExternalRepositoryDefiner(
				className, _legacyExternalRepositoryFactory);

		ServiceRegistration<RepositoryDefiner> serviceRegistration =
			registerRepositoryDefiner(repositoryDefiner);

		_serviceRegistrations.put(className, serviceRegistration);
	}

	public void setLegacyExternalRepositoryFactory(
		RepositoryFactory legacyExternalRepositoryFactory) {

		_legacyExternalRepositoryFactory = legacyExternalRepositoryFactory;
	}

	public void setRepositoryDefiners(
		List<RepositoryDefiner> repositoryDefiners) {

		_repositoryDefiners = repositoryDefiners;
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

	protected RepositoryClassDefinition createRepositoryClassDefinition(
		RepositoryDefiner repositoryDefiner) {

		RepositoryClassDefinition repositoryClassDefinition =
			new RepositoryClassDefinition(repositoryDefiner);

		repositoryDefiner.registerRepositoryFactory(repositoryClassDefinition);
		repositoryDefiner.registerRepositoryEventListeners(
			repositoryClassDefinition);

		return repositoryClassDefinition;
	}

	protected ServiceRegistration<RepositoryDefiner>
		registerRepositoryDefiner(
			RepositoryDefiner repositoryDefiner) {

		Registry registry = RegistryUtil.getRegistry();

		return registry.registerService(
			RepositoryDefiner.class, repositoryDefiner);
	}

	protected void unregisterRepositoryDefiner(String className) {
		_externalRepositoriesClassNames.remove(className);

		_repositoryClassDefinitions.remove(className);
	}

	private final Set<String> _externalRepositoriesClassNames =
		new ConcurrentHashSet<String>();
	private RepositoryFactory _legacyExternalRepositoryFactory;
	private final Map<String, RepositoryClassDefinition>
		_repositoryClassDefinitions =
			new ConcurrentHashMap<String, RepositoryClassDefinition>();
	private List<RepositoryDefiner> _repositoryDefiners;
	private final StringServiceRegistrationMap<RepositoryDefiner>
		_serviceRegistrations =
			new StringServiceRegistrationMap<RepositoryDefiner>();
	private ServiceTracker<RepositoryDefiner, RepositoryDefiner>
		_serviceTracker;

	private class RepositoryDefinerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RepositoryDefiner, RepositoryDefiner> {

		@Override
		public RepositoryDefiner addingService(
			ServiceReference<RepositoryDefiner> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			RepositoryDefiner repositoryDefiner = registry.getService(
				serviceReference);

			String className = repositoryDefiner.getClassName();

			if (repositoryDefiner.isExternalRepository()) {
				_externalRepositoriesClassNames.add(className);
			}

			_repositoryClassDefinitions.put(
				className, createRepositoryClassDefinition(repositoryDefiner));

			return repositoryDefiner;
		}

		@Override
		public void modifiedService(
			ServiceReference<RepositoryDefiner> serviceReference,
			RepositoryDefiner repositoryDefiner) {

			String className = repositoryDefiner.getClassName();

			if (repositoryDefiner.isExternalRepository()) {
				_externalRepositoriesClassNames.add(className);
			}
			else {
				_externalRepositoriesClassNames.remove(className);
			}

			_repositoryClassDefinitions.put(
				repositoryDefiner.getClassName(),
				createRepositoryClassDefinition(repositoryDefiner));
		}

		@Override
		public void removedService(
			ServiceReference<RepositoryDefiner> serviceReference,
			RepositoryDefiner repositoryDefiner) {

			unregisterRepositoryDefiner(repositoryDefiner.getClassName());
		}

	}

}