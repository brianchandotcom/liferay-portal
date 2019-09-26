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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.security.auth.DefaultScreenNameValidator;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.HtmlImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class DefaultScreenNameValidatorTest extends DefaultScreenNameValidator {

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testGetJSEscapedSpecialChars() {
		PropsTestUtil.setProps(
			PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS, "-._'");

		String expected = HtmlUtil.escapeJS(_escapeRegexCharacters("-._'"));

		Assert.assertEquals(expected, getJSEscapedSpecialChars());
	}

	@Test
	public void testValidateSpecialCharacters() {
		for (String regexReservedCharacter : _REGEX_RESERVED_CHARACTERS) {
			PropsTestUtil.setProps(
				PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS, "");

			Assert.assertFalse(validate(0, regexReservedCharacter));

			PropsTestUtil.setProps(
				PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS,
				regexReservedCharacter);

			Assert.assertTrue(validate(0, regexReservedCharacter));
		}
	}

	@Override
	protected String getSpecialChars() {
		String specialChars = PropsUtil.get(
			PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

		return StringUtil.removeChar(specialChars, CharPool.SLASH);
	}

	@Override
	protected String getSpecialCharsRegex() {
		return _escapeRegexCharacters(getSpecialChars());
	}

	private String _escapeRegexCharacters(String characters) {
		Matcher matcher = _escapeRegexPattern.matcher(characters);

		return matcher.replaceAll("\\\\$0");
	}

	private static final String[] _REGEX_RESERVED_CHARACTERS = {
		".", "^", "$", "*", "+", "-", "?", "(", ")", "[", "]", "{", "}", "\\",
		"|"
	};

	private static final Pattern _escapeRegexPattern = Pattern.compile(
		"[-+\\\\\\[\\]]");

}