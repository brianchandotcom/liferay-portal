/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.google.cloud.internal.translator;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.google.cloud.internal.configuration.GoogleCloudTranslatorConfiguration;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class GoogleCloudTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_googleCredentialsMockedStatic = Mockito.mockStatic(
			GoogleCredentials.class);

		_googleCredentialsMockedStatic.when(
			() -> GoogleCredentials.fromStream(Mockito.any(InputStream.class))
		).thenAnswer(
			invocation -> {
				InputStream inputStream = invocation.getArgument(0);

				_serviceAccountPrivateKeys.add(StringUtil.read(inputStream));

				return _googleCredentials;
			}
		);

		Translate translate = _mockTranslate();

		_defaultTranslateFactoryMockedConstruction = Mockito.mockConstruction(
			TranslateOptions.DefaultTranslateFactory.class,
			(defaultTranslateFactory, context) -> Mockito.when(
				defaultTranslateFactory.create(
					Mockito.any(TranslateOptions.class))
			).thenReturn(
				translate
			));

		SecretResolver secretResolver = Mockito.mock(SecretResolver.class);

		Mockito.when(
			secretResolver.resolve(_COMPANY_ID, _SERVICE_ACCOUNT_PRIVATE_KEY)
		).thenReturn(
			_RESOLVED_SERVICE_ACCOUNT_PRIVATE_KEY
		);

		ReflectionTestUtil.setFieldValue(
			_googleCloudTranslator, "_secretResolver", secretResolver);
	}

	@After
	public void tearDown() {
		_defaultTranslateFactoryMockedConstruction.close();
		_googleCredentialsMockedStatic.close();
	}

	@Test
	public void testIsEnabled() throws Exception {
		_mockGoogleCloudTranslatorConfiguration(
			false, _SERVICE_ACCOUNT_PRIVATE_KEY);

		Assert.assertFalse(_googleCloudTranslator.isEnabled(_COMPANY_ID));

		_mockGoogleCloudTranslatorConfiguration(true, "");

		Assert.assertFalse(_googleCloudTranslator.isEnabled(_COMPANY_ID));

		_mockGoogleCloudTranslatorConfiguration(
			true, _SERVICE_ACCOUNT_PRIVATE_KEY);

		Assert.assertTrue(_googleCloudTranslator.isEnabled(_COMPANY_ID));
	}

	@Test
	public void testTranslate() throws Exception {
		_mockGoogleCloudTranslatorConfiguration(
			true, _SERVICE_ACCOUNT_PRIVATE_KEY);

		TranslatorPacket translatorPacket = _googleCloudTranslator.translate(
			_getTranslatorPacket(
				LinkedHashMapBuilder.put(
					"infoField--JournalArticle_description--0", "Bye"
				).put(
					"infoField--JournalArticle_title--0", "Hello"
				).build(),
				"en_US", "es_ES"));

		Assert.assertEquals(
			Map.of(
				"infoField--JournalArticle_description--0", "en>es Bye",
				"infoField--JournalArticle_title--0", "en>es Hello"),
			translatorPacket.getFieldsMap());
		Assert.assertEquals("en_US", translatorPacket.getSourceLanguageId());
		Assert.assertEquals("es_ES", translatorPacket.getTargetLanguageId());

		Assert.assertEquals(
			List.of(_RESOLVED_SERVICE_ACCOUNT_PRIVATE_KEY),
			_serviceAccountPrivateKeys);

		List<TranslateOptions.DefaultTranslateFactory>
			defaultTranslateFactories =
				_defaultTranslateFactoryMockedConstruction.constructed();

		ArgumentCaptor<TranslateOptions> argumentCaptor =
			ArgumentCaptor.forClass(TranslateOptions.class);

		Mockito.verify(
			defaultTranslateFactories.get(0)
		).create(
			argumentCaptor.capture()
		);

		TranslateOptions translateOptions = argumentCaptor.getValue();

		Assert.assertSame(
			_googleCredentials, translateOptions.getCredentials());
	}

	@Test
	public void testTranslateDisabled() throws Exception {
		_mockGoogleCloudTranslatorConfiguration(
			false, _SERVICE_ACCOUNT_PRIVATE_KEY);

		TranslatorPacket translatorPacket = _getTranslatorPacket(
			Map.of("infoField--JournalArticle_title--0", "Hello"), "en_US",
			"es_ES");

		Assert.assertSame(
			translatorPacket,
			_googleCloudTranslator.translate(translatorPacket));

		Assert.assertEquals(
			List.of(),
			_defaultTranslateFactoryMockedConstruction.constructed());
	}

	@Test
	public void testTranslateUnsupportedLanguage() throws Exception {
		_mockGoogleCloudTranslatorConfiguration(
			true, _SERVICE_ACCOUNT_PRIVATE_KEY);

		TranslatorException translatorException = Assert.assertThrows(
			TranslatorException.class,
			() -> _googleCloudTranslator.translate(
				_getTranslatorPacket(
					Map.of("infoField--JournalArticle_title--0", "Hello"),
					"en_US", "ja_JP")));

		Assert.assertEquals(
			"Translation between the selected languages is not supported",
			translatorException.getMessage());
	}

	private Object _getOptionValue(Translate.TranslateOption translateOption) {
		return ReflectionTestUtil.invoke(
			translateOption, "getValue", new Class<?>[0]);
	}

	private TranslatorPacket _getTranslatorPacket(
		Map<String, String> fieldsMap, String sourceLanguageId,
		String targetLanguageId) {

		return new TranslatorPacket() {

			@Override
			public long getCompanyId() {
				return _COMPANY_ID;
			}

			@Override
			public Map<String, String> getFieldsMap() {
				return fieldsMap;
			}

			@Override
			public Map<String, Boolean> getHTMLMap() {
				return Map.of();
			}

			@Override
			public String getSourceLanguageId() {
				return sourceLanguageId;
			}

			@Override
			public String getTargetLanguageId() {
				return targetLanguageId;
			}

		};
	}

	private void _mockGoogleCloudTranslatorConfiguration(
			boolean enabled, String serviceAccountPrivateKey)
		throws Exception {

		GoogleCloudTranslatorConfiguration googleCloudTranslatorConfiguration =
			Mockito.mock(GoogleCloudTranslatorConfiguration.class);

		Mockito.when(
			googleCloudTranslatorConfiguration.enabled()
		).thenReturn(
			enabled
		);

		Mockito.when(
			googleCloudTranslatorConfiguration.serviceAccountPrivateKey()
		).thenReturn(
			serviceAccountPrivateKey
		);

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		Mockito.when(
			configurationProvider.getCompanyConfiguration(
				GoogleCloudTranslatorConfiguration.class, _COMPANY_ID)
		).thenReturn(
			googleCloudTranslatorConfiguration
		);

		ReflectionTestUtil.setFieldValue(
			_googleCloudTranslator, "_configurationProvider",
			configurationProvider);
	}

	private Language _mockLanguage(String code) {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.getCode()
		).thenReturn(
			code
		);

		return language;
	}

	private Translate _mockTranslate() {
		Translate translate = Mockito.mock(Translate.class);

		List<Language> languages = Arrays.asList(
			_mockLanguage("en"), _mockLanguage("es"));

		Mockito.when(
			translate.listSupportedLanguages()
		).thenReturn(
			languages
		);

		Mockito.when(
			translate.translate(
				Mockito.<List<String>>any(),
				Mockito.any(Translate.TranslateOption[].class))
		).thenAnswer(
			invocation -> {
				List<String> texts = invocation.getArgument(0);

				List<Translation> translations = new ArrayList<>();

				for (String text : texts) {
					Translation translation = Mockito.mock(Translation.class);

					Mockito.when(
						translation.getTranslatedText()
					).thenReturn(
						String.format(
							"%s>%s %s",
							_getOptionValue(invocation.getArgument(1)),
							_getOptionValue(invocation.getArgument(2)), text)
					);

					translations.add(translation);
				}

				return translations;
			}
		);

		return translate;
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _RESOLVED_SERVICE_ACCOUNT_PRIVATE_KEY =
		"{\"type\": \"service_account\"}";

	private static final String _SERVICE_ACCOUNT_PRIVATE_KEY =
		RandomTestUtil.randomString();

	private MockedConstruction<TranslateOptions.DefaultTranslateFactory>
		_defaultTranslateFactoryMockedConstruction;
	private final GoogleCloudTranslator _googleCloudTranslator =
		new GoogleCloudTranslator();
	private final GoogleCredentials _googleCredentials = Mockito.mock(
		GoogleCredentials.class);
	private MockedStatic<GoogleCredentials> _googleCredentialsMockedStatic;
	private final List<String> _serviceAccountPrivateKeys = new ArrayList<>();

}