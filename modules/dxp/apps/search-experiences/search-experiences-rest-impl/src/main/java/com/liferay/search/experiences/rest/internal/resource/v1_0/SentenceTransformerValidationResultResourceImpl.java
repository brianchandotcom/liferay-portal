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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.ml.sentence.embedding.SentenceEmbeddingRetriever;
import com.liferay.search.experiences.rest.dto.v1_0.SentenceTransformerValidationResult;
import com.liferay.search.experiences.rest.resource.v1_0.SentenceTransformerValidationResultResource;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/sentence-transformer-validation-result.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SentenceTransformerValidationResultResource.class
)
public class SentenceTransformerValidationResultResourceImpl
	extends BaseSentenceTransformerValidationResultResourceImpl {

	@Override
	public SentenceTransformerValidationResult
			postSentenceTransformerValidateConfiguration(String json)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			return null;
		}

		return _validate(_getProperties(_jsonFactory.createJSONObject(json)));
	}

	private Map<?, ?> _getProperties(JSONObject jsonObject) {
		return HashMapBuilder.<String, Object>put(
			"assetEntryClassNames",
			JSONUtil.toStringArray(
				jsonObject.getJSONArray("assetEntryClassNames"))
		).put(
			"cacheTimeout", jsonObject.getInt("cacheTimeout")
		).put(
			"embeddingVectorDimensions",
			jsonObject.getInt("embeddingVectorDimensions")
		).put(
			"enabled", jsonObject.getBoolean("enabled")
		).put(
			"enableGPU", jsonObject.getBoolean("enableGPU")
		).put(
			"huggingFaceAccessToken",
			jsonObject.getString("huggingFaceAccessToken")
		).put(
			"languageIds",
			JSONUtil.toStringArray(jsonObject.getJSONArray("languageIds"))
		).put(
			"maxCharacterCount", jsonObject.getInt("maxCharacterCount")
		).put(
			"model", jsonObject.getString("model")
		).put(
			"modelTimeout", jsonObject.getInt("modelTimeout")
		).put(
			"sentenceTransformProvider",
			jsonObject.getString("sentenceTransformProvider")
		).put(
			"textTruncationStrategy",
			jsonObject.getString("textTruncationStrategy")
		).put(
			"txtaiHostAddress", jsonObject.getString("txtaiHostAddress")
		).build();
	}

	private SentenceTransformerValidationResult _validate(
		Map<?, ?> properties) {

		try {
			Double[] embedding =
				_sentenceEmbeddingRetriever.getSentenceEmbedding(
					ConfigurableUtil.createConfigurable(
						SemanticSearchConfiguration.class, properties),
					"hello liferay");

			return new SentenceTransformerValidationResult() {
				{
					expectedDimensions = embedding.length;
				}
			};
		}
		catch (Exception exception) {
			return new SentenceTransformerValidationResult() {
				{
					errorMessage = exception.getMessage();
				}
			};
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SentenceEmbeddingRetriever _sentenceEmbeddingRetriever;

}