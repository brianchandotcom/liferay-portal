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

package com.liferay.ai.creator.openai.web.internal.exception;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAIClientExceptionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_originalLanguage = LanguageUtil.getLanguage();

		LanguageUtil languageUtil = new LanguageUtil();

		_language = Mockito.mock(Language.class);

		languageUtil.setLanguage(_language);
	}

	@After
	public void tearDown() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_originalLanguage);
	}

	@Test
	public void testGetLocalizedMessage() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_CLIENT_TIMEOUT),
			RandomTestUtil.randomString(),
			AICreatorOpenAIClientException.AN_UNEXPECTED_ERROR_MESSAGE_KEY);
	}

	@Test
	public void testGetLocalizedMessageInvalidAPIKey() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_UNAUTHORIZED),
			"invalid_api_key",
			AICreatorOpenAIClientException.INCORRECT_API_KEY_MESSAGE_KEY);
	}

	@Test
	public void testGetLocalizedMessageIOException() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(new IOException()),
			RandomTestUtil.randomString(),
			AICreatorOpenAIClientException.AN_UNEXPECTED_ERROR_MESSAGE_KEY);
	}

	@Test
	public void testGetLocalizedMessageUnauthorizedResponseCode() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_UNAUTHORIZED),
			RandomTestUtil.randomString(),
			AICreatorOpenAIClientException.INCORRECT_API_KEY_MESSAGE_KEY);
	}

	private void _assertGetLocalizedMessage(
		AICreatorOpenAIClientException aiCreatorOpenAIClientException,
		String code, String key) {

		Locale locale = LocaleUtil.getDefault();

		String expected = RandomTestUtil.randomString();

		Mockito.when(
			_language.get(locale, key)
		).thenReturn(
			expected
		);

		Assert.assertEquals(
			expected,
			aiCreatorOpenAIClientException.getLocalizedMessage(locale));

		aiCreatorOpenAIClientException = new AICreatorOpenAIClientException(
			code, RandomTestUtil.randomString(),
			aiCreatorOpenAIClientException.getResponseCode());

		Assert.assertEquals(
			expected,
			aiCreatorOpenAIClientException.getLocalizedMessage(locale));

		Mockito.verify(
			_language, Mockito.times(2)
		).get(
			locale, key
		);
	}

	private static Language _originalLanguage;

	private Language _language;

}