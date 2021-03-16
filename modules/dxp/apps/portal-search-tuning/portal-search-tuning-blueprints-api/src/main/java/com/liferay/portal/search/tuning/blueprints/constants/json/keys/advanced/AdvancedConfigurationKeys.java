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
public enum AdvancedConfigurationKeys {

	ENTRY_CLASS_NAMES("entry_class_names"), HIGHLIGHTING("highlighting"),
	MISSPELLING_SET_IDS("misspelling_set_ids"),
	QUERY_INDEXING("query_indexing"), QUERY_PROCESSING("query_processing"),
	SOURCE("source");

	public static final AdvancedConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<AdvancedConfigurationKeys> advancedConfigurationKeysStream =
			Arrays.stream(AdvancedConfigurationKeys.values());

		return advancedConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private AdvancedConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}