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

package com.liferay.search.experiences.blueprint.parameter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public class StringSXPParameter extends BaseSXPParameter {

	public StringSXPParameter(
		String name, boolean templateVariable, String value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean evaluateEquals(Object object) {
		return Objects.equals(_value, GetterUtil.getString(object));
	}

	@Override
	public boolean evaluateIn(Object[] values) {
		return ArrayUtil.contains(
			GetterUtil.getStringValues(ArrayUtil.toStringArray(values)),
			_value);
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final String _value;

}