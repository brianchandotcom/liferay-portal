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

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.DynamicCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
public class LiferayDynamicCapability
	implements DynamicCapability, RepositoryEventAware, RepositoryWrapperAware {

	public LiferayDynamicCapability(
		BundleContext bundleContext, DocumentRepository documentRepository) {

		_documentRepository = documentRepository;

		_capabilities = ServiceTrackerListFactory.open(
			bundleContext, Capability.class, null,
			new ServiceTrackerCustomizer<Capability, Capability>() {

				@Override
				public Capability addingService(
					ServiceReference<Capability> serviceReference) {

					Capability capability = bundleContext.getService(
						serviceReference);

					CapabilityRegistration capabilityRegistration =
						new CapabilityRegistration();

					String repositoryType = _getRepositoryType(
						serviceReference);

					if (capability instanceof RepositoryEventAware) {
						_registerRepositoryEventListener(
							(RepositoryEventAware)capability,
							capabilityRegistration, repositoryType);
					}

					Set<Capability> capabilities = _getCapabilities(
						repositoryType);

					capabilities.add(capability);

					_capabilityRegistrations.put(
						capability, capabilityRegistration);

					_updateRepositoryWrappers();

					return capability;
				}

				@Override
				public void modifiedService(
					ServiceReference<Capability> serviceReference,
					Capability capability) {

					removedService(serviceReference, capability);

					addingService(serviceReference);
				}

				@Override
				public void removedService(
					ServiceReference<Capability> serviceReference,
					Capability capability) {

					CapabilityRegistration capabilityRegistration =
						_capabilityRegistrations.remove(capability);

					Set<Capability> capabilities = _getCapabilities(
						_getRepositoryType(serviceReference));

					capabilities.remove(capability);

					for (RepositoryEventListenerRegistration
							repositoryEventListenerRegistration :
								capabilityRegistration.
									_repositoryEventListenerRegistrations) {

						_repositoryEventRegistry.
							unregisterRepositoryEventListener(
								repositoryEventListenerRegistration.
									getRepositoryEventTypeClass(),
								repositoryEventListenerRegistration.
									getModelClass(),
								repositoryEventListenerRegistration.
									getRepositoryEventListener());
					}

					_updateRepositoryWrappers();
				}

			});
	}

	public void clear() {
		_capabilities.close();
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		_repositoryEventRegistry = repositoryEventRegistry;
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		LocalRepositoryWrapper localRepositoryWrapper = _wrapLocalRepository(
			localRepository);

		_localRepositoriesMap.put(localRepositoryWrapper, localRepository);

		return localRepositoryWrapper;
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		RepositoryWrapper repositoryWrapper = _wrapRepository(repository);

		_repositoriesMap.put(repositoryWrapper, repository);

		return repositoryWrapper;
	}

	public static class LiferayDynamicCapabilityLocalRepositoryWrapper
		extends LocalRepositoryWrapper {

		public LiferayDynamicCapabilityLocalRepositoryWrapper(
			LocalRepository localRepository) {

			super(localRepository);
		}

	}

	public static class LiferayDynamicCapabilityRepositoryWrapper
		extends RepositoryWrapper {

		public LiferayDynamicCapabilityRepositoryWrapper(
			Repository repository) {

			super(repository);
		}

	}

	private Set<Capability> _getCapabilities(
		DocumentRepository documentRepository) {

		Set<Capability> capabilities = new HashSet<>();

		capabilities.addAll(
			_getCapabilities(documentRepository.getRepositoryType()));

		if (documentRepository.getRepositoryType() != null) {
			capabilities.addAll(_getCapabilities((String)null));
		}

		return capabilities;
	}

	private Set<Capability> _getCapabilities(String repositoryType) {
		return _capabilitiesMap.computeIfAbsent(
			repositoryType != null ? repositoryType : "*",
			key -> new HashSet<>());
	}

	private String _getRepositoryType(
		ServiceReference<Capability> serviceReference) {

		return (String)serviceReference.getProperty("repository.type");
	}

	private void _registerRepositoryEventListener(
		RepositoryEventAware repositoryEventAware,
		CapabilityRegistration capabilityRegistration, String repositoryType) {

		if ((repositoryType == null) ||
			repositoryType.equals(_documentRepository.getRepositoryType())) {

			repositoryEventAware.registerRepositoryEventListeners(
				new DynamicCapabilityRepositoryEventRegistryWrapper(
					capabilityRegistration));
		}
	}

	private void _updateRepositoryWrappers() {
		for (Map.Entry<LocalRepositoryWrapper, LocalRepository>
				localRepositoryEntry : _localRepositoriesMap.entrySet()) {

			LocalRepository originalLocalRepository =
				localRepositoryEntry.getValue();

			LocalRepositoryWrapper localRepositoryWrapper =
				localRepositoryEntry.getKey();

			localRepositoryWrapper.setLocalRepository(
				_wrapLocalRepository(originalLocalRepository));
		}

		for (Map.Entry<RepositoryWrapper, Repository> repositoryWrapperEntry :
				_repositoriesMap.entrySet()) {

			Repository originalRepository = repositoryWrapperEntry.getValue();
			RepositoryWrapper repositoryWrapper =
				repositoryWrapperEntry.getKey();

			repositoryWrapper.setRepository(
				_wrapRepository(originalRepository));
		}
	}

	private LocalRepositoryWrapper _wrapLocalRepository(
		LocalRepository localRepository) {

		LocalRepository wrappedLocalRepository = localRepository;

		for (Capability capability : _getCapabilities(localRepository)) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedLocalRepository =
					repositoryWrapperAware.wrapLocalRepository(
						wrappedLocalRepository);
			}
		}

		return new LiferayDynamicCapabilityLocalRepositoryWrapper(
			wrappedLocalRepository);
	}

	private RepositoryWrapper _wrapRepository(Repository repository) {
		Repository wrappedRepository = repository;

		for (Capability capability : _getCapabilities(repository)) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedRepository = repositoryWrapperAware.wrapRepository(
					wrappedRepository);
			}
		}

		return new LiferayDynamicCapabilityRepositoryWrapper(wrappedRepository);
	}

	private final ServiceTrackerList<Capability, Capability> _capabilities;
	private final Map<String, Set<Capability>> _capabilitiesMap =
		new HashMap<>();
	private final Map<Capability, CapabilityRegistration>
		_capabilityRegistrations = new HashMap<>();
	private final DocumentRepository _documentRepository;
	private final Map<LocalRepositoryWrapper, LocalRepository>
		_localRepositoriesMap = new HashMap<>();
	private final Map<RepositoryWrapper, Repository> _repositoriesMap =
		new HashMap<>();
	private RepositoryEventRegistry _repositoryEventRegistry;

	private class CapabilityRegistration {

		public <S extends RepositoryEventType, T> void
			addRepositoryEventListenerRegistration(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListeners) {

			_repositoryEventListenerRegistrations.add(
				new RepositoryEventListenerRegistration(
					repositoryEventTypeClass, modelClass,
					repositoryEventListeners));
		}

		private final List<RepositoryEventListenerRegistration>
			_repositoryEventListenerRegistrations = new ArrayList<>();

	}

	private class DynamicCapabilityRepositoryEventRegistryWrapper
		implements RepositoryEventRegistry {

		public DynamicCapabilityRepositoryEventRegistryWrapper(
			CapabilityRegistration capabilityRegistration) {

			_capabilityRegistration = capabilityRegistration;
		}

		@Override
		public <S extends RepositoryEventType, T> void
			registerRepositoryEventListener(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListeners) {

			_capabilityRegistration.addRepositoryEventListenerRegistration(
				repositoryEventTypeClass, modelClass, repositoryEventListeners);

			_repositoryEventRegistry.registerRepositoryEventListener(
				repositoryEventTypeClass, modelClass, repositoryEventListeners);
		}

		@Override
		public <S extends RepositoryEventType, T> void
			unregisterRepositoryEventListener(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListener) {

			return;
		}

		private final CapabilityRegistration _capabilityRegistration;

	}

	private class RepositoryEventListenerRegistration
		<S extends RepositoryEventType, T> {

		public RepositoryEventListenerRegistration(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

			_repositoryEventTypeClass = repositoryEventTypeClass;
			_modelClass = modelClass;
			_repositoryEventListener = repositoryEventListener;
		}

		public Class<T> getModelClass() {
			return _modelClass;
		}

		public RepositoryEventListener<S, T> getRepositoryEventListener() {
			return _repositoryEventListener;
		}

		public Class<S> getRepositoryEventTypeClass() {
			return _repositoryEventTypeClass;
		}

		private final Class<T> _modelClass;
		private final RepositoryEventListener<S, T> _repositoryEventListener;
		private final Class<S> _repositoryEventTypeClass;

	}

}