/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.aws.internal.translator;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.TranslateClientBuilder;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

/**
 * @author Jürgen Kappler
 */
public class AWSTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_translateClientBuilder = Mockito.mock(
			TranslateClientBuilder.class, Mockito.RETURNS_SELF);

		TranslateClient translateClient = _mockTranslateClient();

		Mockito.when(
			_translateClientBuilder.build()
		).thenReturn(
			translateClient
		);

		_translateClientMockedStatic = Mockito.mockStatic(
			TranslateClient.class);

		_translateClientMockedStatic.when(
			TranslateClient::builder
		).thenReturn(
			_translateClientBuilder
		);

		SecretResolver secretResolver = Mockito.mock(SecretResolver.class);

		Mockito.when(
			secretResolver.resolve(_COMPANY_ID, _SECRET_KEY)
		).thenReturn(
			_RESOLVED_SECRET_KEY
		);

		ReflectionTestUtil.setFieldValue(
			_awsTranslator, "_secretResolver", secretResolver);
	}

	@After
	public void tearDown() {
		_translateClientMockedStatic.close();
	}

	@Test
	public void testIsEnabled() throws Exception {
		_mockAWSTranslatorConfiguration(false);

		Assert.assertFalse(_awsTranslator.isEnabled(_COMPANY_ID));

		_mockAWSTranslatorConfiguration(true);

		Assert.assertTrue(_awsTranslator.isEnabled(_COMPANY_ID));
	}

	@Test
	public void testTranslate() throws Exception {
		_mockAWSTranslatorConfiguration(true);

		TranslatorPacket translatorPacket = _awsTranslator.translate(
			_getTranslatorPacket(
				Map.of(
					"infoField--JournalArticle_content--0", "",
					"infoField--JournalArticle_title--0", "Hello"),
				"en_US", "es_ES"));

		Assert.assertEquals(
			Map.of(
				"infoField--JournalArticle_content--0", "",
				"infoField--JournalArticle_title--0", "en>es Hello"),
			translatorPacket.getFieldsMap());
		Assert.assertEquals("en_US", translatorPacket.getSourceLanguageId());
		Assert.assertEquals("es_ES", translatorPacket.getTargetLanguageId());

		Assert.assertEquals(List.of("Hello"), _translatedTexts);

		ArgumentCaptor<AwsCredentialsProvider> argumentCaptor =
			ArgumentCaptor.forClass(AwsCredentialsProvider.class);

		Mockito.verify(
			_translateClientBuilder
		).credentialsProvider(
			argumentCaptor.capture()
		);

		AwsCredentialsProvider awsCredentialsProvider =
			argumentCaptor.getValue();

		AwsCredentials awsCredentials =
			awsCredentialsProvider.resolveCredentials();

		Assert.assertEquals(_ACCESS_KEY, awsCredentials.accessKeyId());
		Assert.assertEquals(
			_RESOLVED_SECRET_KEY, awsCredentials.secretAccessKey());

		Mockito.verify(
			_translateClientBuilder
		).region(
			Region.of(_REGION)
		);
	}

	@Test
	public void testTranslateDisabled() throws Exception {
		_mockAWSTranslatorConfiguration(false);

		TranslatorPacket translatorPacket = _getTranslatorPacket(
			Map.of("infoField--JournalArticle_title--0", "Hello"), "en_US",
			"es_ES");

		Assert.assertSame(
			translatorPacket, _awsTranslator.translate(translatorPacket));

		_translateClientMockedStatic.verifyNoInteractions();
	}

	@Test
	public void testTranslateIndonesian() throws Exception {
		_mockAWSTranslatorConfiguration(true);

		TranslatorPacket translatorPacket = _awsTranslator.translate(
			_getTranslatorPacket(
				Map.of("infoField--JournalArticle_title--0", "Hello"), "en_US",
				"in_ID"));

		Assert.assertEquals(
			Map.of("infoField--JournalArticle_title--0", "en>id Hello"),
			translatorPacket.getFieldsMap());
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

	private void _mockAWSTranslatorConfiguration(boolean enabled)
		throws Exception {

		AWSTranslatorConfiguration awsTranslatorConfiguration = Mockito.mock(
			AWSTranslatorConfiguration.class);

		Mockito.when(
			awsTranslatorConfiguration.accessKey()
		).thenReturn(
			_ACCESS_KEY
		);

		Mockito.when(
			awsTranslatorConfiguration.enabled()
		).thenReturn(
			enabled
		);

		Mockito.when(
			awsTranslatorConfiguration.region()
		).thenReturn(
			_REGION
		);

		Mockito.when(
			awsTranslatorConfiguration.secretKey()
		).thenReturn(
			_SECRET_KEY
		);

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		Mockito.when(
			configurationProvider.getCompanyConfiguration(
				AWSTranslatorConfiguration.class, _COMPANY_ID)
		).thenReturn(
			awsTranslatorConfiguration
		);

		ReflectionTestUtil.setFieldValue(
			_awsTranslator, "_configurationProvider", configurationProvider);
	}

	private TranslateClient _mockTranslateClient() {
		TranslateClient translateClient = Mockito.mock(TranslateClient.class);

		Mockito.when(
			translateClient.translateText(
				Mockito.<Consumer<TranslateTextRequest.Builder>>any())
		).thenAnswer(
			invocation -> {
				TranslateTextRequest.Builder builder =
					TranslateTextRequest.builder();

				Consumer<TranslateTextRequest.Builder> consumer =
					invocation.getArgument(0);

				consumer.accept(builder);

				TranslateTextRequest translateTextRequest = builder.build();

				_translatedTexts.add(translateTextRequest.text());

				return TranslateTextResponse.builder(
				).translatedText(
					String.format(
						"%s>%s %s", translateTextRequest.sourceLanguageCode(),
						translateTextRequest.targetLanguageCode(),
						translateTextRequest.text())
				).build();
			}
		);

		return translateClient;
	}

	private static final String _ACCESS_KEY = RandomTestUtil.randomString();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _REGION = "us-east-1";

	private static final String _RESOLVED_SECRET_KEY =
		RandomTestUtil.randomString();

	private static final String _SECRET_KEY = RandomTestUtil.randomString();

	private final AWSTranslator _awsTranslator = new AWSTranslator();
	private TranslateClientBuilder _translateClientBuilder;
	private MockedStatic<TranslateClient> _translateClientMockedStatic;
	private final List<String> _translatedTexts = new ArrayList<>();

}