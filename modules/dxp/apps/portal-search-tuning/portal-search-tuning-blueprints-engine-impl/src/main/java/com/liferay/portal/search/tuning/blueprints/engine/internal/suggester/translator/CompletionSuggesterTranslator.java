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

package com.liferay.portal.search.tuning.blueprints.engine.internal.suggester.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester.CompletionSuggesterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=completion",
	service = SuggesterTranslator.class
)
public class CompletionSuggesterTranslator
	extends BaseSuggesterTranslator implements SuggesterTranslator {

	@Override
	public Optional<Suggester> translate(
		String suggesterName, JSONObject configurationJSONObject,
		ParameterData parameterData, Messages messages) {

		if (!validateSuggesterConfiguration(
				messages, configurationJSONObject, suggesterName)) {

			return Optional.empty();
		}

		String field = configurationJSONObject.getString(
			CompletionSuggesterConfigurationKeys.FIELD.getJsonKey());

		CompletionSuggester completionSuggester = new CompletionSuggester(
			suggesterName, field,
			getValue(configurationJSONObject, parameterData));

		if (!configurationJSONObject.isNull(
				CompletionSuggesterConfigurationKeys.ANALYZER.getJsonKey())) {

			completionSuggester.setAnalyzer(
				configurationJSONObject.getString(
					CompletionSuggesterConfigurationKeys.ANALYZER.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				CompletionSuggesterConfigurationKeys.SHARD_SIZE.getJsonKey())) {

			completionSuggester.setShardSize(
				configurationJSONObject.getInt(
					CompletionSuggesterConfigurationKeys.SHARD_SIZE.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				CompletionSuggesterConfigurationKeys.SIZE.getJsonKey())) {

			completionSuggester.setSize(
				configurationJSONObject.getInt(
					CompletionSuggesterConfigurationKeys.SIZE.getJsonKey()));
		}

		return Optional.of(completionSuggester);
	}

	protected String getValue(
		JSONObject configurationJSONObject, ParameterData parameterData) {

		String text = configurationJSONObject.getString(
			CompletionSuggesterConfigurationKeys.PREFIX.getJsonKey());

		if (Validator.isBlank(text)) {
			text = parameterData.getKeywords();
		}

		return StringUtil.toLowerCase(text);
	}

}