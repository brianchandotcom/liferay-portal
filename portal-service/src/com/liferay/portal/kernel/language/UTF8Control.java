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

package com.liferay.portal.kernel.language;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * @author Raymond Augé
 */
public class UTF8Control extends Control {

	public static final UTF8Control INSTANCE = new UTF8Control();

	@Override
	public ResourceBundle newBundle(
			String baseName, Locale locale, String format,
			ClassLoader classLoader, boolean reload)
		throws IllegalAccessException, InstantiationException, IOException {

		String resourceName = toResourceName(
			toBundleName(baseName, locale), "properties");

		InputStream inputStream = null;

		if (reload) {
			URL url = classLoader.getResource(resourceName);

			if (url != null) {
				URLConnection connection = url.openConnection();

				if (connection != null) {
					connection.setUseCaches(false);

					inputStream = connection.getInputStream();
				}
			}
		}
		else {
			inputStream = classLoader.getResourceAsStream(resourceName);
		}

		if (inputStream == null) {
			return null;
		}

		try (InputStream in = inputStream) {
			return new PropertyResourceBundle(new InputStreamReader(in, _UTF8));
		}
	}

	private static final String _UTF8 = "UTF-8";

}