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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.query;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum SimpleQueryStringConfigurationKeys {

	ANALYZE_WILDCARD("analyze_wildcard"), ANALYZER("analyzer"),
	AUTO_GENERATE_SYNONYMS_PHRASE_QUERY("auto_generate_synonyms_phrase_query"),
	BOOST("boost"), DEFAULT_OPERATOR("default_operator"),
	ENABLE_POSITION_INCREMENTS("enable_position_increments"), FIELD("field"),
	FIELDS("fields"), FUZZINESS("fuzziness"),
	FUZZY_MAX_EXPANSIONS("fuzzy_max_expansions"),
	FUZZY_PREFIX_LENGTH("fuzzy_prefix_length"),
	FUZZY_TRANSPOSITIONS("fuzzy_transpositions"), LENIENT("lenient"),
	QUERY("query"), QUOTE_FIELD_SUFFIX("quote_field_suffix");

	public static final SimpleQueryStringConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<SimpleQueryStringConfigurationKeys>
			simpleQueryStringConfigurationKeysStream = Arrays.stream(
				SimpleQueryStringConfigurationKeys.values());

		return simpleQueryStringConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private SimpleQueryStringConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}