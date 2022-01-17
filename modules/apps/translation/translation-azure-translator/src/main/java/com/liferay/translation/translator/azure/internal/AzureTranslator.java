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

package com.liferay.translation.translator.azure.internal;

import com.liferay.petra.apache.http.components.URIBuilder;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.azure.internal.configuration.AzureTranslatorConfiguration;

import java.io.IOException;

import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.translation.translator.azure.internal.configuration.AzureTranslatorConfiguration",
	service = Translator.class
)
public class AzureTranslator implements Translator {

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

	@Override
	public boolean isEnabled(long companyId) throws ConfigurationException {
		return _azureTranslatorConfiguration.enabled();
	}

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket)
		throws PortalException {

		try (CloseableHttpClient closeableHttpClient =
				HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(
				URIBuilder.create(
					"https://api.cognitive.microsofttranslator.com/translate"
				).addParameter(
					"api-version", "3.0"
				).addParameter(
					"from",
					_getLanguageCode(translatorPacket.getSourceLanguageId())
				).addParameter(
					"to",
					_getLanguageCode(translatorPacket.getTargetLanguageId())
				).build());

			httpPost.addHeader(
				"Ocp-Apim-Subscription-Key",
				_azureTranslatorConfiguration.subscriptionKey());
			httpPost.addHeader(
				"Ocp-Apim-Subscription-Region",
				_azureTranslatorConfiguration.resourceLocation());
			httpPost.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

			httpPost.setEntity(
				new StringEntity(
					_getTranslatorPacketPayload(translatorPacket)));

			try (CloseableHttpResponse closeableHttpResponse =
					closeableHttpClient.execute(httpPost)) {

				StatusLine statusLine = closeableHttpResponse.getStatusLine();

				if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
					throw new PortalException(statusLine.getReasonPhrase());
				}

				HttpEntity entity = closeableHttpResponse.getEntity();

				Map<String, String> translatedFieldsMap =
					_getTranslatedFieldsMap(
						translatorPacket.getFieldsMap(),
						JSONFactoryUtil.createJSONArray(
							StreamUtil.toString(entity.getContent())));

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
		}
		catch (IOException | URISyntaxException exception) {
			throw new PortalException(exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_azureTranslatorConfiguration = ConfigurableUtil.createConfigurable(
			AzureTranslatorConfiguration.class, properties);
	}

	private String _getLanguageCode(String languageId) {
		List<String> list = StringUtil.split(languageId, CharPool.UNDERLINE);

		return list.get(0);
	}

	private Map<String, String> _getTranslatedFieldsMap(
		Map<String, String> fieldsMap, JSONArray jsonArray) {

		Map<String, String> translatedFieldsMap = new HashMap<>();

		int i = 0;

		for (String key : fieldsMap.keySet()) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray translationsJSONArray = jsonObject.getJSONArray(
				"translations");

			JSONObject translationJSONObject =
				translationsJSONArray.getJSONObject(0);

			translatedFieldsMap.put(
				key, translationJSONObject.getString("text"));

			i++;
		}

		return translatedFieldsMap;
	}

	private String _getTranslatorPacketPayload(
		TranslatorPacket translatorPacket) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Map<String, String> fields = translatorPacket.getFieldsMap();

		fields.forEach(
			(key, value) -> jsonArray.put(JSONUtil.put("Text", value)));

		return jsonArray.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AzureTranslator.class);

	private volatile AzureTranslatorConfiguration _azureTranslatorConfiguration;

}