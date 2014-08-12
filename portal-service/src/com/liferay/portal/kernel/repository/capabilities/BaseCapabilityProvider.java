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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityProvider implements CapabilityProvider {

	public BaseCapabilityProvider(
		Map<Class<? extends Capability>, Capability> supportedCapabilities,
		Set<Class<? extends Capability>> exportedCapabilityClasses) {

		Set<Class<? extends Capability>> supportedCapabilitiesSet =
			supportedCapabilities.keySet();

		if (!supportedCapabilitiesSet.containsAll(exportedCapabilityClasses)) {
			throw new IllegalArgumentException(
				String.format(
					"Unable to export unsupported capability %s. Supported " +
						"capabilities are %s.",
					exportedCapabilityClasses,
					StringUtil.merge(
						supportedCapabilities.keySet(), StringPool.COMMA)));
		}

		_supportedCapabilities = supportedCapabilities;
		_exportedCapabilityClasses = exportedCapabilityClasses;
	}

	@Override
	public <S extends Capability> S getCapability(Class<S> capabilityClass) {
		if (_exportedCapabilityClasses.contains(capabilityClass)) {
			return getInternalCapability(capabilityClass);
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

	protected <S extends Capability> S getInternalCapability(
		Class<S> capabilityClass) {

		Capability capability = _supportedCapabilities.get(capabilityClass);

		if (capability == null) {
			throw new IllegalArgumentException(
				String.format(
					"Capability %s is not supported by provider %s",
					capabilityClass.getName(), getProviderKey()));
		}

		return (S)capability;
	}

	protected abstract String getProviderKey();

	private final Set<Class<? extends Capability>> _exportedCapabilityClasses;
	private final Map<Class<? extends Capability>, Capability> _supportedCapabilities;

}