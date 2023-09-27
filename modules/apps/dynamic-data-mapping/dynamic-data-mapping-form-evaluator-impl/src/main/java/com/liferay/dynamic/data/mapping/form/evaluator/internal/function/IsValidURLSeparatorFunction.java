/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Mahmoud Azzam
 */
public class IsValidURLSeparatorFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "isValidURLSeparator";

	@Override
	public Boolean apply(Object parameter) {
		if (Objects.equals(parameter.toString(), "-") ||
			Objects.equals(parameter.toString(), "~") ||
			Objects.equals(parameter.toString(), "b") ||
			Objects.equals(parameter.toString(), "d") ||
			Objects.equals(parameter.toString(), "w")) {

			return Boolean.FALSE;
		}

		for (char c :
				parameter.toString(
				).toCharArray()) {

			if (!Validator.isAscii(c)) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;
	}

	@Override
	public String getName() {
		return NAME;
	}

}