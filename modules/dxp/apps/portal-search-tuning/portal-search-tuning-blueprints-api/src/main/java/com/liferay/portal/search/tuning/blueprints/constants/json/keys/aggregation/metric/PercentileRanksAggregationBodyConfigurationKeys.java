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
public enum PercentileRanksAggregationBodyConfigurationKeys {

	FIELD("field"), HDR("hdr"), KEYED("keyed"), MISSING("missing"),
	SCRIPT("script"), TDIGEST("tdigest"), VALUES("values");

	public static final PercentileRanksAggregationBodyConfigurationKeys
		findByJsonKey(String jsonKey) {

		Stream<PercentileRanksAggregationBodyConfigurationKeys> stream =
			Arrays.stream(
				PercentileRanksAggregationBodyConfigurationKeys.values());

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

	private PercentileRanksAggregationBodyConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}