/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DDMFormBuilderContextResponse {

	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	public Map<String, Object> getContext() {
		return getProperty("context");
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public void setContext(Map<String, Object> context) {
		addProperty("context", context);
	}

	private final Map<String, Object> _properties = new HashMap<>();

}