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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderResponseEntry implements Serializable {

	public Map<String, Object> getProperties() {
		return _properties;
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public void setProperties(Map<String, Object> properties) {
		_properties.putAll(properties);
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	private final Map<String, Object> _properties = new HashMap<>();

}