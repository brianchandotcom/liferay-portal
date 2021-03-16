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
public enum QueryIndexingConfigurationKeys {

	BLACKLIST("blacklist"), ENABLED("enabled"),
	HITS_THRESHOLD("hits_threshold"),
	QUERY_INDEX_CONFIGURATION_ID("query_index_configuration_id");

	public static final QueryIndexingConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<QueryIndexingConfigurationKeys>
			queryIndexingConfigurationKeysStream = Arrays.stream(
				QueryIndexingConfigurationKeys.values());

		return queryIndexingConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private QueryIndexingConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}