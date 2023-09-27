/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Carolina Barbosa
 */
public class DateValidationFunction
	implements DDMExpressionFunction.Function1<String, Boolean> {

	@Override
	public Boolean apply(String parameter) {
		return StringUtil.equals(parameter, "{startsFrom: responseDate}");
	}

	@Override
	public String getName() {
		return "dateValidation";
	}

}