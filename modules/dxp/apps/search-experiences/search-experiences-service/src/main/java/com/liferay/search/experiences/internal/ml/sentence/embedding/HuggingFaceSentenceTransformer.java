/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.ml.sentence.embedding;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.configuration.SentenceTransformerConfiguration;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.search.experiences.configuration.SentenceTransformerConfiguration",
	enabled = false, immediate = true,
	property = "search.experiences.sentence.transformer.name=huggingFace",
	service = SentenceTransformer.class
)
public class HuggingFaceSentenceTransformer
	extends BaseSentenceTransformer implements SentenceTransformer {

	public Double[] getEmbedding(String text) {
		String input = getInput(
			_sentenceTransformerConfiguration.maxCharacterCount(), text,
			_sentenceTransformerConfiguration.textTruncationStrategy());

		if (Validator.isBlank(input)) {
			return new Double[0];
		}

		return _getEmbedding(input);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sentenceTransformerConfiguration = ConfigurableUtil.createConfigurable(
			SentenceTransformerConfiguration.class, properties);
	}

	private Double[] _getEmbedding(String text) {
		try {
			Http.Options options = new Http.Options();

			String responseJSON = _getResponse(options, text);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() ==
					HttpURLConnection.HTTP_UNAVAILABLE) {

				options.addHeader("x-wait-for-model", "true");

				options.setTimeout(
					_sentenceTransformerConfiguration.modelTimeout() * 1000);

				responseJSON = _getResponse(options, text);
			}

			List<Double> list = JSONUtil.toDoubleList(
				_getJSONArray(_jsonFactory.createJSONArray(responseJSON)));

			return list.toArray(new Double[0]);
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

	private JSONArray _getJSONArray(JSONArray jsonArray1) {
		JSONArray jsonArray2 = jsonArray1.getJSONArray(0);

		if (jsonArray2 != null) {
			return _getJSONArray(jsonArray2);
		}

		return jsonArray1;
	}

	private String _getResponse(Http.Options options, String text)
		throws IOException {

		JSONObject jsonObject = JSONUtil.put("inputs", text);

		options.addHeader(
			HttpHeaders.AUTHORIZATION,
			StringBundler.concat(
				"Bearer ${",
				_sentenceTransformerConfiguration.huggingFaceAccessToken(),
				"}"));
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		if (_sentenceTransformerConfiguration.enableGPU()) {
			options.addHeader("x-use-gpu", "true");
		}

		options.setBody(
			jsonObject.toString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(
			_HUGGING_FACE_INFERENCE_API_ROOT +
				_sentenceTransformerConfiguration.model());
		options.setPost(true);

		return _http.URLtoString(options);
	}

	private static final String _HUGGING_FACE_INFERENCE_API_ROOT =
		"https://api-inference.huggingface.co/models/";

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile SentenceTransformerConfiguration
		_sentenceTransformerConfiguration;

}