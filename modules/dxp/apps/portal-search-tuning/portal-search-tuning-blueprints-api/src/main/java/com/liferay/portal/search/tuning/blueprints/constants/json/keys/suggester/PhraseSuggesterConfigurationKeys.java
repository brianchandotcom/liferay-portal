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
public enum PhraseSuggesterConfigurationKeys {

	ANALYZER("analyzer"), COLLATE("collate"), CONFIDENCE("confidence"),
	DESCRIPTION("description"), DIRECT_GENERATOR("direct_generator"),
	FIELD("field"), FORCE_UNIGRAMS("force_unigrams"), GRAM_SIZE("gram_size"),
	MAX_ERRORS("max_errors"), POST_HIGHLIGHT_TAG("post_highlight_tag"),
	PRE_HIGHLIGHT_TAG("pre_highlight_tag"), PRUNE("prune"),
	REAL_WORLD_ERROR_LIKELIHOOD("real_word_error_likelihood"),
	SEPARATOR("separator"), SHARD_SIZE("shard_size"), SIZE("size"),
	TEXT("text");

	public static final PhraseSuggesterConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<PhraseSuggesterConfigurationKeys>
			phraseSuggesterConfigurationKeysStream = Arrays.stream(
				PhraseSuggesterConfigurationKeys.values());

		return phraseSuggesterConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private PhraseSuggesterConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}