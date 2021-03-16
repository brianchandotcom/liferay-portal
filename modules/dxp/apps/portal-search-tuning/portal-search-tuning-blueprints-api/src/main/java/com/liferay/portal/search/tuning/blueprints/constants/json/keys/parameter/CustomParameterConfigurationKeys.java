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

package com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum CustomParameterConfigurationKeys {

	DATE_FORMAT("date_format"), DEFAULT("default"), MAX_VALUE("max_value"),
	MIN_VALUE("min_value"), PARAMETER_NAME("parameter_name"), TYPE("type");

	public static final CustomParameterConfigurationKeys findByJsonKey(
		String jsonKey) {

		Stream<CustomParameterConfigurationKeys>
			customRequestParameterConfigurationKeysStream = Arrays.stream(
				CustomParameterConfigurationKeys.values());

		return customRequestParameterConfigurationKeysStream.filter(
			value -> value._jsonKey.equals(jsonKey)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getJsonKey() {
		return _jsonKey;
	}

	private CustomParameterConfigurationKeys(String jsonKey) {
		_jsonKey = jsonKey;
	}

	private final String _jsonKey;

}