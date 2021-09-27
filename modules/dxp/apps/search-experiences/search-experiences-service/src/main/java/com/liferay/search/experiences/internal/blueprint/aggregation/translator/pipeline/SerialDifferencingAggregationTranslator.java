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

package com.liferay.search.experiences.internal.blueprint.aggregation.translator.pipeline;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslator;
import com.liferay.search.experiences.internal.blueprint.aggregation.util.AggregationHelper;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.SetterHelper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=serial_differencing",
	service = AggregationTranslator.class
)
public class SerialDifferencingAggregationTranslator
	implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		SerialDiffPipelineAggregation aggregation = _aggregations.serialDiff(
			aggregationName, jsonObject.getString("buckets_path"));

		_setterHelper.setStringValue(
			jsonObject, "format", aggregation::setFormat);

		_aggregationHelper.setGapPolicy(jsonObject, aggregation::setGapPolicy);

		_setterHelper.setIntegerValue(jsonObject, "lag", aggregation::setLag);

		return _aggregationHelper.wrap(aggregation);
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}