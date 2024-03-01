/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Leonardo Barros
 */
public class IsIntegerFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "isInteger";

	@Override
	public Boolean apply(Object parameter) {
		return GetterUtil.getInteger(parameter.toString(), Integer.MIN_VALUE) !=
			Integer.MIN_VALUE;
	}

	@Override
	public String getName() {
		return NAME;
	}

}