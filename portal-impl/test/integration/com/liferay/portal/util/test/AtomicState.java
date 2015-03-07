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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Peter Fellwock
 */
@SuppressWarnings("rawtypes")
public class AtomicState {

	public AtomicState() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("test", "AtomicState");

		_serviceRegistration = registry.registerService(
			AtomicReference.class, _atomicReference, properties);
	}

	public void close() {
		_serviceRegistration.unregister();
	}

	public boolean equalsTo(String value) {
		return Validator.equals(_atomicReference.get(), value);
	}

	public String get() {
		return _atomicReference.get();
	}

	public boolean isSet() {
		String reference = _atomicReference.get();

		if (reference == null) {
			return false;
		}

		return true;
	}

	public void reset() {
		_atomicReference.set(null);
	}

	private final AtomicReference<String> _atomicReference =
		new AtomicReference<>();
	private final ServiceRegistration<AtomicReference> _serviceRegistration;

}