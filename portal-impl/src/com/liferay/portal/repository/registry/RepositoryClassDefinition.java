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

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.repository.InitializedLocalRepository;
import com.liferay.portal.repository.InitializedRepository;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.capabilities.LiferayRepositoryEventTriggerCapability;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinition
	implements RepositoryFactory, RepositoryFactoryRegistry {

	public static final RepositoryClassDefinition fromRepositoryDefiner(
		RepositoryDefiner repositoryDefiner) {

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(null);

		RepositoryClassDefinition repositoryClassDefinition =
			new RepositoryClassDefinition(
				repositoryDefiner, defaultRepositoryEventRegistry);

		repositoryDefiner.registerRepositoryFactory(repositoryClassDefinition);

		repositoryDefiner.registerRepositoryEventListeners(
			defaultRepositoryEventRegistry);

		return repositoryClassDefinition;
	}

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		if (_localRepositories.containsKey(repositoryId)) {
			return _localRepositories.get(repositoryId);
		}

		InitializedLocalRepository initializedLocalRepository =
			new InitializedLocalRepository();

		DefaultCapabilityRegistry defaultCapabilityRegistry =
			new DefaultCapabilityRegistry(initializedLocalRepository);

		_repositoryDefiner.registerCapabilities(defaultCapabilityRegistry);

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(_rootRepositoryEventTrigger);

		setUpCommonCapabilities(
			initializedLocalRepository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		defaultCapabilityRegistry.registerCapabilityRepositoryEvents(
			defaultRepositoryEventRegistry);

		LocalRepository localRepository =
			_repositoryFactory.createLocalRepository(repositoryId);

		LocalRepository wrappedLocalRepository =
			defaultCapabilityRegistry.invokeCapabilityWrappers(localRepository);

		CapabilityLocalRepository capabilityLocalRepository =
			new CapabilityLocalRepository(
				wrappedLocalRepository, defaultCapabilityRegistry,
				defaultRepositoryEventRegistry);

		initializedLocalRepository.setDocumentRepository(
			capabilityLocalRepository);

		_localRepositories.put(repositoryId, capabilityLocalRepository);

		return capabilityLocalRepository;
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		if (_repositories.containsKey(repositoryId)) {
			return _repositories.get(repositoryId);
		}

		InitializedRepository initializedRepository =
			new InitializedRepository();

		DefaultCapabilityRegistry defaultCapabilityRegistry =
			new DefaultCapabilityRegistry(initializedRepository);

		_repositoryDefiner.registerCapabilities(defaultCapabilityRegistry);

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(_rootRepositoryEventTrigger);

		setUpCommonCapabilities(
			initializedRepository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		Repository repository = _repositoryFactory.createRepository(
			repositoryId);

		defaultCapabilityRegistry.registerCapabilityRepositoryEvents(
			defaultRepositoryEventRegistry);

		Repository wrappedRepository =
			defaultCapabilityRegistry.invokeCapabilityWrappers(repository);

		CapabilityRepository capabilityRepository = new CapabilityRepository(
			wrappedRepository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		initializedRepository.setDocumentRepository(capabilityRepository);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			_repositories.put(repositoryId, capabilityRepository);
		}

		return capabilityRepository;
	}

	public String getClassName() {
		return _repositoryDefiner.getClassName();
	}

	public RepositoryConfiguration getRepositoryConfiguration() {
		return _repositoryDefiner.getRepositoryConfiguration();
	}

	public String getRepositoryTypeLabel(Locale locale) {
		return _repositoryDefiner.getRepositoryTypeLabel(locale);
	}

	public void invalidateCache() {
		_localRepositories.clear();
		_repositories.clear();
	}

	@Override
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		if (_repositoryFactory != null) {
			throw new IllegalStateException(
				"Repository factory already exists");
		}

		_repositoryFactory = repositoryFactory;
	}

	protected RepositoryClassDefinition(
		RepositoryDefiner repositoryDefiner,
		RepositoryEventTrigger rootRepositoryEventTrigger) {

		_repositoryDefiner = repositoryDefiner;
		_rootRepositoryEventTrigger = rootRepositoryEventTrigger;
	}

	protected void invalidateCachedRepository(long repositoryId) {
		_localRepositories.remove(repositoryId);
		_repositories.remove(repositoryId);
	}

	protected void setUpCommonCapabilities(
		DocumentRepository documentRepository,
		DefaultCapabilityRegistry capabilityRegistry,
		RepositoryEventTrigger repositoryEventTrigger) {

		if (!capabilityRegistry.isCapabilityProvided(
				ConfigurationCapability.class)) {

			capabilityRegistry.addExportedCapability(
				ConfigurationCapability.class,
				_portalCapabilityLocator.getConfigurationCapability(
					documentRepository));
		}

		if (!capabilityRegistry.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			capabilityRegistry.addExportedCapability(
				RepositoryEventTriggerCapability.class,
				new LiferayRepositoryEventTriggerCapability(
					repositoryEventTrigger));
		}

		capabilityRegistry.addSupportedCapability(
			CacheCapability.class, new CacheCapability());
	}

	private static PortalCapabilityLocator _portalCapabilityLocator =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalCapabilityLocator.class, RepositoryClassDefinition.class,
			"_portalCapabilityLocator", false);

	private final Map<Long, LocalRepository> _localRepositories =
		new ConcurrentHashMap<>();
	private final Map<Long, Repository> _repositories =
		new ConcurrentHashMap<>();
	private final RepositoryDefiner _repositoryDefiner;
	private RepositoryFactory _repositoryFactory;
	private final RepositoryEventTrigger _rootRepositoryEventTrigger;

	private class CacheCapability implements Capability, RepositoryEventAware {

		@Override
		public void registerRepositoryEventListeners(
			RepositoryEventRegistry repositoryEventRegistry) {

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Delete.class, LocalRepository.class,
				new RepositoryEventListener
					<RepositoryEventType.Delete, LocalRepository>() {

					@Override
					public void execute(LocalRepository localRepository) {
						invalidateCachedRepository(
							localRepository.getRepositoryId());
					}

				});
		}

	}

}