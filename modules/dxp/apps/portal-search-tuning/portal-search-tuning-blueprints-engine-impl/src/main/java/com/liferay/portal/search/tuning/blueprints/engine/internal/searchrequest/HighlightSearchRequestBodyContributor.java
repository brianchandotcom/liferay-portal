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

package com.liferay.portal.search.tuning.blueprints.engine.internal.searchrequest;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.highlight.Highlights;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced.HighlightingConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.searchrequest.SearchRequestBodyContributor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=highlight",
	service = SearchRequestBodyContributor.class
)
public class HighlightSearchRequestBodyContributor
	implements SearchRequestBodyContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, Blueprint blueprint,
		ParameterData parameterData, Messages messages) {

		Optional<JSONObject> configurationJSONObjectOptional =
			_blueprintHelper.getHighlightConfigurationOptional(blueprint);

		if (!configurationJSONObjectOptional.isPresent()) {
			return;
		}

		_contribute(
			searchRequestBuilder, configurationJSONObjectOptional.get());
	}

	private void _addFieldConfigs(
		HighlightBuilder highlightBuilder, JSONArray fieldsJSONArray) {

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if (!fieldJSONObject.has(
					HighlightingConfigurationKeys.FIELD.getJsonKey())) {

				continue;
			}

			FieldConfigBuilder fieldConfigBuilder =
				_highlights.fieldConfigBuilder();

			fieldConfigBuilder.field(
				fieldJSONObject.getString(
					HighlightingConfigurationKeys.FIELD.getJsonKey()));

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.FRAGMENT_OFFSET.
						getJsonKey())) {

				fieldConfigBuilder.fragmentOffset(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.FRAGMENT_OFFSET.
							getJsonKey()));
			}

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey())) {

				fieldConfigBuilder.fragmentSize(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.FRAGMENT_SIZE.
							getJsonKey()));
			}

			if (fieldJSONObject.has(
					HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
						getJsonKey())) {

				fieldConfigBuilder.numFragments(
					fieldJSONObject.getInt(
						HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
							getJsonKey()));
			}

			highlightBuilder.addFieldConfig(fieldConfigBuilder.build());
		}
	}

	private void _contribute(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject configurationJSONObject) {

		boolean enabled = configurationJSONObject.getBoolean(
			HighlightingConfigurationKeys.ENABLED.getJsonKey());

		if (!enabled) {
			searchRequestBuilder.highlightEnabled(false);

			return;
		}

		HighlightBuilder highlightBuilder = _highlights.builder();

		if (configurationJSONObject.has(
				HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey())) {

			highlightBuilder.fragmentSize(
				configurationJSONObject.getInt(
					HighlightingConfigurationKeys.FRAGMENT_SIZE.getJsonKey()));
		}

		if (configurationJSONObject.has(
				HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
					getJsonKey())) {

			highlightBuilder.numOfFragments(
				configurationJSONObject.getInt(
					HighlightingConfigurationKeys.NUMBER_OF_FRAGMENTS.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				HighlightingConfigurationKeys.REQUIRE_FIELD_MATCH.
					getJsonKey())) {

			highlightBuilder.requireFieldMatch(
				configurationJSONObject.getBoolean(
					HighlightingConfigurationKeys.REQUIRE_FIELD_MATCH.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				HighlightingConfigurationKeys.FIELDS.getJsonKey())) {

			JSONArray fieldsJSONArray = configurationJSONObject.getJSONArray(
				HighlightingConfigurationKeys.FIELDS.getJsonKey());

			_addFieldConfigs(highlightBuilder, fieldsJSONArray);
		}

		// TODO: https://issues.liferay.com/browse/LPS-121365

		// searchRequestBuilder.setHightlight(highlightBuilder.build());

	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private Highlights _highlights;

}