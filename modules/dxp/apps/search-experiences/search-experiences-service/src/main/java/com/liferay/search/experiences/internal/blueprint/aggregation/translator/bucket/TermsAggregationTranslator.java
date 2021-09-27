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

package com.liferay.search.experiences.internal.blueprint.aggregation.translator.bucket;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.CollectionMode;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
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
	immediate = true, property = "name=terms",
	service = AggregationTranslator.class
)
public class TermsAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		TermsAggregation aggregation = _aggregations.terms(
			aggregationName, jsonObject.getString("field"));

		_setCollectMode(aggregation, jsonObject);

		_setterHelper.setStringValue(
			jsonObject, "execution_hint", aggregation::setExecutionHint);

		_aggregationHelper.setIncludeExcludeClause(
			jsonObject, aggregation::setIncludeExcludeClause);

		_setterHelper.setIntegerValue(
			jsonObject, "min_doc_count", aggregation::setMinDocCount);

		_setterHelper.setStringValue(
			jsonObject, "missing", aggregation::setMissing);

		_aggregationHelper.setOrders(jsonObject, aggregation::addOrders);

		_aggregationHelper.setScript(jsonObject, aggregation::setScript);

		_setterHelper.setIntegerValue(
			jsonObject, "shard_min_doc_count",
			aggregation::setShardMinDocCount);

		_setterHelper.setIntegerValue(
			jsonObject, "shard_size", aggregation::setShardSize);

		_setterHelper.setBooleanValue(
			jsonObject, "show_term_doc_count_error",
			aggregation::setShowTermDocCountError);

		_setterHelper.setIntegerValue(jsonObject, "size", aggregation::setSize);

		return _aggregationHelper.wrap(aggregation);
	}

	private void _setCollectMode(
		TermsAggregation aggregation, JSONObject jsonObject) {

		String collectModeString = jsonObject.getString("collect_mode");

		if (Validator.isBlank(collectModeString)) {
			return;
		}

		aggregation.setCollectionMode(
			CollectionMode.valueOf(StringUtil.toUpperCase(collectModeString)));
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}