/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				throw new RuntimeException(exception);
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