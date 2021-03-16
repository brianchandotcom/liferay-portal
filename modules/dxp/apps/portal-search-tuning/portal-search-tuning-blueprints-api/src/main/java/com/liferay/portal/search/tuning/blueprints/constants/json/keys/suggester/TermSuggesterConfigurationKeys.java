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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum TermSuggesterConfigurationKeys {

	ANALYZER("analyzer"), FIELD("field"), MAX_EDITS("max_edits"),
	MAX_INSPECTIONS("max_inspections"), MAX_TERM_FREQ("max_term_freq"),
	MIN_DOC_FREQ("min_doc_freq"), MIN_WORD_LENGTH("min_word_length"),
	PREFIX_LENGTH("prefix_length"), SIZE("size"), SORT("sort"),
	STRING_DISTANCE("string_distance"), SUGGEST_MODE("suggest_mode"),
	TEXT("text");

	public static final TermSuggesterConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<TermSuggesterConfigurationKeys>
			termSuggesterConfigurationKeysStream = Arrays.stream(
				TermSuggesterConfigurationKeys.values());

		return termSuggesterConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private TermSuggesterConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}