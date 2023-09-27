/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil extends com.liferay.gradle.util.StringUtil {

	public static int indexOfDigit(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				return i;
			}
		}

		return -1;
	}

	public static List<String> replaceEnding(
		Collection<String> strings, String oldEnding, String newEnding) {

		List<String> newStrings = new ArrayList<>(strings.size());

		for (String s : strings) {
			if (s.endsWith(oldEnding)) {
				s = s.substring(0, s.length() - oldEnding.length()) + newEnding;
			}

			newStrings.add(s);
		}

		return newStrings;
	}

}