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

import java.net.URL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = NamedConfigurationContentFactory.class)
public class NamedConfigurationPathContentFactory
	implements NamedConfigurationContentFactory {

	@Override
	public NamedConfigurationContent create(URL url) throws IOException {
		String name = url.getFile();

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		int lastIndexOfSlash = name.lastIndexOf('/');

		if (lastIndexOfSlash > 0) {
			name = name.substring(lastIndexOfSlash + 1);
		}

		if (!name.endsWith(".properties")) {
			throw new IllegalArgumentException(
				"File name does not end with .properties");
		}

		return new PropertiesFileNamedConfigurationContent(
			name.substring(0, name.length() - 11), url.openStream());
	}

	@Override
	public String getFilePattern() {
		return "*.properties";
	}

}