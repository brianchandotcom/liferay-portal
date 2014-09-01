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
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class DefaultRepositoryDefinition
	implements CapabilityRegistry, RepositoryDefinition,
			RepositoryFactoryRegistry, RepositoryEventTrigger,
			RepositoryEventRegistry {

	public DefaultRepositoryDefinition() {
		_exportedCapabilities = new HashSet<Class<? extends Capability>>();

		_supportedCapabilities =
			new HashMap<Class<? extends Capability>, Capability>();
	}

	@Override
	public <S extends Capability, T extends S> void addExportedCapability(
		Class<S> capabilityClass, T capability) {

		_exportedCapabilities.add(capabilityClass);

		addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability, T extends S> void addSupportedCapability(
		Class<S> capabilityClass, T capability) {

		_supportedCapabilities.put(capabilityClass, capability);
	}

	@Override
	public Set<Class<? extends Capability>> getExportedCapabilities() {
		return _exportedCapabilities;
	}

	@Override
	public RepositoryEventTrigger getRepositoryEventTrigger() {
		return this;
	}

	@Override
	public RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	@Override
	public Map<Class<? extends Capability>, Capability>
		getSupportedCapabilities() {

		return _supportedCapabilities;
	}

	@Override
	public <S extends RepositoryEventType, T>
		void registerRepositoryEventListener(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

		Tuple key = new Tuple(repositoryEventTypeClass, modelClass);

		Collection<RepositoryEventListener<?, ?>> repositoryEventListeners =
			_repositoryEventListeners.get(key);

		if (repositoryEventListeners == null) {
			repositoryEventListeners =
				new ArrayList<RepositoryEventListener<?, ?>>();

			_repositoryEventListeners.put(key, repositoryEventListeners);
		}

		repositoryEventListeners.add(repositoryEventListener);
	}

	@Override
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		if (_repositoryFactory != null) {
			throw new IllegalStateException(
				"Repository factory is already set");
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

		for (RepositoryEventListener<S, T> repositoryEventListener :
				repositoryEventListeners) {

			repositoryEventListener.execute(payload);
		}
	}

	private Set<Class<? extends Capability>> _exportedCapabilities;
	private Map<Tuple, Collection<RepositoryEventListener<?, ?>>>
		_repositoryEventListeners =
			new HashMap<Tuple, Collection<RepositoryEventListener<?, ?>>>();
	private RepositoryFactory _repositoryFactory;
	private Map<Class<? extends Capability>, Capability> _supportedCapabilities;

}