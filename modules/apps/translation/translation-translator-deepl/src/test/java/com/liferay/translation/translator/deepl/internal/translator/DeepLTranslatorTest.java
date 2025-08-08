/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.deepl.internal.translator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gergely Szalay
 */
public class DeepLTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws ConfigurationException, IOException {
		_companyId = RandomTestUtil.randomLong();

		ReflectionTestUtil.setFieldValue(
			_deepLTranslator, "_configurationProvider", _configurationProvider);

		ReflectionTestUtil.setFieldValue(_deepLTranslator, "_http", _http);

		ReflectionTestUtil.setFieldValue(
			_deepLTranslator, "_jsonFactory", _jsonFactory);

		_setUpPortalUtil();

		_setUpDeepL();
	}

	@Test
	public void testTranslationHandlesBaseLanguage() throws PortalException {
		TranslatorPacket originTranslatorPacket = _getTranslatorPocket(
			Map.of("infoField--JournalArticle_title--0", false), "en_US",
			"ca_ES");

		TranslatorPacket translatorPacket = _deepLTranslator.translate(
			originTranslatorPacket);

		Assert.assertEquals(
			Map.of("infoField--JournalArticle_title--0", "¡Hola, mundo!"),
			translatorPacket.getFieldsMap());
	}

	@Test
	public void testTranslationHandlesLanguageVariant() throws PortalException {
		TranslatorPacket originTranslatorPacket = _getTranslatorPocket(
			Map.of("infoField--JournalArticle_title--0", false), "en_US",
			"pt_BR");

		TranslatorPacket translatorPacket = _deepLTranslator.translate(
			originTranslatorPacket);

		Assert.assertEquals(
			Map.of("infoField--JournalArticle_title--0", "Olá, mundo!"),
			translatorPacket.getFieldsMap());
	}

	@Test
	public void testTranslationHandlesTraditionalChinese()
		throws PortalException {

		TranslatorPacket originTranslatorPacket = _getTranslatorPocket(
			Map.of("infoField--JournalArticle_title--0", false), "en_US",
			"zh_TW");

		TranslatorPacket translatorPacket = _deepLTranslator.translate(
			originTranslatorPacket);

		Assert.assertEquals(
			Map.of("infoField--JournalArticle_title--0", "哈囉，世界！"),
			translatorPacket.getFieldsMap());
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private String _getTranslationString(String text) {
		StringBundler sb = new StringBundler(4);

		sb.append("{\"translations\":[{\"detected_source_language\":\"EN\",");
		sb.append("\"text\":\"");
		sb.append(text);
		sb.append("\"}]}");

		return sb.toString();
	}

	private TranslatorPacket _getTranslatorPocket(
		Map<String, Boolean> htmlMap, String sourceLanguage,
		String targetLanguage) {

		return new TranslatorPacket() {

			@Override
			public long getCompanyId() {
				return _companyId;
			}

			@Override
			public Map<String, String> getFieldsMap() {
				return Map.of(
					"infoField--JournalArticle_title--0", "Hello, world!");
			}

			@Override
			public Map<String, Boolean> getHTMLMap() {
				return htmlMap;
			}

			@Override
			public String getSourceLanguageId() {
				return sourceLanguage;
			}

			@Override
			public String getTargetLanguageId() {
				return targetLanguage;
			}

		};
	}

	private void _setUpDeepL() throws ConfigurationException, IOException {
		DeepLTranslatorConfiguration deepLTranslatorConfiguration =
			Mockito.mock(DeepLTranslatorConfiguration.class);

		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				DeepLTranslatorConfiguration.class, _companyId)
		).thenReturn(
			deepLTranslatorConfiguration
		);

		Mockito.when(
			deepLTranslatorConfiguration.authKey()
		).thenReturn(
			"DEEPL_authkey"
		);

		Mockito.when(
			deepLTranslatorConfiguration.enabled()
		).thenReturn(
			true
		);

		String languagesUrlString = "https://api-free.deepl.com/v2/languages";

		Mockito.when(
			deepLTranslatorConfiguration.validateLanguageURL()
		).thenReturn(
			languagesUrlString
		);

		Mockito.when(
			deepLTranslatorConfiguration.url()
		).thenReturn(
			"https://api-free.deepl.com/v2/translate"
		);

		String[] urlArray = {languagesUrlString, ""};

		Mockito.when(
			PortalUtil.stripURLAnchor(Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			urlArray
		);

		Mockito.when(
			_http.URLtoString(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocation -> {
				Http.Options options = invocation.getArgument(0);

				Http.Response httpResponse = new Http.Response();

				httpResponse.setResponseCode(200);

				options.setResponse(httpResponse);

				if (options.isPost()) {
					Http.Body body = options.getBody();

					JSONObject payloadJSONObject =
						_jsonFactory.createJSONObject(body.getContent());

					String targetLanguage = JSONUtil.getValue(
						payloadJSONObject, "Object/target_lang"
					).toString();

					if (targetLanguage.equals("ZH-HANT")) {
						return _getTranslationString("哈囉，世界！");
					}

					if (targetLanguage.equals("ES")) {
						return _getTranslationString("¡Hola, mundo!");
					}

					if (targetLanguage.equals("PT-BR")) {
						return _getTranslationString("Olá, mundo!");
					}
				}

				return JSONUtil.putAll(
					JSONUtil.put("language", "ES"),
					JSONUtil.put("language", "PT-BR"),
					JSONUtil.put("language", "ZH-HANT")
				).toString();
			}
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private long _companyId;
	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);
	private final DeepLTranslator _deepLTranslator = new DeepLTranslator();
	private final Http _http = Mockito.mock(Http.class);
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final Portal _portal = Mockito.mock(Portal.class);

}