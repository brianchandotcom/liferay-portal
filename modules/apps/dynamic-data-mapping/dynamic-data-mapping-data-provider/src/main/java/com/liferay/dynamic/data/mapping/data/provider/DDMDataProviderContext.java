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

package com.liferay.dynamic.data.mapping.data.provider;

import aQute.bnd.annotation.metatype.Configurable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderContext {

	public DDMDataProviderContext(Map<String, Object> properties) {
		_properties = properties;
	}

	public Map<String, String> getParameters() {
		return _parameters;
	}

	public <T> T getSettingsInstance(Class<T> clazz) {
		return Configurable.createConfigurable(clazz, _properties);
	}

	public void setParameter(String key, String value) {
		_parameters.put(key, value);
	}

	private final Map<String, String> _parameters = new HashMap<>();
	private final Map<String, Object> _properties;

}