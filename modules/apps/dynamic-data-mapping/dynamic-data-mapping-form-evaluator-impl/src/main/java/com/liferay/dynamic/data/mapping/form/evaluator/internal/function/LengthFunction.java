/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

/**
 * @author Rodrigo Paulino
 */
public class LengthFunction
	implements DDMExpressionFunction.Function1<String, Integer> {

	public static final String NAME = "length";

	@Override
	public Integer apply(String text) {
		if (text == null) {
			return 0;
		}

		return text.length();
	}

	@Override
	public String getName() {
		return NAME;
	}

}