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

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class NamedConfigurationContent {

	public NamedConfigurationContent(String name, InputStream inputStream) {
		_name = name;
		_inputStream = inputStream;
	}

	public ConfigurationDescription getConfigurationDescription() {
		String pid = null;

		String name = getName();

		int index = name.lastIndexOf('-');

		if (index > 0) {
			String factoryPid = name.substring(0, index);
			pid = name.substring(index + 1);

			return new ConfigurationDescription(factoryPid, pid);
		}

		pid = name;

		return new ConfigurationDescription(null, pid);
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	public String getName() {
		return _name;
	}

	public abstract Dictionary<String, Object> getProperties()
		throws IOException;

	private final InputStream _inputStream;
	private final String _name;

}