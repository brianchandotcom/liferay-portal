/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Drew Brokke
 */
public class LanguageValidator {

	public static boolean isSpecialPropertyKey(String key) {
		return ArrayUtil.contains(
			LanguageConstants.KEYS_SPECIAL_PROPERTIES, key);
	}

	public static boolean isValid(String key, String value) {
		if (key.equals(LanguageConstants.KEY_DIR)) {
			return ArrayUtil.contains(LanguageConstants.VALUES_DIR, value);
		}
		else if (key.equals(LanguageConstants.KEY_LINE_BEGIN) ||
				 key.equals(LanguageConstants.KEY_LINE_END)) {

			return ArrayUtil.contains(LanguageConstants.VALUES_LINE, value);
		}
		else if (key.equals(LanguageConstants.KEY_USER_DEFAULT_PORTRAIT)) {
			return ArrayUtil.contains(
				LanguageConstants.VALUES_USER_DEFAULT_PORTRAIT, value);
		}
		else if (key.equals(LanguageConstants.KEY_USER_INITIALS_FIELD_NAMES)) {
			return _isValidUserInitialsFieldNamesValue(value);
		}
		else if (key.equals(LanguageConstants.KEY_USER_NAME_FIELD_NAMES)) {
			return _isValidUserNameFieldNamesValue(value);
		}
		else if (key.equals(LanguageConstants.KEY_USER_NAME_PREFIX_VALUES) ||
				 key.equals(LanguageConstants.KEY_USER_NAME_SUFFIX_VALUES)) {

			return Validator.isNotNull(value);
		}
		else if (key.equals(
					LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES)) {

			return _isValidUserNameRequiredFieldNamesValue(value);
		}

		return true;
	}

	private static boolean _isValidUserInitialsFieldNamesValue(String value) {
		String[] valueParts = StringUtil.split(value);

		for (String valuePart : valueParts) {
			if (!ArrayUtil.contains(
					LanguageConstants.VALUES_USER_INITIALS_FIELD_NAMES,
					valuePart)) {

				return false;
			}
		}

		return true;
	}

	private static boolean _isValidUserNameFieldNamesValue(String value) {
		String[] valueParts = StringUtil.split(value);

		if (ArrayUtil.isEmpty(valueParts) ||
			!ArrayUtil.contains(
				valueParts, LanguageConstants.VALUE_FIRST_NAME) ||
			!ArrayUtil.contains(
				valueParts, LanguageConstants.VALUE_LAST_NAME)) {

			return false;
		}

		if (ArrayUtil.contains(valueParts, LanguageConstants.VALUE_PREFIX) &&
			!valueParts[0].equals(LanguageConstants.VALUE_PREFIX)) {

			return false;
		}

		int index = valueParts.length - 1;

		if (ArrayUtil.contains(valueParts, LanguageConstants.VALUE_SUFFIX) &&
			!valueParts[index].equals(LanguageConstants.VALUE_SUFFIX)) {

			return false;
		}

		for (String valuePart : valueParts) {
			if (!ArrayUtil.contains(
					LanguageConstants.VALUES_USER_NAME_FIELD_NAMES,
					valuePart)) {

				return false;
			}
		}

		return true;
	}

	private static boolean _isValidUserNameRequiredFieldNamesValue(
		String value) {

		String[] valueParts = StringUtil.split(value);

		if (ArrayUtil.isEmpty(valueParts)) {
			return false;
		}

		for (String valuePart : valueParts) {
			if (!ArrayUtil.contains(
					LanguageConstants.VALUES_USER_NAME_FIELD_NAMES,
					valuePart)) {

				return false;
			}
		}

		return true;
	}

}