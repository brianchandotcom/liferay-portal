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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Iván Zaera
 */
public class LocationVariableResolver {

	public LocationVariableResolver(
		ResourceManager resourceManager, SettingsFactory settingsFactory) {

		_resourceManager = resourceManager;
		_settingsFactory = settingsFactory;
	}

	public boolean isLocationVariable(String value) {
		if (value == null) {
			return false;
		}

		if (value.startsWith(_LOCATION_VARIABLE_START) &&
			value.endsWith(_LOCATION_VARIABLE_END) &&
			value.contains(_LOCATION_VARIABLE_PROTOCOL_SEPARATOR)) {

			return true;
		}

		return false;
	}

	public String resolve(String value) {
		String protocol = _getProtocol(value);
		String location = _getLocation(value);

		if (protocol.equals("resource")) {
			return _resolveResource(location);
		}
		else if (protocol.equals("file")) {
			return _resolveFile(location);
		}
		else if (protocol.equals("server-property")) {
			return _resolveServerProperty(location);
		}

		throw new UnsupportedOperationException(
			"Unsupported protocol " + protocol);
	}

	private String _getLocation(String value) {
		int i = value.indexOf(_LOCATION_VARIABLE_PROTOCOL_SEPARATOR);

		return value.substring(i + 1, value.length() - 1);
	}

	private String _getProtocol(String value) {
		int i = value.indexOf(_LOCATION_VARIABLE_PROTOCOL_SEPARATOR);

		return value.substring(2, i);
	}

	private String _resolveFile(String location) {
		if (!location.startsWith("///")) {
			throw new IllegalArgumentException(
				"Invalid file location " + location + " because only local " +
					"file URIs starting with file:/// are supported");
		}

		try {
			return StringUtil.read(new FileInputStream(location.substring(2)));
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to read file " + location, ioe);
		}
	}

	private String _resolveResource(String location) {
		ResourceRetriever resourceRetriever =
			_resourceManager.getResourceRetriever(location);

		try {
			return StringUtil.read(resourceRetriever.getInputStream());
		}
		catch (IOException ioe) {
			throw new SystemException(
				"Unable to read resource " + location, ioe);
		}
	}

	private String _resolveServerProperty(String location) {
		if (!location.startsWith("//")) {
			throw new IllegalArgumentException(
				"Invalid server property location " + location);
		}

		location = location.substring(2);

		int i = location.indexOf("/");

		if (i == -1) {
			throw new IllegalArgumentException(
				"Invalid server property location " + location);
		}

		String serviceName = location.substring(0, i);

		Settings settings = _settingsFactory.getServerSettings(serviceName);

		String property = location.substring(i + 1);

		return settings.getValue(property, null);
	}

	private static final String _LOCATION_VARIABLE_END = "}";

	private static final String _LOCATION_VARIABLE_PROTOCOL_SEPARATOR = ":";

	private static final String _LOCATION_VARIABLE_START = "${";

	private final ResourceManager _resourceManager;
	private final SettingsFactory _settingsFactory;

}