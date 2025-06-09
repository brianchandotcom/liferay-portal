/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Luis Ortiz
 */
public class JakartaUpgradeProcessUtil {

	public static String replace(String value, char... customSeparators) {
		for (String subpackageName : _subpackageNames) {
			String jakartaPackage = "jakarta." + subpackageName;
			String javaxPackage = "javax." + subpackageName;

			value = StringUtil.replace(value, javaxPackage, jakartaPackage);

			for (char separator : _SEPARATORS) {
				value = StringUtil.replace(
					value, StringUtil.replace(javaxPackage, '.', separator),
					StringUtil.replace(jakartaPackage, '.', separator));
			}

			for (Character separator : customSeparators) {
				value = StringUtil.replace(
					value, StringUtil.replace(javaxPackage, '.', separator),
					StringUtil.replace(jakartaPackage, '.', separator));
			}
		}

		for (String preservedSubpackageName : _preservedSubpackageNames) {
			String preservedJakartaPackage =
				"jakarta." + preservedSubpackageName;
			String preservedJavaxPackage = "javax." + preservedSubpackageName;

			value = StringUtil.replace(
				value, preservedJakartaPackage, preservedJavaxPackage);

			for (char separator : _SEPARATORS) {
				value = StringUtil.replace(
					value,
					StringUtil.replace(preservedJakartaPackage, '.', separator),
					StringUtil.replace(preservedJavaxPackage, '.', separator));
			}

			for (Character separator : customSeparators) {
				value = StringUtil.replace(
					value,
					StringUtil.replace(preservedJakartaPackage, '.', separator),
					StringUtil.replace(preservedJavaxPackage, '.', separator));
			}
		}

		return StringUtil.replace(
			value, "X-JAVAX-PORTLET-NAMESPACED-RESPONSE",
			"X-JAKARTA-PORTLET-NAMESPACED-RESPONSE");
	}

	private static final char[] _SEPARATORS = {'-', '/'};

	private static final Set<String> _preservedSubpackageNames = new HashSet<>(
		Arrays.asList("annotation.processing", "transaction.xa"));
	private static final Set<String> _subpackageNames = new HashSet<>(
		Arrays.asList(
			"activation", "annotation", "batch", "decorator", "ejb", "el",
			"enterprise", "faces", "inject", "interceptor", "jms", "json",
			"jws", "mail", "mvc", "persistence", "portlet", "resource",
			"security.auth.message", "security.enterprise", "security.jacc",
			"servlet", "transaction", "validation", "websocket", "ws.rs",
			"xml.bind", "xml.soap", "xml.ws"));

}