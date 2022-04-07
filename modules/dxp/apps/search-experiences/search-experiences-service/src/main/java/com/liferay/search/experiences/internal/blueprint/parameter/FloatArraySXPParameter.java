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

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class FloatArraySXPParameter extends BaseSXPParameter {

	public FloatArraySXPParameter(
		String name, boolean templateVariable, Float[] value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean evaluateContains(Object value) {
		if (value instanceof Object[]) {
			for (Object object : (Object[])value) {
				if (ArrayUtil.contains(_value, GetterUtil.getFloat(object))) {
					return true;
				}
			}

			return false;
		}

		return ArrayUtil.contains(_value, GetterUtil.getFloat(value));
	}

	@Override
	public String evaluateToString(Map<String, String> options) {
		return Arrays.toString(_value);
	}

	@Override
	public Float[] getValue() {
		return _value;
	}

	private final Float[] _value;

}