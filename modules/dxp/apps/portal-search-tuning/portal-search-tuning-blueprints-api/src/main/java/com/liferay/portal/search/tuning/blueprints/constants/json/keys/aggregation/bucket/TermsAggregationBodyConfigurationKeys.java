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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.bucket;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum TermsAggregationBodyConfigurationKeys {

	AGGS("aggs"), COLLECT_MODE("collect_mode"), EXCLUDE("exclude"),
	EXECUTION_HINT("execution_hint"), FIELD("field"), INCLUDE("include"),
	MIN_DOC_COUNT("min_doc_count"), MISSING("missing"), ORDER("order"),
	SCRIPT("script"), SHARD_MIN_DOC_COUNT("shard_min_doc_count"),
	SHARD_SIZE("shard_size"),
	SHOW_TERM_DOC_COUNT_ERROR("show_term_doc_count_error"), SIZE("size");

	public static final TermsAggregationBodyConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<TermsAggregationBodyConfigurationKeys> stream = Arrays.stream(
			TermsAggregationBodyConfigurationKeys.values());

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

	private TermsAggregationBodyConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}