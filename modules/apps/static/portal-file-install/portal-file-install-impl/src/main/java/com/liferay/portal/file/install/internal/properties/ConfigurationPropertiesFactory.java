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

package com.liferay.portal.file.install.internal.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Matthew Tambara
 */
public class ConfigurationPropertiesFactory {

	public static ConfigurationProperties create(File file, String encoding)
		throws IOException {

		ConfigurationProperties configurationProperties = null;

		String name = file.getName();

		if (name.endsWith("config")) {
			configurationProperties = new TypedProperties();
		}
		else if (name.endsWith("cfg")) {
			configurationProperties = new CFGProperties();
		}
		else {
			throw new IllegalArgumentException(
				"Unknown configuration type: " + file);
		}

		try (InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, encoding)) {

			configurationProperties.load(reader);
		}

		return configurationProperties;
	}

}