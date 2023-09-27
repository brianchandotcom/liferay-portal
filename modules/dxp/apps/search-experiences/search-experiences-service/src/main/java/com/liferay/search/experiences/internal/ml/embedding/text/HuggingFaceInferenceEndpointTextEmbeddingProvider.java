/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.ml.embedding.text;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "search.experiences.text.embedding.provider.name=huggingFaceInferenceEndpoint",
	service = TextEmbeddingProvider.class
)
public class HuggingFaceInferenceEndpointTextEmbeddingProvider
	extends BaseTextEmbeddingProvider implements TextEmbeddingProvider {

	public Double[] getEmbedding(
		EmbeddingProviderConfiguration embeddingProviderConfiguration,
		String text) {

		Map<String, Object> attributes =
			(Map<String, Object>)embeddingProviderConfiguration.getAttributes();

		if ((attributes == null) || !attributes.containsKey("accessToken")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Attributes do not contain access token");
			}

			return new Double[0];
		}

		String sentences = extractSentences(
			MapUtil.getInteger(attributes, "maxCharacterCount", 1000), text,
			MapUtil.getString(
				attributes, "textTruncationStrategy", "beginning"));

		if (Validator.isBlank(sentences)) {
			return new Double[0];
		}

		return _getEmbedding(attributes, sentences);
	}

	private Double[] _getEmbedding(
		Map<String, Object> attributes, String text) {

		try {
			Http.Options options = new Http.Options();

			JSONObject requestJSONObject = JSONUtil.put("inputs", text);

			options.addHeader(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + MapUtil.getString(attributes, "accessToken"));
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			options.setBody(
				requestJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
			options.setCookieSpec(Http.CookieSpec.STANDARD);
			options.setLocation(MapUtil.getString(attributes, "hostAddress"));
			options.setPost(true);

			JSONObject responseJSONObject = _jsonFactory.createJSONObject(
				_http.URLtoString(options));

			if (responseJSONObject.has("embeddings")) {
				List<Double> list = JSONUtil.toDoubleList(
					responseJSONObject.getJSONArray("embeddings"));

				return list.toArray(new Double[0]);
			}

			throw new IllegalArgumentException(responseJSONObject.toString());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HuggingFaceInferenceEndpointTextEmbeddingProvider.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}