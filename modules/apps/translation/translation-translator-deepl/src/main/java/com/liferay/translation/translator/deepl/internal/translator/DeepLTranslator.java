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

package com.liferay.translation.translator.deepl.internal.translator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;
import com.liferay.translation.translator.deepl.internal.constants.DeepLConstants;
import com.liferay.translation.translator.deepl.internal.model.SupportedLanguage;
import com.liferay.translation.translator.deepl.internal.model.TranslateResponse;
import com.liferay.translation.translator.deepl.internal.model.Translation;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	configurationPid = "com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
	service = Translator.class
)
public class DeepLTranslator implements Translator {

	@Override
	public boolean isEnabled(long companyId) throws ConfigurationException {
		DeepLTranslatorConfiguration deepLTranslatorConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DeepLTranslatorConfiguration.class, companyId);

		if (deepLTranslatorConfiguration.enabled() &&
			!Validator.isBlank(deepLTranslatorConfiguration.authKey()) &&
			!Validator.isBlank(deepLTranslatorConfiguration.url())) {

			return true;
		}

		return false;
	}

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket)
		throws PortalException {

		if (!isEnabled(translatorPacket.getCompanyId())) {
			return translatorPacket;
		}

		DeepLTranslatorConfiguration deepLTranslatorConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DeepLTranslatorConfiguration.class,
				translatorPacket.getCompanyId());

		String targetLanguageCode = _getLanguageCode(
			translatorPacket.getTargetLanguageId());

		List<String> supportedLanguages = _getSupportedLanguages(
			deepLTranslatorConfiguration);

		if (!_verifyLanguage(supportedLanguages, targetLanguageCode)) {
			_log.error(
				StringBundler.concat(
					"No target language available for ", targetLanguageCode,
					". Supported languages are: ",
					StringUtil.merge(
						supportedLanguages, StringPool.COMMA_AND_SPACE)));

			return translatorPacket;
		}

		String sourceLanguageCode = _getLanguageCode(
			translatorPacket.getSourceLanguageId());

		Map<String, String> translatedFieldsMap = new HashMap<>();
		Map<String, String> fieldsMap = translatorPacket.getFieldsMap();

		for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
			translatedFieldsMap.put(
				entry.getKey(),
				_translate(
					deepLTranslatorConfiguration.url(),
					deepLTranslatorConfiguration.authKey(), entry.getValue(),
					sourceLanguageCode, targetLanguageCode));
		}

		return new TranslatorPacket() {

			@Override
			public long getCompanyId() {
				return translatorPacket.getCompanyId();
			}

			@Override
			public Map<String, String> getFieldsMap() {
				return translatedFieldsMap;
			}

			@Override
			public String getSourceLanguageId() {
				return translatorPacket.getSourceLanguageId();
			}

			@Override
			public String getTargetLanguageId() {
				return translatorPacket.getTargetLanguageId();
			}

		};
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_deepLTranslatorConfiguration = ConfigurableUtil.createConfigurable(
			DeepLTranslatorConfiguration.class, properties);
	}

	private String _getLanguageCode(String languageId) {
		List<String> list = Arrays.asList(
			StringUtil.split(languageId, CharPool.UNDERLINE));

		return StringUtil.toUpperCase(list.get(0));
	}

	private List<String> _getSupportedLanguages(
			DeepLTranslatorConfiguration deepLTranslatorConfiguration)
		throws PortalException {

		try {
			List<SupportedLanguage> supportedLanguages =
				_deepLClient.getSupportedLanguages(
					deepLTranslatorConfiguration.authKey(),
					DeepLConstants.TARGET,
					DeepLConstants.SUPPORTED_LANGUAGES_URL);
			List<String> languages = new ArrayList<>();

			supportedLanguages.forEach(
				supportedLanguage -> languages.add(
					supportedLanguage.getLanguage()));

			return languages;
		}
		catch (IOException ioException) {
			_log.error(
				"Failed to call supported language list." +
					System.lineSeparator() + ioException.getLocalizedMessage());

			return new ArrayList<>();
		}
	}

	private String _translate(
			String url, String authKey, String text, String sourceLanguageCode,
			String targetLanguageCode)
		throws PortalException {

		try {
			if (Validator.isBlank(text)) {
				return text;
			}

			TranslateResponse translateResponse = _deepLClient.execute(
				authKey, text, sourceLanguageCode, targetLanguageCode, url);

			List<Translation> translations =
				translateResponse.getTranslations();

			Translation translation = translations.get(0);

			return translation.getText();
		}
		catch (IOException ioException) {
			throw new TranslatorException(
				"DeepL translator returns original text. " +
					ioException.getLocalizedMessage());
		}
	}

	private boolean _verifyLanguage(
		List<String> deepLLanguages, String compareLanguage) {

		if (Collections.disjoint(
				deepLLanguages, Arrays.asList(compareLanguage))) {

			_log.error(
				"DeepL does not support " + compareLanguage +
					". Abort processing translation.");

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeepLTranslator.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DeepLClient _deepLClient;

	private volatile DeepLTranslatorConfiguration _deepLTranslatorConfiguration;

}