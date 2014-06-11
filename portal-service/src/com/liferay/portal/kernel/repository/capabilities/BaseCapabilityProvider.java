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

import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityProvider implements CapabilityProvider {

	public BaseCapabilityProvider(
		Map<Class<? extends Capability>, Capability> supportedCapabilitiesMap,
		Set<Class<? extends Capability>> exportedCapabilityClasses) {

		Set<Class<? extends Capability>> supportedCapabilities =
			supportedCapabilitiesMap.keySet();

		if (!supportedCapabilities.containsAll(exportedCapabilityClasses)) {
			throw new IllegalArgumentException(
				String.format(
					"Exporting capabilities not explicitly supported is not " +
						"allowed. Provider supports %s, but tried to " +
						"export %s",
					supportedCapabilitiesMap.keySet(),
					exportedCapabilityClasses));
		}

		_supportedCapabilitiesMap = supportedCapabilitiesMap;
		_exportedCapabilityClasses = exportedCapabilityClasses;
	}

	@Override
	public <S extends Capability> S getCapability(Class<S> capabilityClass) {
		if (_exportedCapabilityClasses.contains(capabilityClass)) {
			return getInternalCapability(capabilityClass);
		}

		throw new IllegalArgumentException(
			String.format(
				"Capability %s not exported by provider %s",
				capabilityClass.getName(), getProviderId()));
	}

	@Override
	public <S extends Capability> boolean isCapabilityProvided(
		Class<S> capabilityClass) {

		return _exportedCapabilityClasses.contains(capabilityClass);
	}

	protected <S extends Capability> S getInternalCapability(
		Class<S> capabilityClass) {

		Capability capability = _supportedCapabilitiesMap.get(capabilityClass);

		if (capability == null) {
			throw new IllegalArgumentException(
				String.format(
					"Capability %s not supported by provider %s",
					capabilityClass.getName(), getProviderId()));
		}

		return (S)capability;
	}

	protected abstract String getProviderId();

	private final Set<Class<? extends Capability>> _exportedCapabilityClasses;
	private final Map<Class<? extends Capability>, Capability>
		_supportedCapabilitiesMap;

}