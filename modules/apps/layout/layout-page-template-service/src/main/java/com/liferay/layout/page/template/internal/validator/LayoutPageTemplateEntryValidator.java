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