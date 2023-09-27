/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.validator;

import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LayoutPageTemplateEntryValidator {

	public static boolean isBlacklistedChar(char c) {
		for (char blacklistedChar : _BLACKLIST_CHAR) {
			if (c == blacklistedChar) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValidName(String layoutPageTemplateEntryName) {
		for (char c : _BLACKLIST_CHAR) {
			if (layoutPageTemplateEntryName.indexOf(c) >= 0) {
				return false;
			}
		}

		return true;
	}

	public static void validateNameCharacters(
			String layoutPageTemplateEntryName)
		throws LayoutPageTemplateEntryNameException {

		for (char c : _BLACKLIST_CHAR) {
			if (layoutPageTemplateEntryName.indexOf(c) >= 0) {
				throw new LayoutPageTemplateEntryNameException.
					MustNotContainInvalidCharacters(c);
			}
		}
	}

	private static final char[] _BLACKLIST_CHAR = {
		';', '/', '?', ':', '@', '=', '&', '\"', '<', '>', '#', '%', '{', '}',
		'|', '\\', '^', '~', '[', ']', '`'
	};

}