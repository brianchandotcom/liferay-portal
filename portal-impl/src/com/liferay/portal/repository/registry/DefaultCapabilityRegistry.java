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

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.BaseCapabilityProvider;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DefaultCapabilityRegistry
	extends BaseCapabilityProvider
	implements CapabilityProvider, CapabilityRegistry<DocumentRepository> {

	public DefaultCapabilityRegistry(DocumentRepository documentRepository) {
		_documentRepository = documentRepository;
	}

	@Override
	public <S extends Capability> void addExportedCapability(
		Class<S> capabilityClass, S capability) {

		super.addExportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability> void addSupportedCapability(
		Class<S> capabilityClass, S capability) {

		super.addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public DocumentRepository getTarget() {
		return _documentRepository;
	}

	public LocalRepository invokeCapabilityWrappers(
		LocalRepository localRepository) {

		Map<Class<? extends Capability>, Capability> capabilities =
			getCapabilities();

		for (Capability capability : capabilities.values()) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				localRepository = repositoryWrapperAware.wrapLocalRepository(
					localRepository);
			}
		}

		return localRepository;
	}

	public Repository invokeCapabilityWrappers(Repository repository) {
		Map<Class<? extends Capability>, Capability> capabilities =
			getCapabilities();

		for (Capability capability : capabilities.values()) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				repository = repositoryWrapperAware.wrapRepository(repository);
			}
		}

		return repository;
	}

	public void registerCapabilityRepositoryEvents(
		RepositoryEventRegistry repositoryEventRegistry) {

		Map<Class<? extends Capability>, Capability> capabilities =
			getCapabilities();

		for (Capability capability : capabilities.values()) {
			if (capability instanceof RepositoryEventAware) {
				RepositoryEventAware repositoryEventAware =
					(RepositoryEventAware)capability;

				repositoryEventAware.registerRepositoryEventListeners(
					repositoryEventRegistry);
			}
		}
	}

	@Override
	protected String getProviderKey() {
		return String.valueOf(_documentRepository.getRepositoryId());
	}

	private final DocumentRepository _documentRepository;

}