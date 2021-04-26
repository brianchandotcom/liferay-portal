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
public enum ScriptConfigurationKeys {

	ID("id"), LANG("lang"), OPTIONS("options"), PARAMS("params"),
	SOURCE("source");

	public static final ScriptConfigurationKeys findByJsonKey(String jsonKey) {
		Stream<ScriptConfigurationKeys> stream = Arrays.stream(
			ScriptConfigurationKeys.values());

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

	private ScriptConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}