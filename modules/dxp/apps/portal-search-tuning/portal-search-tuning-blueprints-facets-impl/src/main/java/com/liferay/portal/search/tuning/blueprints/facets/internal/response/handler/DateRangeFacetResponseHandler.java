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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=date_range",
	service = FacetResponseHandler.class
)
public class DateRangeFacetResponseHandler
	extends BaseTermsFacetResponseHandler implements FacetResponseHandler {

	@Override
	public Optional<JSONObject> getResultOptional(
		AggregationResult aggregationResult,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages,
		JSONObject configurationJSONObject) {

		RangeAggregationResult rangeAggregationResult =
			(RangeAggregationResult)aggregationResult;

		Collection<Bucket> buckets = rangeAggregationResult.getBuckets();

		if (buckets.isEmpty()) {
			return Optional.empty();
		}

		long frequencyThreshold = configurationJSONObject.getLong(
			FacetConfigurationKeys.FREQUENCY_THRESHOLD.getJsonKey(), 1);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Bucket bucket : buckets) {
			if (bucket.getDocCount() < frequencyThreshold) {
				continue;
			}

			jsonArray.put(_createBucketJSONObject(bucket, resourceBundle));
		}

		return createResultObject(
			jsonArray, configurationJSONObject, resourceBundle);
	}

	private JSONObject _createBucketJSONObject(
		Bucket bucket, ResourceBundle resourceBundle) {

		long frequency = bucket.getDocCount();

		String value = bucket.getKey();

		return JSONUtil.put(
			FacetsJSONResponseKeys.FREQUENCY, frequency
		).put(
			FacetsJSONResponseKeys.NAME, value
		).put(
			FacetsJSONResponseKeys.TEXT,
			getText(value, frequency, resourceBundle)
		).put(
			FacetsJSONResponseKeys.VALUE, value
		);
	}

}