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

package com.liferay.frontend.taglib.chart.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 */
public abstract class ChartObject extends AbstractMap<String, Object> {

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return _properties.entrySet();
	}

	protected <T> T get(String name, Class<T> clazz) {
		return get(name, clazz, true);
	}

	protected <T> T get(String name, Class<T> clazz, boolean createIfNotFound) {
		T value = (T)_properties.get(name);

		if ((value == null) && createIfNotFound) {
			try {
				value = clazz.newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}

			_properties.put(name, value);
		}

		return value;
	}

	protected void set(String name, Object value) {
		if (value == null) {
			throw new UnsupportedOperationException(
				"Property " + name + " has a null value");
		}

		_properties.put(name, value);
	}

	private final Map<String, Object> _properties = new HashMap<>();

}