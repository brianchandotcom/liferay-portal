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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class UTF8Control extends ResourceBundle.Control {

	public static final UTF8Control INSTANCE = new UTF8Control();

	@Override
	public Locale getFallbackLocale(String baseName, Locale locale) {
		return null;
	}

	@Override
	public ResourceBundle newBundle(
			String baseName, Locale locale, String format,
			ClassLoader classLoader, boolean reload)
		throws IOException {

		URL url = classLoader.getResource(
			toResourceName(toBundleName(baseName, locale), "properties"));

		if (url == null) {
			return null;
		}

		if (!reload) {
			Map<URL, ResourceBundle> resourceBundles = _resourceBundlesMap.get(
				classLoader);

			if (resourceBundles != null) {
				ResourceBundle resourceBundle = resourceBundles.get(url);

				if (resourceBundle != null) {
					return resourceBundle;
				}
			}
		}

		URLConnection urlConnection = url.openConnection();

		urlConnection.setUseCaches(!reload);

		try (InputStream inputStream = urlConnection.getInputStream()) {
			ResourceBundle resourceBundle = new PropertyResourceBundle(
				new InputStreamReader(inputStream, StringPool.UTF8));

			Map<URL, ResourceBundle> resourceBundles =
				_resourceBundlesMap.computeIfAbsent(
					classLoader, key -> new ConcurrentHashMap<>());

			resourceBundles.put(url, resourceBundle);

			return resourceBundle;
		}
	}

	private static final Map<ClassLoader, Map<URL, ResourceBundle>>
		_resourceBundlesMap = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}