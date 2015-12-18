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

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderContext {

	public DDMDataProviderContext(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
	}

	public Map<String, String> getParameters() {
		return _parameters;
	}

	public <T> T getSettingsInstance(Class<T> clazz) {
		return DDMFormInstanceFactory.create(clazz, _ddmFormValues);
	}

	public void setParameter(String key, String value) {
		_parameters.put(key, value);
	}

	private final DDMFormValues _ddmFormValues;
	private final Map<String, String> _parameters = new HashMap<>();

}