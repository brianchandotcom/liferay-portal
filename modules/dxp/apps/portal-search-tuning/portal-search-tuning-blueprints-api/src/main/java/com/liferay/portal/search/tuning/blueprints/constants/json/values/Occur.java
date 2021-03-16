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

package com.liferay.portal.search.tuning.blueprints.constants.json.values;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum Occur {

	FILTER("filter"), MUST("must"), MUST_NOT("must_not"), SHOULD("should");

	public static final Occur findByjsonValue(String jsonValue) {
		Stream<Occur> occurStream = Arrays.stream(Occur.values());

		return occurStream.filter(
			value -> value._jsonValue.equals(jsonValue)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getjsonValue() {
		return _jsonValue;
	}

	private Occur(String jsonValue) {
		_jsonValue = jsonValue;
	}

	private final String _jsonValue;

}