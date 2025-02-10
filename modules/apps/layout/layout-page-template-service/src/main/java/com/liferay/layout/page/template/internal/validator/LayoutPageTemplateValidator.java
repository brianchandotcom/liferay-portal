/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.validator;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LayoutPageTemplateValidator {

	public static Character getBlacklistCharacter(String name) {
		for (char c : _BLACKLIST_CHAR) {
			if (name.indexOf(c) >= 0) {
				return c;
			}
		}

		return null;
	}

	public static boolean isBlacklistedChar(char c) {
		for (char blacklistedChar : _BLACKLIST_CHAR) {
			if (c == blacklistedChar) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValidName(String name) {
		for (char c : _BLACKLIST_CHAR) {
			if (name.indexOf(c) >= 0) {
				return false;
			}
		}

		return true;
	}

	private static final char[] _BLACKLIST_CHAR = {
		';', '/', '?', ':', '@', '=', '&', '\"', '<', '>', '#', '%', '{', '}',
		'|', '\\', '^', '~', '[', ']', '`'
	};

}