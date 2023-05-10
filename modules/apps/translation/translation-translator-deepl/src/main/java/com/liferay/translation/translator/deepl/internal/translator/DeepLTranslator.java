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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

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

		return deepLTranslatorConfiguration.enabled();
	}

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket)
		throws PortalException {

		if (!isEnabled(translatorPacket.getCompanyId())) {
			return translatorPacket;
		}

		List<String> supportedLanguageCodes = _getSupportedLanguageCodes();
		String targetLanguageCode = _getLanguageCode(
			translatorPacket.getTargetLanguageId());

		if (!supportedLanguageCodes.contains(targetLanguageCode)) {
			throw new TranslatorException(
				StringBundler.concat(
					"Target language code ", targetLanguageCode,
					" is not among the supported langauge codes: ",
					StringUtil.merge(
						supportedLanguageCodes, StringPool.COMMA_AND_SPACE)));
		}

		Map<String, String> translatedFieldsMap = _translate(
			translatorPacket.getFieldsMap(),
			_getLanguageCode(translatorPacket.getSourceLanguageId()),
			targetLanguageCode);

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
		String[] parts = StringUtil.split(languageId, CharPool.UNDERLINE);

		return StringUtil.toUpperCase(parts[0]);
	}

	private List<String> _getSupportedLanguageCodes() throws PortalException {
		Http.Options options = new Http.Options();

		options.addPart("type", "target");
		options.setMethod(Http.Method.GET);

		return JSONUtil.toList(
			_jsonFactory.createJSONArray(
				_invoke(
					options,
					_deepLTranslatorConfiguration.validateLanguageURL())),
			jsonObject -> jsonObject.getString("language"), _log);
	}

	private String _invoke(Http.Options options, String url)
		throws PortalException {

		String json = null;

		options.addHeader(
			HttpHeaders.AUTHORIZATION,
			"DeepL-Auth-Key " + _deepLTranslatorConfiguration.authKey());
		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.setLocation(url);

		try {
			json = _http.URLtoString(options);
		}
		catch (IOException ioException) {
			throw new TranslatorException(ioException);
		}

		Http.Response response = options.getResponse();

		Response.Status status = Response.Status.fromStatusCode(
			response.getResponseCode());

		if (status == Response.Status.OK) {
			return json;
		}

		throw new TranslatorException("HTTP response status " + status);
	}

	private Map<String, String> _translate(
			Map<String, String> fieldsMap, String sourceLanguageCode,
			String targetLanguageCode)
		throws PortalException {

		Map<String, String> translatedFieldsMap = new HashMap<>();

		for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
			translatedFieldsMap.put(
				entry.getKey(),
				_translate(
					sourceLanguageCode, targetLanguageCode, entry.getValue()));
		}

		return translatedFieldsMap;
	}

	private String _translate(
			String sourceLanguageCode, String targetLanguageCode, String text)
		throws PortalException {

		if (Validator.isBlank(text)) {
			return text;
		}

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.AUTHORIZATION,
			"DeepL-Auth-Key " + _deepLTranslatorConfiguration.authKey());
		options.addPart("source_lang", sourceLanguageCode);
		options.addPart("target_lang", targetLanguageCode);
		options.addPart("text", text);
		options.setMethod(Http.Method.POST);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_invoke(options, _deepLTranslatorConfiguration.url()));

		JSONArray jsonArray = jsonObject.getJSONArray("translations");

		JSONObject translationJSONObject = jsonArray.getJSONObject(0);

		return translationJSONObject.getString("text");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeepLTranslator.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile DeepLTranslatorConfiguration _deepLTranslatorConfiguration;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}