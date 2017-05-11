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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 * @author Iván Zaera
 */
public class PropertiesSettings extends BaseSettings {

	public PropertiesSettings(
		LocationVariableResolver locationVariableResolver,
		Properties properties) {

		this(locationVariableResolver, properties, null);
	}

	@SuppressWarnings("unchecked")
	public PropertiesSettings(
		LocationVariableResolver locationVariableResolver,
		Properties properties, Settings parentSettings) {

		super(parentSettings);

		_locationVariableResolver = locationVariableResolver;
		_properties = new HashMap<>((Map)properties);
	}

	@Override
	protected String doGetValue(String key) {
		return readProperty(key);
	}

	@Override
	protected String[] doGetValues(String key) {
		return StringUtil.split(doGetValue(key));
	}

	protected String getProperty(String key) {
		return readProperty(key);
	}

	protected String readProperty(String key) {
		String value = _properties.get(key);

		if (_locationVariableResolver.isLocationVariable(value)) {
			return _locationVariableResolver.resolve(value);
		}

		return value;
	}

	private final LocationVariableResolver _locationVariableResolver;
	private final Map<String, String> _properties;

}