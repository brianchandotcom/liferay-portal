/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Leonardo Barros
 */
public class IsEmailAddressFunction
	implements DDMExpressionFunction.Function1<String, Boolean> {

	public static final String NAME = "isEmailAddress";

	@Override
	public Boolean apply(String parameter) {
		for (String string : StringUtil.split(parameter, CharPool.COMMA)) {
			if (!Validator.isEmailAddress(string.trim())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

}