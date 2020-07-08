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

package com.liferay.frontend.theme.token.definition.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeBundleInspector {

	/**
	 * Check if the bundle is a theme.
	 *
	 * @see com.liferay.portal.deploy.auto.PluginAutoDeployListenerHelper#isThemePlugin()
	 */
	public static boolean isTheme(Bundle bundle) {
		if (bundle.getEntry("WEB-INF/liferay-look-and-feel.xml") != null) {
			return true;
		}

		Matcher matcher = _themePluginPattern.matcher(bundle.getSymbolicName());

		if (matcher.find() &&
			(bundle.getEntry("WEB-INF/liferay-plugin-package.properties") !=
				null)) {

			return true;
		}

		return false;
	}

	/**
	 * @throws IllegalArgumentException if the bundle is not a theme bundle
	 */
	public ThemeBundleInspector(Bundle bundle) throws IllegalArgumentException {
		_bundle = bundle;

		if (!isTheme(bundle)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Bundle ", _bundle.getSymbolicName(), " is not a theme"));
		}
	}

	public String getTokenDefinition() {
		String tokenDefinitionPath = _getTokenDefinitionPath();

		URL url = _bundle.getEntry(tokenDefinitionPath);

		if (url == null) {
			return null;
		}

		try (InputStream is = url.openStream()) {
			return StringUtil.read(is);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read: " + tokenDefinitionPath, ioException);
		}
	}

	private String _getTokenDefinitionPath() {
		Locale defaultLocale = LocaleUtil.getDefault();

		Dictionary<String, String> headers = _bundle.getHeaders(
			defaultLocale.toString());

		String tokenDefinitionPath = headers.get("Token-Definition-Path");

		if (Validator.isNull(tokenDefinitionPath)) {
			tokenDefinitionPath = "WEB-INF/token-definition.json";
		}

		if (tokenDefinitionPath.charAt(0) == '/') {
			tokenDefinitionPath = tokenDefinitionPath.substring(1);
		}

		return tokenDefinitionPath;
	}

	private static final Pattern _themePluginPattern = Pattern.compile(
		"-(T|t)heme[-0-9._]*$");

	private final Bundle _bundle;

}