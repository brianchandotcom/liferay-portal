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

package com.liferay.portal.util.test;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Peter Fellwock
 */
public class AtomicStateUtil {

	public AtomicStateUtil() {
		_state = new AtomicReference<>(null);

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("test", "AtomicState");

		_serviceRegistration = registry.registerService(
			AtomicReference.class, _state, properties);
	}

	public void close() {
		_serviceRegistration.unregister();
	}

	public boolean equalsState(String value) {
		return _state.get().equals(value);
	}

	public String getState() {
		return _state.get();
	}

	public boolean isStateSet() {
		String reference = _state.get();

		if (reference == null) {
			return false;
		}

		return true;
	}

	public void resetState() {
		_state.set(null);
	}

	private final ServiceRegistration<AtomicReference> _serviceRegistration;
	private final AtomicReference<String> _state;

}