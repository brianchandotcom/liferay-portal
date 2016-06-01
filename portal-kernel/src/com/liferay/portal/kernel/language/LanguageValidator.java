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

package com.liferay.portal.kernel.language;

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
		else if (key.equals(LanguageConstants.KEY_USER_NAME_FIELD_NAMES)) {
			return _isValidUserNameFieldNamesValue(value);
		}
		else if (key.equals(LanguageConstants.KEY_USER_NAME_PREFIX_VALUES) ||
				 key.equals(LanguageConstants.KEY_USER_NAME_SUFFIX_VALUES)) {

			return !Validator.isNull(value);
		}
		else if (key.equals(
					LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES)) {

			return _isValidUserNameRequiredFieldNamesValue(value);
		}

		return true;
	}

	private static boolean _isValidUserNameFieldNamesValue(String value) {
		String[] valueArray = StringUtil.split(value);

		if (ArrayUtil.isEmpty(valueArray)) {
			return false;
		}

		if (!ArrayUtil.contains(
				valueArray, LanguageConstants.VALUE_FIRST_NAME) ||
			!ArrayUtil.contains(
				valueArray, LanguageConstants.VALUE_LAST_NAME)) {

			return false;
		}

		if (ArrayUtil.contains(valueArray, LanguageConstants.VALUE_PREFIX) &&
			!valueArray[0].equals(LanguageConstants.VALUE_PREFIX)) {

			return false;
		}

		int index = valueArray.length - 1;

		if (ArrayUtil.contains(valueArray, LanguageConstants.VALUE_SUFFIX) &&
			!valueArray[index].equals(LanguageConstants.VALUE_SUFFIX)) {

			return false;
		}

		for (String curValue : valueArray) {
			if (!ArrayUtil.contains(
					LanguageConstants.VALUES_USER_NAME_FIELD_NAMES, curValue)) {

				return false;
			}
		}

		return true;
	}

	private static boolean _isValidUserNameRequiredFieldNamesValue(
		String value) {

		String[] valuesArray = StringUtil.split(value);

		if (ArrayUtil.isEmpty(valuesArray)) {
			return false;
		}

		for (String curValue : valuesArray) {
			if (!ArrayUtil.contains(
					LanguageConstants.VALUES_USER_NAME_FIELD_NAMES, curValue)) {

				return false;
			}
		}

		return true;
	}

}