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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinition
	implements RepositoryEventRegistry, RepositoryEventTrigger,
		RepositoryFactoryRegistry {

	public RepositoryClassDefinition(RepositoryDefiner repositoryDefiner) {
		_repositoryDefiner = repositoryDefiner;
	}

	public CapabilityLocalRepository createCapabilityLocalRepository(
			long repositoryId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryFactory.createLocalRepository(repositoryId);

		CapabilityLocalRepository capabilityLocalRepository =
			new CapabilityLocalRepository(localRepository, this);

		_repositoryDefiner.registerCapabilities(capabilityLocalRepository);

		return capabilityLocalRepository;
	}

	public CapabilityRepository createCapabilityRepository(long repositoryId)
		throws PortalException {

		Repository repository = _repositoryFactory.createRepository(
			repositoryId);

		CapabilityRepository capabilityRepository = new CapabilityRepository(
			repository, this);

		_repositoryDefiner.registerCapabilities(capabilityRepository);

		return capabilityRepository;
	}

	public RepositoryEventTrigger getRepositoryEventTrigger() {
		return this;
	}

	public RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	@Override
	public <S extends RepositoryEventType, T>
		void registerRepositoryEventListener(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

		if (repositoryEventListener == null) {
			throw new NullPointerException(
				"it's not legal to register a null repository event listener");
		}

		Tuple key = new Tuple(repositoryEventTypeClass, modelClass);

		Collection<RepositoryEventListener<?, ?>> repositoryEventListeners =
			_repositoryEventListeners.get(key);

		if (repositoryEventListeners == null) {
			repositoryEventListeners = new ArrayList<>();

			_repositoryEventListeners.put(key, repositoryEventListeners);
		}

		repositoryEventListeners.add(repositoryEventListener);
	}

	@Override
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		if (_repositoryFactory != null) {
			throw new IllegalStateException(
				"Repository factory already exists");
		}

		_repositoryFactory = repositoryFactory;
	}

	@Override
	public <S extends RepositoryEventType, T> void trigger(
			Class<S> repositoryEventTypeClass, Class<T> modelClass, T payload)
		throws PortalException {

		Tuple key = new Tuple(repositoryEventTypeClass, modelClass);

		@SuppressWarnings("rawtypes")
		Collection<RepositoryEventListener<S, T>> repositoryEventListeners =
			(Collection)_repositoryEventListeners.get(key);

		if (repositoryEventListeners != null) {
			for (RepositoryEventListener<S, T> repositoryEventListener :
					repositoryEventListeners) {

				repositoryEventListener.execute(payload);
			}
		}
	}

	private final RepositoryDefiner _repositoryDefiner;
	private final Map<Tuple, Collection<RepositoryEventListener<?, ?>>>
		_repositoryEventListeners =
			new HashMap<Tuple, Collection<RepositoryEventListener<?, ?>>>();
	private RepositoryFactory _repositoryFactory;

}