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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.advanced;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum HighlightingConfigurationKeys {

	ENABLED("enabled"), FIELD("field"), FIELDS("fields"),
	FRAGMENT_OFFSET("fragment_offset"), FRAGMENT_SIZE("fragment_size"),
	NUMBER_OF_FRAGMENTS("number_of_fragments"),
	REQUIRE_FIELD_MATCH("require_field_match");

	public static final HighlightingConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<HighlightingConfigurationKeys>
			highlightingConfigurationKeysStream = Arrays.stream(
				HighlightingConfigurationKeys.values());

		return highlightingConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private HighlightingConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}