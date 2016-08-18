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