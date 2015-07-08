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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.InitializedLocalRepository;
import com.liferay.portal.repository.InitializedRepository;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.capabilities.ConfigurationCapabilityImpl;
import com.liferay.portal.repository.capabilities.LiferayRepositoryEventTriggerCapability;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinition
	implements RepositoryConfiguration, RepositoryFactory,
			   RepositoryFactoryRegistry {

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

		return capabilityLocalRepository;
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

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

		return capabilityRepository;
	}

	public String getClassName() {
		return _repositoryDefiner.getClassName();
	}

	public String getRepositoryTypeLabel(Locale locale) {
		return _repositoryDefiner.getRepositoryTypeLabel(locale);
	}

	@Override
	public String[] getSupportedConfigurations() {
		return _repositoryDefiner.getSupportedConfigurations();
	}

	@Override
	public String[][] getSupportedParameters() {
		return _repositoryDefiner.getSupportedParameters();
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

	protected void setUpCommonCapabilities(
		DocumentRepository documentRepository,
		DefaultCapabilityRegistry capabilityRegistry,
		RepositoryEventTrigger repositoryEventTrigger) {

		if (!capabilityRegistry.isCapabilityProvided(
				ConfigurationCapability.class)) {

			RepositoryServiceAdapter repositoryServiceAdapter =
				RepositoryServiceAdapter.create(documentRepository);

			capabilityRegistry.addExportedCapability(
				ConfigurationCapability.class,
				new ConfigurationCapabilityImpl(
					documentRepository, repositoryServiceAdapter));
		}

		if (!capabilityRegistry.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			capabilityRegistry.addExportedCapability(
				RepositoryEventTriggerCapability.class,
				new LiferayRepositoryEventTriggerCapability(
					repositoryEventTrigger));
		}
	}

	private final RepositoryDefiner _repositoryDefiner;
	private RepositoryFactory _repositoryFactory;
	private final RepositoryEventTrigger _rootRepositoryEventTrigger;

}