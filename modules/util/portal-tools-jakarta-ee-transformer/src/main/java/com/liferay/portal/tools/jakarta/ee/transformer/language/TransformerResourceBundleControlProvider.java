/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.jakarta.ee.transformer.language;

import com.liferay.portal.tools.jakarta.ee.transformer.function.TextReplacerBiFunction;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

/**
 * @author Shuyang Zhou
 */
public class TransformerResourceBundleControlProvider
	implements ResourceBundleControlProvider {

	@Override
	public ResourceBundle.Control getControl(String baseName) {
		if (_shouldTransform(baseName)) {
			return TransformerResourceBundleControl._INSTANCE;
		}

		return null;
	}

	private boolean _shouldTransform(String baseName) {
		for (String baseNamePrefix : _BASE_NAME_PREFIXES) {
			if (baseName.startsWith(baseNamePrefix)) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _BASE_NAME_PREFIXES = {"javax.portlet.tck."};

	private static class TransformerResourceBundleControl
		extends ResourceBundle.Control {

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

			URLConnection urlConnection = url.openConnection();

			urlConnection.setUseCaches(!reload);

			try (InputStream inputStream = urlConnection.getInputStream()) {
				return new PropertyResourceBundle(_toReader(url, inputStream));
			}
		}

		private Reader _toReader(URL url, InputStream inputStream)
			throws IOException {

			return new StringReader(
				TextReplacerBiFunction.INSTANCE.apply(
					"UTF8Control#" + url,
					new String(inputStream.readAllBytes(), "UTF-8")));
		}

		private static final ResourceBundle.Control _INSTANCE =
			new TransformerResourceBundleControl();

	}

}