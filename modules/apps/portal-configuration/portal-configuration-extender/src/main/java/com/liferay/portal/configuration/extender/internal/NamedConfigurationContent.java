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

package com.liferay.portal.configuration.extender.internal;

import com.liferay.petra.function.UnsafeSupplier;

import java.io.IOException;

import java.util.Dictionary;

/**
 * @author Carlos Sierra Andrés
 */
public class NamedConfigurationContent {

	public NamedConfigurationContent(
		String name,
		UnsafeSupplier<Dictionary<?, ?>, IOException> propertySupplier) {

		_name = name;
		_propertySupplier = propertySupplier;
	}

	public ConfigurationDescription getConfigurationDescription() {
		String name = getName();

		String factoryPid = null;
		String pid = null;

		int index = name.lastIndexOf('-');

		if (index > 0) {
			factoryPid = name.substring(0, index);
			pid = name.substring(index + 1);
		}
		else {
			pid = name;
		}

		return new ConfigurationDescription(factoryPid, pid);
	}

	public String getName() {
		return _name;
	}

	@SuppressWarnings("unchecked")
	public Dictionary<String, Object> getProperties() throws IOException {
		Dictionary<?, ?> properties = _propertySupplier.get();

		return (Dictionary<String, Object>)properties;
	}

	private final String _name;
	private final UnsafeSupplier<Dictionary<?, ?>, IOException>
		_propertySupplier;

}