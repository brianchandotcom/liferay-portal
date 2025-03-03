/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

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

		try (InputStream inputStream = urlConnection.getInputStream();
			Reader reader = _toReader(url, inputStream)) {

			ResourceBundle resourceBundle = new PropertyResourceBundle(reader);

			Map<URL, ResourceBundle> resourceBundles =
				_resourceBundlesMap.computeIfAbsent(
					classLoader, key -> new ConcurrentHashMap<>());

			resourceBundles.put(url, resourceBundle);

			return resourceBundle;
		}
	}

	private Reader _toReader(URL url, InputStream inputStream)
		throws IOException {

		if (_textReplacerBiFunction == null) {
			return new InputStreamReader(inputStream, StringPool.UTF8);
		}

		return new UnsyncStringReader(
			_textReplacerBiFunction.apply(
				"UTF8Control#" + url,
				StreamUtil.toString(inputStream, StringPool.UTF8)));
	}

	private static final Map<ClassLoader, Map<URL, ResourceBundle>>
		_resourceBundlesMap = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final BiFunction<String, String, String>
		_textReplacerBiFunction;

	static {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		Object instance = null;

		try {
			Class<?> clazz = classLoader.loadClass(
				"com.liferay.portal.tools.jakarta.ee.transformer.function." +
					"TextReplacerBiFunction");

			instance = clazz.newInstance();
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			if (!(reflectiveOperationException instanceof
					ClassNotFoundException)) {

				throw new ExceptionInInitializerError(
					reflectiveOperationException);
			}
		}

		_textReplacerBiFunction = (BiFunction<String, String, String>)instance;
	}

}