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

package com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseTermsFacetResponseHandler
	implements FacetResponseHandler {

	@Override
	public Optional<JSONObject> getResultOptional(
		AggregationResult aggregationResult,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages,
		JSONObject configurationJSONObject) {

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResult;

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		if (buckets.isEmpty()) {
			return Optional.empty();
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		long frequencyThreshold = configurationJSONObject.getLong(
			FacetConfigurationKeys.FREQUENCY_THRESHOLD.getJsonKey(), 1);

		for (Bucket bucket : buckets) {
			if (bucket.getDocCount() < frequencyThreshold) {
				continue;
			}

			try {
				JSONObject jsonObject = createBucketJSONObject(
					bucket, blueprintsAttributes, resourceBundle);

				if (jsonObject != null) {
					jsonArray.put(
						createBucketJSONObject(
							bucket, blueprintsAttributes, resourceBundle));
				}
			}
			catch (Exception exception) {
				messages.addMessage(
					new Message.Builder().className(
						getClass().getName()
					).localizationKey(
						"facets.error.could-not-create-bucket-object"
					).msg(
						exception.getMessage()
					).rootObject(
						configurationJSONObject
					).severity(
						Severity.ERROR
					).throwable(
						exception
					).build());

				if (_log.isWarnEnabled()) {
					_log.warn(exception.getMessage(), exception);
				}
			}
		}

		if (jsonArray.length() == 0) {
			return Optional.empty();
		}

		return createResultObject(
			jsonArray, configurationJSONObject, resourceBundle);
	}

	protected JSONObject createBucketJSONObject(
			Bucket bucket, BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle)
		throws Exception {

		String value = bucket.getKey();

		long frequency = bucket.getDocCount();

		return JSONUtil.put(
			FacetsJSONResponseKeys.FREQUENCY, frequency
		).put(
			FacetsJSONResponseKeys.TEXT,
			getText(value, frequency, resourceBundle)
		).put(
			FacetsJSONResponseKeys.VALUE, value
		);
	}

	protected Optional<JSONObject> createResultObject(
		JSONArray jsonArray, JSONObject configurationJSONObject,
		ResourceBundle resourceBundle) {

		if (jsonArray.length() == 0) {
			return Optional.empty();
		}

		String handlerName = configurationJSONObject.getString(
			FacetConfigurationKeys.HANDLER.getJsonKey(), "default");

		String parameterName = configurationJSONObject.getString(
			FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());

		String label = configurationJSONObject.getString(
			FacetConfigurationKeys.LABEL.getJsonKey(), parameterName);

		return Optional.of(
			JSONUtil.put(
				FacetsJSONResponseKeys.HANDER_NAME, handlerName
			).put(
				FacetsJSONResponseKeys.LABEL,
				LanguageUtil.get(resourceBundle, label)
			).put(
				FacetsJSONResponseKeys.PARAMETER_NAME, parameterName
			).put(
				FacetsJSONResponseKeys.VALUES, jsonArray
			));
	}

	protected FacetCollector getFacetCollector(
		Collection<Facet> facets, String fieldName) {

		for (Facet facet : facets) {
			if (facet.isStatic()) {
				continue;
			}

			String facetFieldName = facet.getFieldName();

			if (StringUtil.equals(facetFieldName, fieldName)) {
				return facet.getFacetCollector();
			}
		}

		return null;
	}

	protected String getText(
		String value, long frequency, ResourceBundle resourceBundle) {

		value = StringUtil.toLowerCase(value);

		StringBundler sb = new StringBundler(4);

		if (resourceBundle == null) {
			sb.append(value);
		}
		else {
			sb.append(LanguageUtil.get(resourceBundle, value));
		}

		sb.append(" (");
		sb.append(String.valueOf(frequency));
		sb.append(")");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTermsFacetResponseHandler.class);

}