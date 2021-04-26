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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced.HighlightingConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.HighlightHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.searchrequest.SearchRequestBodyContributor;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintTemplateVariableParser;
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
			searchRequestBuilder, configurationJSONObjectOptional.get(),
			parameterData, messages);
	}

	private void _contribute(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject configurationJSONObject, ParameterData parameterData,
		Messages messages) {

		boolean enabled = configurationJSONObject.getBoolean(
			HighlightingConfigurationKeys.ENABLED.getJsonKey());

		if (!enabled) {
			searchRequestBuilder.highlightEnabled(false);

			return;
		}

		Optional<JSONObject> highlightJSONObjectOptional =
			_blueprintTemplateVariableParser.parseObject(
				configurationJSONObject, parameterData, messages);

		if (!highlightJSONObjectOptional.isPresent()) {
			return;
		}

		Optional<Highlight> optional = _highlightHelper.getHighlight(
			configurationJSONObject, parameterData, messages);

		if (optional.isPresent()) {
			searchRequestBuilder.highlight(optional.get());
		}
	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintTemplateVariableParser _blueprintTemplateVariableParser;

	@Reference
	private HighlightHelper _highlightHelper;

}