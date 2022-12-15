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

package com.liferay.search.experiences.internal.ml.text.embedding;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "search.experiences.text.embedding.provider.name=txtai",
	service = TextEmbeddingProvider.class
)
public class TXTAITextEmbeddingProvider
	extends BaseTextEmbeddingProvider implements TextEmbeddingProvider {

	public Double[] getEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		String sentences = extractSentences(
			semanticSearchConfiguration.maxCharacterCount(), text,
			semanticSearchConfiguration.textTruncationStrategy());

		if (Validator.isBlank(sentences)) {
			return new Double[0];
		}

		return _getEmbedding(semanticSearchConfiguration, sentences);
	}

	private Double[] _getEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		try {
			Http.Options options = new Http.Options();

			if (!Validator.isBlank(
					semanticSearchConfiguration.txtaiUsername())) {

				_setAuthOptions(options, semanticSearchConfiguration);
			}

			options.setLocation(
				_getLocation(semanticSearchConfiguration, text));

			String responseJSON = _http.URLtoString(options);

			if (_isJSONArray(responseJSON)) {
				List<Double> list = JSONUtil.toDoubleList(
					_jsonFactory.createJSONArray(responseJSON));

				return list.toArray(new Double[0]);
			}

			throw new IllegalArgumentException(responseJSON);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private String _getLocation(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		String hostAddress = semanticSearchConfiguration.txtaiHostAddress();

		if (!hostAddress.endsWith("/")) {
			hostAddress += "/";
		}

		return StringBundler.concat(
			hostAddress, "transform?text=", URLCodec.encodeURL(text, false));
	}

	private boolean _isJSONArray(String s) {
		if (StringUtil.startsWith(s, "[") && StringUtil.endsWith(s, "]")) {
			return true;
		}

		return false;
	}

	private void _setAuthOptions(
		Http.Options options,
		SemanticSearchConfiguration semanticSearchConfiguration) {

		options.setAuth(
			HttpComponentsUtil.getDomain(
				semanticSearchConfiguration.txtaiHostAddress()),
			-1, null, semanticSearchConfiguration.txtaiUsername(),
			semanticSearchConfiguration.txtaiPassword());
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}