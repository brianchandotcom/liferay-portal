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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.HttpURLConnection;

import java.util.Locale;
import java.util.function.BiFunction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

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

		Mockito.when(
			_language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> invocationOnMock.getArgument(
				1, String.class)
		);

		languageUtil.setLanguage(_language);
	}

	@After
	public void tearDown() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_originalLanguage);
	}

	@Test
	public void testGetCompletionLocalizedMessage() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_CLIENT_TIMEOUT),
			AICreatorOpenAIClientException.
				MESSAGE_KEY_AN_UNEXPECTED_ERROR_COMPLETION,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getCompletionLocalizedMessage(
					locale));
	}

	@Test
	public void testGetCompletionLocalizedMessageAPIErrorJSONObject() {
		String message = RandomTestUtil.randomString();

		_assertGetLocalizedMessageOpenAIAPIError(
			new AICreatorOpenAIClientException(
				RandomTestUtil.randomString(), message,
				RandomTestUtil.randomInt()),
			message,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getCompletionLocalizedMessage(
					locale));
	}

	@Test
	public void testGetLocalizedMessage() {
		_assertGetLocalizedMessage(
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_CLIENT_TIMEOUT),
			AICreatorOpenAIClientException.
				MESSAGE_KEY_AN_UNEXPECTED_ERROR_VALIDATION,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getLocalizedMessage(locale));
	}

	@Test
	public void testGetLocalizedMessageMessageAPIErrorJSONObject() {
		String message = RandomTestUtil.randomString();

		_assertGetLocalizedMessageOpenAIAPIError(
			new AICreatorOpenAIClientException(
				RandomTestUtil.randomString(), message,
				RandomTestUtil.randomInt()),
			message,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getLocalizedMessage(locale));
	}

	private void _assertGetLocalizedMessage(
		AICreatorOpenAIClientException aiCreatorOpenAIClientException,
		String key,
		BiFunction<AICreatorOpenAIClientException, Locale, String> function) {

		Locale locale = LocaleUtil.getDefault();

		Assert.assertEquals(
			key, function.apply(aiCreatorOpenAIClientException, locale));

		Mockito.verify(
			_language
		).get(
			locale, key
		);
	}

	private void _assertGetLocalizedMessageOpenAIAPIError(
		AICreatorOpenAIClientException aiCreatorOpenAIClientException,
		String message,
		BiFunction<AICreatorOpenAIClientException, Locale, String> function) {

		Locale locale = LocaleUtil.getDefault();

		Assert.assertEquals(
			StringBundler.concat(
				message, " <a href=\"",
				AICreatorOpenAIClientException.OPENAI_API_ERRORS_LINK, "\">",
				AICreatorOpenAIClientException.MESSAGE_KEY_OPENAI_API_ERRORS,
				"</a>"),
			function.apply(aiCreatorOpenAIClientException, locale));

		Mockito.verify(
			_language
		).get(
			locale, AICreatorOpenAIClientException.MESSAGE_KEY_OPENAI_API_ERRORS
		);
	}

	private static Language _originalLanguage;

	private Language _language;

}