/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testGetLocalizedMessageErrorJSONObject() {
		_testGetLocalizedMessageErrorJSONObject();
	}

	@Test
	public void testGetLocalizedMessageIOException() {
		_testGetLocalizedMessageIOException(
			AICreatorOpenAIClientException.
				MESSAGE_KEY_AN_UNEXPECTED_ERROR_COMPLETION,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getCompletionLocalizedMessage(
					locale));
		_testGetLocalizedMessageIOException(
			AICreatorOpenAIClientException.
				MESSAGE_KEY_AN_UNEXPECTED_ERROR_VALIDATION,
			(aiCreatorOpenAIClientException, locale) ->
				aiCreatorOpenAIClientException.getLocalizedMessage(locale));
	}

	private void _testGetLocalizedMessageErrorJSONObject() {
		String message = RandomTestUtil.randomString();

		AICreatorOpenAIClientException aiCreatorOpenAIClientException =
			new AICreatorOpenAIClientException(
				RandomTestUtil.randomString(), message,
				RandomTestUtil.randomInt());

		String expected = StringBundler.concat(
			message, " <a href=\"",
			AICreatorOpenAIClientException.OPENAI_API_ERRORS_LINK,
			"\" target=\"_blank\">",
			AICreatorOpenAIClientException.MESSAGE_KEY_OPENAI_API_ERRORS,
			"</a>");

		Assert.assertEquals(
			expected,
			aiCreatorOpenAIClientException.getCompletionLocalizedMessage(
				LocaleUtil.getDefault()));
		Assert.assertEquals(
			expected,
			aiCreatorOpenAIClientException.getLocalizedMessage(
				LocaleUtil.getDefault()));

		Mockito.verify(
			_language, Mockito.times(2)
		).get(
			LocaleUtil.getDefault(),
			AICreatorOpenAIClientException.MESSAGE_KEY_OPENAI_API_ERRORS
		);
	}

	private void _testGetLocalizedMessageIOException(
		String key,
		BiFunction<AICreatorOpenAIClientException, Locale, String> biFunction) {

		AICreatorOpenAIClientException aiCreatorOpenAIClientException =
			new AICreatorOpenAIClientException(
				HttpURLConnection.HTTP_CLIENT_TIMEOUT);

		Assert.assertEquals(
			key,
			biFunction.apply(
				aiCreatorOpenAIClientException, LocaleUtil.getDefault()));

		Mockito.verify(
			_language
		).get(
			LocaleUtil.getDefault(), key
		);
	}

	private static Language _originalLanguage;

	private Language _language;

}