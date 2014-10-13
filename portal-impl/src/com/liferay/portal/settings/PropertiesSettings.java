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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.io.resource.Resource;
import com.liferay.portal.kernel.io.resource.loader.ResourceLoader;
import com.liferay.portal.kernel.settings.BaseSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Properties;

/**
 * @author Jorge Ferrer
 * @author Iván Zaera
 */
public class PropertiesSettings extends BaseSettings {

	public PropertiesSettings(
		Properties properties, ResourceLoader resourceLoader) {

		this(properties, resourceLoader, null);
	}

	public PropertiesSettings(
		Properties properties, ResourceLoader resourceLoader,
		Settings parentSettings) {

		super(parentSettings);

		_properties = properties;
		_resourceLoader = resourceLoader;
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
		String value = _properties.getProperty(key);

		if (!isLocationVariable("resource", value)) {
			return value;
		}

		Resource resource = _resourceLoader.getResource(
			getLocation("resource", value));

		try {
			return StringUtil.read(resource.getInputStream());
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to read " + value, ioe);
		}
	}

	private String getLocation(String protocol, String value) {
		return value.substring(protocol.length() + 3, value.length() - 1);
	}

	private boolean isLocationVariable(String protocol, String value) {
		if (value == null) {
			return false;
		}

		String prefix =
			StringPool.DOLLAR + StringPool.OPEN_CURLY_BRACE + protocol +
				StringPool.COLON;

		if (value.startsWith(prefix) &&
			value.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

			return true;
		}

		return false;
	}

	private Properties _properties;
	private ResourceLoader _resourceLoader;

}