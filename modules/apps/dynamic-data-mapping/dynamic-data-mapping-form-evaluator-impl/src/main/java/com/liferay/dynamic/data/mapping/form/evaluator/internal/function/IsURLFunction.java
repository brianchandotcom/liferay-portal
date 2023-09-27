/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class IsURLFunction
	implements DDMExpressionFunction.Function1<Object, Boolean>,
			   DDMExpressionFunction.Function2<Object, Boolean, Boolean> {

	public static final String NAME = "isURL";

	@Override
	public Boolean apply(Object parameter) {
		if ((parameter == null) ||
			Objects.equals(parameter.toString(), Http.HTTP_WITH_SLASH) ||
			Objects.equals(parameter.toString(), Http.HTTPS_WITH_SLASH)) {

			return false;
		}

		return Validator.isUrl(parameter.toString());
	}

	@Override
	public Boolean apply(Object parameter, Boolean acceptRootRelative) {
		if ((parameter == null) ||
			Objects.equals(parameter.toString(), Http.HTTP_WITH_SLASH) ||
			Objects.equals(parameter.toString(), Http.HTTPS_WITH_SLASH)) {

			return false;
		}

		return Validator.isUrl(parameter.toString(), acceptRootRelative);
	}

	@Override
	public String getName() {
		return NAME;
	}

}