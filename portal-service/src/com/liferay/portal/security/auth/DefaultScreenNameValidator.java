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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultScreenNameValidator implements ScreenNameValidator {

	public static final String CYRUS = "cyrus";

	public static final String POSTFIX = "postfix";

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.format(
			locale,
			"the-screen-name-cannot-be-an-email-address-or-a-reserved-word",
			new String[] {CYRUS + ", " + POSTFIX, getSpecialChars()}, false);
	}

	@Override
	public String getJSValidation() {
		return "function(val) {" +
					"var pattern = new RegExp('[^A-Za-z0-9" +
						getSpecialChars() + "]');" +
					"if (val.match(pattern)) {" +
						"return false;" +
					"}" +
					"return true;" +
				"}";
	}

	@Override
	public boolean validate(long companyId, String screenName) {
		if (Validator.isEmailAddress(screenName) ||
			StringUtil.equalsIgnoreCase(screenName, CYRUS) ||
			StringUtil.equalsIgnoreCase(screenName, POSTFIX) ||
			hasInvalidChars(screenName)) {

			return false;
		}

		return true;
	}

	private String getSpecialChars() {
		if (_specialChars == null) {
			String specialChars = PropsUtil.get(
				PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

			_specialChars = specialChars.replaceAll(
				StringPool.SLASH, StringPool.BLANK);
		}

		return _specialChars;
	}

	private boolean hasInvalidChars(String screenName) {
		String validChars = "[A-Za-z0-9" + getSpecialChars() + "]+";

		return !screenName.matches(validChars);
	}

	private String _specialChars;

}