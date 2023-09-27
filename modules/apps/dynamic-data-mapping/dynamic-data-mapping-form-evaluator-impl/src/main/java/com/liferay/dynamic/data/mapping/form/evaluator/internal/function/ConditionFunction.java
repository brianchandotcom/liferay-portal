/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

/**
 * @author Selton Guedes
 */
public class ConditionFunction
	implements DDMExpressionFunction.Function3
		<Boolean, Object, Object, Object> {

	public static final String NAME = "condition";

	@Override
	public Object apply(Boolean condition, Object object1, Object object2) {
		if (condition) {
			return object1;
		}

		return object2;
	}

	@Override
	public String getName() {
		return NAME;
	}

}