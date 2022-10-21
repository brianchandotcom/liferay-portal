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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	enabled = false, immediate = true,
	property = "search.experiences.sentence.transformer.name=txtai",
	service = SentenceTransformer.class
)
public class TXTAISentenceTransformer
	extends BaseSentenceTransformer implements SentenceTransformer {

	public Double[] getSentenceEmbedding(String text) {
		String input = getInput(
			_semanticSearchConfiguration.maxCharacterCount(), text,
			_semanticSearchConfiguration.textTruncationStrategy());

		if (Validator.isBlank(input)) {
			return new Double[0];
		}

		return _getSentenceEmbedding(input);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_semanticSearchConfiguration = ConfigurableUtil.createConfigurable(
			SemanticSearchConfiguration.class, properties);
	}

	private Double[] _getSentenceEmbedding(String text) {
		String hostAddress = _semanticSearchConfiguration.txtaiHostAddress();

		if (!hostAddress.endsWith("/")) {
			hostAddress += "/";
		}

		try {
			List<Double> list = JSONUtil.toDoubleList(
				_jsonFactory.createJSONArray(
					_http.URLtoString(
						StringBundler.concat(
							hostAddress, "transform?text=",
							URLCodec.encodeURL(text, false)))));

			return list.toArray(new Double[0]);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile SemanticSearchConfiguration _semanticSearchConfiguration;

}