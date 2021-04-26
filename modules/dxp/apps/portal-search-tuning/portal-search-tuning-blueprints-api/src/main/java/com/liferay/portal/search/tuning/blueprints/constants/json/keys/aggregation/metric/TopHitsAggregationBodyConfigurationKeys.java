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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum TopHitsAggregationBodyConfigurationKeys {

	DOCVALUE_FIELDS("docvalue_fields"), EXPLAIN("explain"), FROM("from"),
	HIGHLIGHT("highlight"), SCRIPT_FIELDS("script_fields"), SIZE("size"),
	SOURCE("_source"), TRACK_SCORES("track_scores"), VERSION("version");

	public static final TopHitsAggregationBodyConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<TopHitsAggregationBodyConfigurationKeys> stream = Arrays.stream(
			TopHitsAggregationBodyConfigurationKeys.values());

		return stream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private TopHitsAggregationBodyConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}