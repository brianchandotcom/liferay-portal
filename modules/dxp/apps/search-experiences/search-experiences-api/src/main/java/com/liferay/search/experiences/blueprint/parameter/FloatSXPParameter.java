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

/**
 * @author Petteri Karttunen
 */
public class FloatSXPParameter extends BaseSXPParameter {

	public FloatSXPParameter(
		String name, boolean templateVariable, Float value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean evaluateEquals(Object object) {
		if (_value.floatValue() == GetterUtil.getFloat(object)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean evaluateIn(Object[] values) {
		return ArrayUtil.contains(
			GetterUtil.getFloatValues(ArrayUtil.toStringArray(values)), _value);
	}

	@Override
	public boolean evaluateRange(Object gt, Object gte, Object lt, Object lte) {
		if ((gt != null) && (_value <= GetterUtil.getFloat(gt))) {
			return false;
		}

		if ((gte != null) && (_value < GetterUtil.getFloat(gte))) {
			return false;
		}

		if ((lt != null) && (_value >= GetterUtil.getFloat(lt))) {
			return false;
		}

		if ((lte != null) && (_value > GetterUtil.getFloat(lte))) {
			return false;
		}

		return true;
	}

	@Override
	public Float getValue() {
		return _value;
	}

	private final Float _value;

}