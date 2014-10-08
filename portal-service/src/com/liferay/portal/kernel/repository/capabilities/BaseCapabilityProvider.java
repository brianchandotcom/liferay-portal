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

package com.liferay.portal.kernel.repository.capabilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityProvider implements CapabilityProvider {

	@Override
	public <S extends Capability> S getCapability(Class<S> capabilityClass) {
		if (_exportedCapabilityClasses.contains(capabilityClass)) {
			Capability capability = getInternalCapability(capabilityClass);

			if (capability == null) {
				throw new IllegalArgumentException(
					String.format(
						"Capability %s is not supported by provider %s",
						capabilityClass.getName(), getProviderKey()));
			}

			return (S)capability;
		}

		throw new IllegalArgumentException(
			String.format(
				"Capability %s is not exported by provider %s",
				capabilityClass.getName(), getProviderKey()));
	}

	@Override
	public <S extends Capability> boolean isCapabilityProvided(
		Class<S> capabilityClass) {

		return _exportedCapabilityClasses.contains(capabilityClass);
	}

	protected <S extends Capability, T extends S> void addExportedCapability(
		Class<S> capabilityClass, T capability) {

		addSupportedCapability(capabilityClass, capability);

		_exportedCapabilityClasses.add(capabilityClass);
	}

	protected <S extends Capability, T extends S> void addSupportedCapability(
		Class<S> capabilityClass, T capability) {

		if (_supportedCapabilities.containsKey(capabilityClass)) {
			throw new IllegalStateException(
				"Capability " + capabilityClass.getName() + " already exists");
		}

		_supportedCapabilities.put(capabilityClass, capability);
	}

	protected <S extends Capability> S getInternalCapability(
		Class<S> capabilityClass) {

		return (S)_supportedCapabilities.get(capabilityClass);
	}

	protected abstract String getProviderKey();

	private final Set<Class<? extends Capability>> _exportedCapabilityClasses =
		new HashSet<>();
	private final Map<Class<? extends Capability>, Capability>
		_supportedCapabilities = new HashMap<>();

}