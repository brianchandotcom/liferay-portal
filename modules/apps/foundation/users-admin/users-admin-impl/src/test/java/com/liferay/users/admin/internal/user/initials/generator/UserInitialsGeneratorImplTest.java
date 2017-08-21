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

package com.liferay.users.admin.internal.user.initials.generator;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.users.admin.kernel.util.UserInitialsGenerator;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Drew Brokke
 */
public class UserInitialsGeneratorImplTest {

	@Test
	public void testFirstLast() throws Exception {
		_setUpLanguageUtil("first-name,last-name");

		Assert.assertEquals(
			"FL",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, _lastName));
		Assert.assertEquals(
			"F",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, null));
		Assert.assertEquals(
			"L",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, _lastName));
		Assert.assertEquals(
			StringPool.BLANK,
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, null));
	}

	@Test
	public void testFirstMiddleLast() throws Exception {
		_setUpLanguageUtil("first-name,middle-name,last-name");

		Assert.assertEquals(
			"FM",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, _lastName));
		Assert.assertEquals(
			"FM",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, null));
		Assert.assertEquals(
			"ML",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, _lastName));
		Assert.assertEquals(
			"FL",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, null, _lastName));
		Assert.assertEquals(
			"F",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, null, null));
		Assert.assertEquals(
			"M",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, null));
		Assert.assertEquals(
			"L",
			_userInitialsGenerator.getInitials(_locale, null, null, _lastName));
	}

	@Test
	public void testLastFirst() throws Exception {
		_setUpLanguageUtil("last-name,first-name");

		Assert.assertEquals(
			"LF",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, _lastName));
		Assert.assertEquals(
			"F",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, null));
		Assert.assertEquals(
			"L",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, _lastName));
		Assert.assertEquals(
			StringPool.BLANK,
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, null));
	}

	@Test
	public void testLastFirstMiddle() throws Exception {
		_setUpLanguageUtil("last-name,first-name,middle-name");

		Assert.assertEquals(
			"LF",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, _lastName));
		Assert.assertEquals(
			"FM",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, null));
		Assert.assertEquals(
			"LM",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, _lastName));
		Assert.assertEquals(
			"LF",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, null, _lastName));
		Assert.assertEquals(
			"F",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, null, null));
		Assert.assertEquals(
			"M",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, null));
		Assert.assertEquals(
			"L",
			_userInitialsGenerator.getInitials(_locale, null, null, _lastName));
	}

	@Test
	public void testNoPropertiesReturnedUsesDefaultValues() throws Exception {
		_setUpLanguageUtil(StringPool.BLANK);

		Assert.assertEquals(
			"FL",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, _lastName));
		Assert.assertEquals(
			"F",
			_userInitialsGenerator.getInitials(
				_locale, _firstName, _middleName, null));
		Assert.assertEquals(
			"L",
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, _lastName));
		Assert.assertEquals(
			StringPool.BLANK,
			_userInitialsGenerator.getInitials(
				_locale, null, _middleName, null));
	}

	private void _setUpLanguageUtil(String returnValue) throws Exception {
		Language language = Mockito.mock(Language.class);

		Mockito.doReturn(
			returnValue
		).when(
			language
		).get(
			Matchers.any(Locale.class), Matchers.anyString(),
			Matchers.anyString()
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	private static final String _firstName = "First";
	private static final String _lastName = "Last";
	private static final Locale _locale = LocaleUtil.ENGLISH;
	private static final String _middleName = "Middle";
	private static final UserInitialsGenerator _userInitialsGenerator =
		new UserInitialsGeneratorImpl();

}