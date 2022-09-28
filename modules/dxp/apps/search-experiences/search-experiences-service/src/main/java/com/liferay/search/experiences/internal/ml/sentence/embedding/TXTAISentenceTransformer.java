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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.configuration.SentenceTransformerConfiguration;

import java.io.IOException;

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
	property = "search.experiences.sentence.transformer.name=txtai",
	service = SentenceTransformer.class
)
public class TXTAISentenceTransformer
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
		String host = _sentenceTransformerConfiguration.txtAiHost();

		if (!host.endsWith("/")) {
			host += "/";
		}

		try {
			List<Double> list = JSONUtil.toDoubleList(
				_jsonFactory.createJSONArray(
					_http.URLtoString(
						StringBundler.concat(
							host, "transform?text=",
							URLCodec.encodeURL(text, false)))));

			return list.toArray(new Double[0]);
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile SentenceTransformerConfiguration
		_sentenceTransformerConfiguration;

}