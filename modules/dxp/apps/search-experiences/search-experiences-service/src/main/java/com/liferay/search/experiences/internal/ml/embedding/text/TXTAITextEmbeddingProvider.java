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

package com.liferay.search.experiences.internal.ml.embedding.text;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
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
	property = "search.experiences.text.embedding.provider.name=txtai",
	service = TextEmbeddingProvider.class
)
public class TXTAITextEmbeddingProvider
	extends BaseTextEmbeddingProvider implements TextEmbeddingProvider {

	public Double[] getEmbedding(
		EmbeddingProviderConfiguration embeddingProviderConfiguration,
		String text) {

		Map<String, Object> attributes =
			(Map<String, Object>)embeddingProviderConfiguration.getAttributes();

		if ((attributes == null) || !attributes.containsKey("hostAddress")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Attributes do not contain host address");
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

			String hostAddress = MapUtil.getString(attributes, "hostAddress");

			options.setLocation(_getLocation(hostAddress, text));

			_setAuthOptions(attributes, hostAddress, options);

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

	private String _getLocation(String hostAddress, String text) {
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
		Map<String, Object> attributes, String hostAddress,
		Http.Options options) {

		String basicAuthUsername = MapUtil.getString(
			attributes, "basicAuthUsername");

		if (Validator.isBlank(basicAuthUsername)) {
			return;
		}

		options.setAuth(
			HttpComponentsUtil.getDomain(hostAddress), -1, null,
			basicAuthUsername,
			MapUtil.getString(
				attributes, "basicAuthPassword", StringPool.BLANK));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TXTAITextEmbeddingProvider.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}