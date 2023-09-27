/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	protected <S extends Capability> void addExportedCapability(
		Class<S> capabilityClass, S capability) {

		addSupportedCapability(capabilityClass, capability);

		_exportedCapabilityClasses.add(capabilityClass);
	}

	protected <S extends Capability> void addSupportedCapability(
		Class<S> capabilityClass, S capability) {

		if (_supportedCapabilities.containsKey(capabilityClass)) {
			throw new IllegalStateException(
				"Capability " + capabilityClass.getName() + " already exists");
		}

		_supportedCapabilities.put(capabilityClass, capability);
	}

	protected Map<Class<? extends Capability>, Capability> getCapabilities() {
		return _supportedCapabilities;
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