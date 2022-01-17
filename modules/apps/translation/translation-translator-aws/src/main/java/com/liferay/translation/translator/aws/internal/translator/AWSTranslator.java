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

package com.liferay.translation.translator.aws.internal.translator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration",
	service = Translator.class
)
public class AWSTranslator implements Translator {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #isEnabled(long)}
	 */
	@Deprecated
	public boolean isEnabled() {
		try {
			return isEnabled(CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);
		}

		return false;
	}

	@Override
	public boolean isEnabled(long companyId) throws ConfigurationException {
		return _awsTranslatorConfiguration.enabled();
	}

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket)
		throws PortalException {

		Map<String, String> translatedFieldsMap = new HashMap<>();

		String sourceLanguageCode = _getLanguageCode(
			translatorPacket.getSourceLanguageId());
		String targetLanguageCode = _getLanguageCode(
			translatorPacket.getTargetLanguageId());

		Map<String, String> fieldsMap = translatorPacket.getFieldsMap();

		fieldsMap.forEach(
			(key, value) -> translatedFieldsMap.put(
				key,
				_translate(value, sourceLanguageCode, targetLanguageCode)));

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
		_awsTranslatorConfiguration = ConfigurableUtil.createConfigurable(
			AWSTranslatorConfiguration.class, properties);

		_translateClient = TranslateClient.builder(
		).credentialsProvider(
			StaticCredentialsProvider.create(
				AwsBasicCredentials.create(
					_awsTranslatorConfiguration.accessKey(),
					_awsTranslatorConfiguration.secretKey()))
		).region(
			Region.of(_awsTranslatorConfiguration.region())
		).build();
	}

	private String _getLanguageCode(String languageId) {
		List<String> list = StringUtil.split(languageId, CharPool.UNDERLINE);

		return list.get(0);
	}

	private String _translate(
		String text, String sourceLanguageCode, String targetLanguageCode) {

		if (Validator.isBlank(text)) {
			return text;
		}

		TranslateTextResponse translateTextResponse =
			_translateClient.translateText(
				builder -> builder.sourceLanguageCode(
					sourceLanguageCode
				).targetLanguageCode(
					targetLanguageCode
				).text(
					text
				).build());

		return translateTextResponse.translatedText();
	}

	private static final Log _log = LogFactoryUtil.getLog(AWSTranslator.class);

	private volatile AWSTranslatorConfiguration _awsTranslatorConfiguration;
	private volatile TranslateClient _translateClient;

}