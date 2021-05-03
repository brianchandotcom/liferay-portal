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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester.PhraseSuggesterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester.TermSuggesterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=term",
	service = SuggesterTranslator.class
)
public class TermSuggesterTranslator
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
			TermSuggesterConfigurationKeys.FIELD.getJsonKey());

		TermSuggester termSuggester = new TermSuggester(
			suggesterName, field,
			getValue(configurationJSONObject, parameterData));

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.ANALYZER.getJsonKey())) {

			termSuggester.setAccuracy(
				GetterUtil.getFloat(
					configurationJSONObject.get(
						TermSuggesterConfigurationKeys.ANALYZER.getJsonKey())));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.SIZE.getJsonKey())) {

			termSuggester.setSize(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.SIZE.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.SUGGEST_MODE.getJsonKey())) {

			try {
				termSuggester.setSuggestMode(
					getSuggestMode(
						TermSuggesterConfigurationKeys.SUGGEST_MODE.
							getJsonKey()));
			}
			catch (IllegalArgumentException illegalArgumentException) {
				messages.addMessage(
					new Message.Builder().className(
						getClass().getName()
					).localizationKey(
						"core.error.unknown-suggest-mode"
					).msg(
						"Unknown suggest mode"
					).rootObject(
						configurationJSONObject
					).rootProperty(
						TermSuggesterConfigurationKeys.SUGGEST_MODE.getJsonKey()
					).severity(
						Severity.ERROR
					).build());

				_log.error(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.MAX_EDITS.getJsonKey())) {

			termSuggester.setMaxEdits(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.MAX_EDITS.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.MAX_INSPECTIONS.getJsonKey())) {

			termSuggester.setMaxInspections(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.MAX_INSPECTIONS.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.MAX_TERM_FREQ.getJsonKey())) {

			termSuggester.setMaxTermFreq(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.MAX_TERM_FREQ.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.MIN_DOC_FREQ.getJsonKey())) {

			termSuggester.setMinDocFreq(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.MIN_DOC_FREQ.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.MIN_WORD_LENGTH.getJsonKey())) {

			termSuggester.setMinWordLength(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.MIN_WORD_LENGTH.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.PREFIX_LENGTH.getJsonKey())) {

			termSuggester.setPrefixLength(
				configurationJSONObject.getInt(
					TermSuggesterConfigurationKeys.PREFIX_LENGTH.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.SHARD_SIZE.getJsonKey())) {

			termSuggester.setShardSize(
				configurationJSONObject.getInt(
					PhraseSuggesterConfigurationKeys.SHARD_SIZE.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				TermSuggesterConfigurationKeys.STRING_DISTANCE.getJsonKey())) {

			try {
				termSuggester.setStringDistance(
					getStringDistance(
						configurationJSONObject.getString(
							TermSuggesterConfigurationKeys.STRING_DISTANCE.
								getJsonKey())));
			}
			catch (IllegalArgumentException illegalArgumentException) {
				messages.addMessage(
					new Message.Builder().className(
						getClass().getName()
					).localizationKey(
						"core.error.unknown-string-distance"
					).msg(
						"Unknown string distance"
					).rootObject(
						configurationJSONObject
					).rootProperty(
						TermSuggesterConfigurationKeys.STRING_DISTANCE.
							getJsonKey()
					).severity(
						Severity.ERROR
					).build());

				_log.error(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}

		return Optional.of(termSuggester);
	}

	protected Suggester.StringDistance getStringDistance(String s)
		throws IllegalArgumentException {

		return Suggester.StringDistance.valueOf(StringUtil.toUpperCase(s));
	}

	protected String getValue(
		JSONObject configurationJSONObject, ParameterData parameterData) {

		String text = configurationJSONObject.getString(
			TermSuggesterConfigurationKeys.TEXT.getJsonKey());

		if (Validator.isBlank(text)) {
			text = parameterData.getKeywords();
		}

		return StringUtil.toLowerCase(text);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TermSuggesterTranslator.class);

}