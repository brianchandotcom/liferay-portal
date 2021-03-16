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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum BlueprintKeys {

	ADVANCED_CONFIGURATION("advanced_configuration"),
	AGGREGATION_CONFIGURATION("aggregation_configuration"),
	FRAMEWORK_CONFIGURATION("framework_configuration"),
	PARAMETER_CONFIGURATION("parameter_configuration"),
	QUERY_CONFIGURATION("query_configuration"),
	SORT_CONFIGURATION("sort_configuration"),
	SUGGEST_CONFIGURATION("suggest_configuration");

	public static final BlueprintKeys findByJsonKey(String jsonKey) {
		Stream<BlueprintKeys> blueprintKeysStream = Arrays.stream(
			BlueprintKeys.values());

		return blueprintKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private BlueprintKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}