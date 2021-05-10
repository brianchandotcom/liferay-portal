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
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration",
	service = Translator.class
)
public class GoogleCloudTranslator implements Translator {

	public boolean isEnabled() {
		if (_googleCloudTranslatorConfiguration.enabled() &&
			!Validator.isBlank(
				_googleCloudTranslatorConfiguration.
					serviceAccountPrivateKey())) {

			return true;
		}

		return false;
	}

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket) {
		if (!isEnabled()) {
			return translatorPacket;
		}

		Map<String, String> fieldsMap = translatorPacket.getFieldsMap();

		List<Translation> translations = _translate.translate(
			new ArrayList<>(fieldsMap.values()),
			Translate.TranslateOption.sourceLanguage(
				_getLanguageCode(translatorPacket.getSourceLanguageId())),
			Translate.TranslateOption.targetLanguage(
				_getLanguageCode(translatorPacket.getTargetLanguageId())));

		Map<String, String> translationFieldsMap = new HashMap<>();

		Iterator<Translation> iterator = translations.iterator();

		for (String key : fieldsMap.keySet()) {
			Translation translation = iterator.next();

			translationFieldsMap.put(key, translation.getTranslatedText());
		}

		return new TranslatorPacket() {

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

	@Activate
	protected void activate(Map<String, Object> properties) {
		_googleCloudTranslatorConfiguration =
			ConfigurableUtil.createConfigurable(
				GoogleCloudTranslatorConfiguration.class, properties);

		if (!isEnabled()) {
			return;
		}

		ServiceAccountCredentials serviceAccountCredentials = null;

		String serviceAccountPrivateKey =
			_googleCloudTranslatorConfiguration.serviceAccountPrivateKey();

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

		_translate = defaultTranslateFactory.create(
			TranslateOptions.newBuilder(
			).setCredentials(
				serviceAccountCredentials
			).build());
	}

	private String _getLanguageCode(String languageId) {
		List<String> list = StringUtil.split(languageId, CharPool.UNDERLINE);

		return list.get(0);
	}

	private GoogleCloudTranslatorConfiguration
		_googleCloudTranslatorConfiguration;
	private Translate _translate;

}