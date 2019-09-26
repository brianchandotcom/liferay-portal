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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultScreenNameValidator implements ScreenNameValidator {

	public static final String POSTFIX = "postfix";

	@Override
	public String getAUIValidatorJS() {
		return "function(val) {var pattern = new RegExp('[^A-Za-z0-9" +
			getJSEscapedSpecialChars() +
				"]');if (val.match(pattern)) {return false;}return true;}";
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.format(
			locale,
			"the-screen-name-cannot-be-an-email-address-or-a-reserved-word",
			new String[] {POSTFIX, getSpecialChars()}, false);
	}

	@Override
	public boolean validate(long companyId, String screenName) {
		if (Validator.isEmailAddress(screenName) ||
			StringUtil.equalsIgnoreCase(screenName, POSTFIX) ||
			hasInvalidChars(screenName)) {

			return false;
		}

		return true;
	}

	protected String getJSEscapedSpecialChars() {
		if (_jsEscapedSpecialChars == null) {
			_jsEscapedSpecialChars = HtmlUtil.escapeJS(getSpecialCharsRegex());
		}

		return _jsEscapedSpecialChars;
	}

	protected String getSpecialChars() {
		if (_specialChars == null) {
			String specialChars = PropsUtil.get(
				PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

			_specialChars = StringUtil.removeChar(specialChars, CharPool.SLASH);
		}

		return _specialChars;
	}

	protected String getSpecialCharsRegex() {
		if (_specialCharsRegex == null) {
			Matcher matcher = _escapeRegexPattern.matcher(getSpecialChars());

			_specialCharsRegex = matcher.replaceAll("\\\\$0");
		}

		return _specialCharsRegex;
	}

	protected boolean hasInvalidChars(String screenName) {
		return !screenName.matches(
			"[A-Za-z0-9" + getSpecialCharsRegex() + "]+");
	}

	private static final Pattern _escapeRegexPattern = Pattern.compile(
		"[-+\\\\\\[\\]]");

	private String _jsEscapedSpecialChars;
	private String _specialChars;
	private String _specialCharsRegex;

}