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

	public static String replace(String value) {
		return _replace(value, _separators);
	}

	public static String replace(
		String value, Set<Character> customSeparators) {

		Set<Character> separators = new HashSet<>();

		separators.addAll(_separators);
		separators.addAll(customSeparators);

		return _replace(value, separators);
	}

	private static String _replace(String value, Set<Character> separators) {
		for (String subpackageName : _subpackageNames) {
			String javaxPackage = "javax." + subpackageName;
			String jakartaPackage = "jakarta." + subpackageName;

			value = StringUtil.replace(value, javaxPackage, jakartaPackage);

			for (Character separator : separators) {
				value = StringUtil.replace(
					value, StringUtil.replace(javaxPackage, '.', separator),
					StringUtil.replace(jakartaPackage, '.', separator));
			}
		}

		for (String fixupSubpackageName : _fixupSubpackageNames) {
			String fixupJavaxPackage = "javax." + fixupSubpackageName;
			String fixupJakartaPackage = "jakarta." + fixupSubpackageName;

			value = StringUtil.replace(
				value, fixupJakartaPackage, fixupJavaxPackage);

			for (Character separator : separators) {
				value = StringUtil.replace(
					value,
					StringUtil.replace(fixupJakartaPackage, '.', separator),
					StringUtil.replace(fixupJavaxPackage, '.', separator));
			}
		}

		return StringUtil.replace(
			value, "X-JAVAX-PORTLET-NAMESPACED-RESPONSE",
			"X-JAKARTA-PORTLET-NAMESPACED-RESPONSE");
	}

	private static final Set<String> _fixupSubpackageNames = new HashSet<>(
		Arrays.asList("annotation.processing", "transaction.xa"));
	private static final Set<Character> _separators = new HashSet<>(
		Arrays.asList('.', '-', '/'));
	private static final Set<String> _subpackageNames = new HashSet<>(
		Arrays.asList(
			"activation", "annotation", "batch", "decorator", "ejb", "el",
			"enterprise", "faces", "inject", "interceptor", "jms", "json",
			"jws", "mail", "mvc", "persistence", "portlet", "resource",
			"security.auth.message", "security.enterprise", "security.jacc",
			"servlet", "transaction", "validation", "websocket", "ws.rs",
			"xml.bind", "xml.soap", "xml.ws"));

}