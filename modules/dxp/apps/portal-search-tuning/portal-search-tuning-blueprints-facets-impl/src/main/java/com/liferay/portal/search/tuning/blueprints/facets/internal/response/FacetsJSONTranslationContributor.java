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

package com.liferay.portal.search.tuning.blueprints.facets.internal.response;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.engine.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsBlueprintKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler.FacetResponseHandlerFactory;
import com.liferay.portal.search.tuning.blueprints.facets.internal.util.FacetConfigurationUtil;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
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
	immediate = true, property = "name=facets",
	service = JSONTranslationContributor.class
)
public class FacetsJSONTranslationContributor
	implements JSONTranslationContributor {

	@Override
	public void contribute(
		JSONObject responseJSONObject, SearchResponse searchResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages) {

		responseJSONObject.put(
			FacetsJSONResponseKeys.FACETS,
			_getFacetsJSONObject(
				searchResponse, blueprint, blueprintsAttributes, resourceBundle,
				messages));
	}

	private JSONObject _getFacetsJSONObject(
		SearchResponse searchResponse, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Optional<JSONArray> configurationJSONArrayOptional =
			_blueprintHelper.getJSONArrayConfigurationOptional(
				blueprint,
				"JSONArray/" + FacetsBlueprintKeys.CONFIGURATION_SECTION);

		Map<String, AggregationResult> aggregationResultsMap =
			searchResponse.getAggregationResultsMap();

		if (aggregationResultsMap.isEmpty() ||
			!configurationJSONArrayOptional.isPresent()) {

			return jsonObject;
		}

		_processFacets(
			jsonObject, aggregationResultsMap, blueprintsAttributes,
			resourceBundle, messages, configurationJSONArrayOptional.get());

		return jsonObject;
	}

	private void _processFacets(
		JSONObject jsonObject,
		Map<String, AggregationResult> aggregationResultsMap,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages,
		JSONArray configurationJSONArray) {

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			JSONObject configurationJSONObject =
				configurationJSONArray.getJSONObject(i);

			boolean enabled = configurationJSONObject.getBoolean(
				FacetConfigurationKeys.ENABLED.getJsonKey(), true);

			if (!enabled) {
				continue;
			}

			String responseHandlerName = configurationJSONObject.getString(
				FacetConfigurationKeys.HANDLER.getJsonKey(), "default");

			String aggregationName = FacetConfigurationUtil.getAggregationName(
				configurationJSONObject);

			for (Map.Entry<String, AggregationResult> entry :
					aggregationResultsMap.entrySet()) {

				String aggregationResultName = entry.getKey();

				if (!StringUtil.equalsIgnoreCase(
						aggregationResultName, aggregationName)) {

					continue;
				}

				FacetResponseHandler facetResponseHandler =
					_facetResponseHandlerFactory.getHandler(
						responseHandlerName);

				Optional<JSONObject> resultOptional =
					facetResponseHandler.getResultOptional(
						entry.getValue(), blueprintsAttributes, resourceBundle,
						messages, configurationJSONObject);

				if (resultOptional.isPresent()) {
					jsonObject.put(
						FacetConfigurationUtil.getFacetName(
							configurationJSONObject),
						resultOptional.get());
				}
			}
		}
	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference(target = "(type=internal)")
	private FacetResponseHandlerFactory _facetResponseHandlerFactory;

	@Reference
	private JSONFactory _jsonFactory;

}