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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.contributor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.AggregationConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.JSONKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.aggregation.AggregationJSONTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.aggregation.AggregationJSONTranslator;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.contributor.JSONTranslationContributor;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=aggregations",
	service = JSONTranslationContributor.class
)
public class AggregationsTranslationContributor
	implements JSONTranslationContributor {

	@Override
	public void contribute(
		JSONObject responseJSONObject, SearchResponse searchResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages) {

		responseJSONObject.put(
			JSONKeys.AGGREGATIONS,
			_getAggregationsJSONObject(searchResponse, blueprint, messages));
	}

	private JSONObject _getAggregationsJSONObject(
		SearchResponse searchResponse, Blueprint blueprint, Messages messages) {

		JSONObject aggregationsJSONObject = _jsonFactory.createJSONObject();

		Optional<JSONArray> aggregationsConfigurationOptional =
			_blueprintHelper.getAggsConfigurationOptional(blueprint);

		Map<String, AggregationResult> aggregations =
			searchResponse.getAggregationResultsMap();

		if (aggregations.isEmpty() ||
			!aggregationsConfigurationOptional.isPresent()) {

			return aggregationsJSONObject;
		}

		JSONArray aggregationsConfigurationJSONArray =
			aggregationsConfigurationOptional.get();

		for (int i = 0; i < aggregationsConfigurationJSONArray.length(); i++) {
			JSONObject aggregationJSONObject =
				aggregationsConfigurationJSONArray.getJSONObject(i);

			String aggregationName = aggregationJSONObject.getString(
				AggregationConfigurationKeys.NAME.getJsonKey());

			String type = aggregationJSONObject.getString(
				AggregationConfigurationKeys.TYPE.getJsonKey());

			for (Map.Entry<String, AggregationResult> entry :
					aggregations.entrySet()) {

				String aggregationResultName = entry.getKey();

				if (!StringUtil.equalsIgnoreCase(
						aggregationResultName, aggregationName)) {

					continue;
				}

				try {
					AggregationJSONTranslator aggregationResponseBuilder =
						_aggregationResponseBuilderFactory.getBuilder(type);

					Optional<JSONObject> aggregationJsonOptional =
						aggregationResponseBuilder.translate(entry.getValue());

					if (aggregationJsonOptional.isPresent()) {
						aggregationsJSONObject.put(
							aggregationName, aggregationJsonOptional.get());
					}
				}
				catch (IllegalArgumentException illegalArgumentException) {
					messages.addMessage(
						new Message.Builder().className(
							getClass().getName()
						).localizationKey(
							"json-response.error.unknown-aggregation-type"
						).msg(
							illegalArgumentException.getMessage()
						).rootObject(
							aggregationJSONObject
						).rootProperty(
							AggregationConfigurationKeys.TYPE.getJsonKey()
						).rootValue(
							type
						).severity(
							Severity.ERROR
						).throwable(
							illegalArgumentException
						).build());

					_log.error(
						illegalArgumentException.getMessage(),
						illegalArgumentException);
				}
			}
		}

		return aggregationsJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AggregationsTranslationContributor.class);

	@Reference
	private AggregationJSONTranslatorFactory _aggregationResponseBuilderFactory;

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private JSONFactory _jsonFactory;

}