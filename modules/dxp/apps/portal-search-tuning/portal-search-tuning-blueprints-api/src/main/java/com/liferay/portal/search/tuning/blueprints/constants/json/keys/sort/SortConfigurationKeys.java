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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.sort;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum SortConfigurationKeys {

	CONFIGURATION("configuration"), DEFAULT("default"), FIELD("field"),
	LABEL("label"), ORDER("order"), PARAMETER_NAME("parameter_name"),
	PARAMETERS("parameters"), TYPE("type");

	public static final SortConfigurationKeys findByJsonKey(String jsonKey) {
		Stream<SortConfigurationKeys> sortConfigurationKeysStream =
			Arrays.stream(SortConfigurationKeys.values());

		return sortConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private SortConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}