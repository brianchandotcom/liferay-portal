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

package com.liferay.portal.configuration.extender.internal.support.config.file;

import com.liferay.portal.configuration.extender.internal.NamedConfigurationContent;

import java.io.InputStream;

/**
 * @author Carlos Sierra Andrés
 */
public final class ConfigFileNamedConfigurationContent
	implements NamedConfigurationContent {

	public ConfigFileNamedConfigurationContent(
		String name, InputStream inputStream) {

		_name = name;
		_inputStream = inputStream;
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	@Override
	public String getName() {
		return _name;
	}

	private final InputStream _inputStream;
	private final String _name;

}