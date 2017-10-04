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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Peter Fellwock
 */
public class AtomicState {

	public AtomicState() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("test", "AtomicState");

		_serviceRegistration = registry.registerService(
			AtomicBoolean.class, _atomicBoolean, properties);
	}

	public void close() {
		_serviceRegistration.unregister();
	}

	public Boolean get() {
		return _atomicBoolean.get();
	}

	public boolean isSet() {
		if (Boolean.TRUE.equals(_atomicBoolean.get())) {
			return true;
		}

		return false;
	}

	public void reset() {
		_atomicBoolean.set(Boolean.FALSE);
	}

	private final AtomicBoolean _atomicBoolean = new AtomicBoolean();
	private final ServiceRegistration<AtomicBoolean> _serviceRegistration;

}