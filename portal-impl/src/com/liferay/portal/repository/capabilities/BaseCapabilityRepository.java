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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;

import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityRepository<T>
	implements CapabilityProvider {

	public BaseCapabilityRepository(
		T repository,
		Map<Class<? extends Capability>, Capability> capabilityMap,
		Set<Class<? extends Capability>> exportedCapabilities) {

		Set<Class<? extends Capability>> supportedCapabilities =
			capabilityMap.keySet();

		if (!supportedCapabilities.containsAll(exportedCapabilities)) {
			throw new IllegalArgumentException(
				String.format(
					"Exporting capabilities not explicitly supported is not " +
					"allowed. Repository supports %s, but tried to export %s",
					capabilityMap.keySet(), exportedCapabilities));
		}

		_repository = repository;
		_capabilityMap = capabilityMap;
		_exportedCapabilities = exportedCapabilities;
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		if (_exportedCapabilities.contains(capabilityClass)) {
			return getInternalCapability(capabilityClass);
		}

		throw new IllegalArgumentException(
			String.format(
				"Capability %s not exported by repository %d",
				capabilityClass.getName(), getRepositoryId()));
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _exportedCapabilities.contains(capabilityClass);
	}

	protected <T extends Capability> T getInternalCapability(
		Class<T> capabilityClass) {

		Capability capability = _capabilityMap.get(capabilityClass);

		if (capability == null) {
			throw new IllegalArgumentException(
				String.format(
					"Capability %s not supported by repository %d",
					capabilityClass.getName(), getRepositoryId()));
		}

		return (T)capability;
	}

	protected T getRepository() {
		return _repository;
	}

	protected abstract long getRepositoryId();

	private final Map<Class<? extends Capability>, Capability>
		_capabilityMap;
	private final Set<Class<? extends Capability>> _exportedCapabilities;
	private final T _repository;

}