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

package com.liferay.translation.google.cloud.translator.internal.translator;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration",
	service = Translator.class
)
public class GoogleCloudTranslator implements Translator {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #isEnabled(long)}
	 */
	@Deprecated
	public boolean isEnabled() {
		try {
			return isEnabled(CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException, configurationException);
			}
		}

		return false;
	}

	public boolean isEnabled(long companyId) throws ConfigurationException {
		GoogleCloudTranslatorConfiguration
			googleCloudTranslatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					GoogleCloudTranslatorConfiguration.class, companyId);

		if (googleCloudTranslatorCompanyConfiguration.enabled() &&
			!Validator.isBlank(
				googleCloudTranslatorCompanyConfiguration.
					serviceAccountPrivateKey())) {

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

		String sourceLanguageCode = _getLanguageCode(
			translatorPacket.getSourceLanguageId());
		String targetLanguageCode = _getLanguageCode(
			translatorPacket.getTargetLanguageId());

		Translate translate = _getTranslate(translatorPacket.getCompanyId());

		Set<String> supportedLanguageCodes = _getSupportedLanguageCodes(
			translate.listSupportedLanguages());

		if (!supportedLanguageCodes.contains(sourceLanguageCode) ||
			!supportedLanguageCodes.contains(targetLanguageCode)) {

			throw new TranslatorException(
				"Translation between the selected languages is not supported");
		}

		Map<String, String> fieldsMap = translatorPacket.getFieldsMap();

		List<Translation> translations = translate.translate(
			new ArrayList<>(fieldsMap.values()),
			Translate.TranslateOption.sourceLanguage(sourceLanguageCode),
			Translate.TranslateOption.targetLanguage(targetLanguageCode));

		Map<String, String> translationFieldsMap = new HashMap<>();

		Iterator<Translation> iterator = translations.iterator();

		for (String key : fieldsMap.keySet()) {
			Translation translation = iterator.next();

			translationFieldsMap.put(key, translation.getTranslatedText());
		}

		return new TranslatorPacket() {

			@Override
			public long getCompanyId() {
				return translatorPacket.getCompanyId();
			}

			@Override
			public Map<String, String> getFieldsMap() {
				return translationFieldsMap;
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

	private String _getLanguageCode(String languageId) {
		List<String> list = StringUtil.split(languageId, CharPool.UNDERLINE);

		return list.get(0);
	}

	private Set<String> _getSupportedLanguageCodes(
		List<Language> supportedLanguages) {

		Stream<Language> stream = supportedLanguages.stream();

		return stream.map(
			Language::getCode
		).collect(
			Collectors.toSet()
		);
	}

	private Translate _getTranslate(long companyId)
		throws ConfigurationException {

		GoogleCloudTranslatorConfiguration
			googleCloudTranslatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					GoogleCloudTranslatorConfiguration.class, companyId);

		String serviceAccountPrivateKey =
			googleCloudTranslatorCompanyConfiguration.
				serviceAccountPrivateKey();

		ServiceAccountCredentials serviceAccountCredentials = null;

		try (InputStream inputStream = new ByteArrayInputStream(
				serviceAccountPrivateKey.getBytes())) {

			serviceAccountCredentials = ServiceAccountCredentials.fromStream(
				inputStream);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to authenticate with Google Cloud", ioException);
		}

		TranslateOptions.DefaultTranslateFactory defaultTranslateFactory =
			new TranslateOptions.DefaultTranslateFactory();

		return defaultTranslateFactory.create(
			TranslateOptions.newBuilder(
			).setCredentials(
				serviceAccountCredentials
			).build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleCloudTranslator.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}