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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester.SuggesterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.suggester.SuggesterTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.searchrequest.SearchRequestBodyContributor;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintTemplateVariableParser;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=suggest",
	service = SearchRequestBodyContributor.class
)
public class SuggestSearchRequestBodyContributor
	implements SearchRequestBodyContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, Blueprint blueprint,
		ParameterData parameterData, Messages messages) {

		Optional<JSONArray> configurationJSONArrayOptional =
			_blueprintHelper.getSuggestConfigurationOptional(blueprint);

		if (!configurationJSONArrayOptional.isPresent()) {
			return;
		}

		_contribute(
			searchRequestBuilder, configurationJSONArrayOptional.get(),
			parameterData, messages);
	}

	private void _contribute(
		SearchRequestBuilder searchRequestBuilder,
		JSONArray configurationJSONArray, ParameterData parameterData,
		Messages messages) {

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			JSONObject configurationJSONObject =
				configurationJSONArray.getJSONObject(i);

			Optional<Suggester> suggesterOptional = _getSuggesterOptional(
				configurationJSONObject, parameterData, messages);

			if (suggesterOptional.isPresent()) {

				// TODO: https://issues.liferay.com/browse/LPS-120711

				searchRequestBuilder.getClass();
			}
		}
	}

	private Optional<Suggester> _getSuggesterOptional(
		JSONObject configurationJSONObject, ParameterData parameterData,
		Messages messages) {

		if (!_validateConfiguration(configurationJSONObject, messages) ||
			!configurationJSONObject.getBoolean(
				SuggesterConfigurationKeys.ENABLED.getJsonKey(), true)) {

			return Optional.empty();
		}

		String name = configurationJSONObject.getString(
			SuggesterConfigurationKeys.NAME.getJsonKey());

		String type = configurationJSONObject.getString(
			SuggesterConfigurationKeys.TYPE.getJsonKey());

		try {
			Optional<JSONObject> suggestConfigurationJSONObjectOptional =
				_blueprintTemplateVariableParser.parse(
					configurationJSONObject.getJSONObject(
						SuggesterConfigurationKeys.CONFIGURATION.getJsonKey()),
					parameterData, messages);

			if (!suggestConfigurationJSONObjectOptional.isPresent()) {
				return Optional.empty();
			}

			SuggesterTranslator suggesterTranslator =
				_suggesterTranslatorFactory.getTranslator(type);

			return suggesterTranslator.translate(
				name, suggestConfigurationJSONObjectOptional.get(),
				parameterData, messages);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-suggester-type"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					SuggesterConfigurationKeys.TYPE.getJsonKey()
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

		return Optional.empty();
	}

	private boolean _validateConfiguration(
		JSONObject configurationJSONObject, Messages messages) {

		boolean valid = true;

		if (configurationJSONObject.isNull(
				SuggesterConfigurationKeys.NAME.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-suggester-name"
				).msg(
					"Suggester name is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					SuggesterConfigurationKeys.NAME.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Suggester name is not defined [ " +
						configurationJSONObject + "]");
			}
		}

		if (configurationJSONObject.isNull(
				SuggesterConfigurationKeys.TYPE.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-suggester-type"
				).msg(
					"Suggester type is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					SuggesterConfigurationKeys.TYPE.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			valid = false;

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Suggester type is not defined [ " +
						configurationJSONObject + "]");
			}
		}

		return valid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SuggestSearchRequestBodyContributor.class);

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintTemplateVariableParser _blueprintTemplateVariableParser;

	@Reference
	private SuggesterTranslatorFactory _suggesterTranslatorFactory;

}