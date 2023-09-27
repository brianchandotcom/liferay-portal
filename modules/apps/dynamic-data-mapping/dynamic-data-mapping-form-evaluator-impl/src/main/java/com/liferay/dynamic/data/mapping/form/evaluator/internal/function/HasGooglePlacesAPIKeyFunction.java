/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rodrigo Paulino
 */
public class HasGooglePlacesAPIKeyFunction
	implements DDMExpressionFunction.Function0<Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "hasGooglePlacesAPIKey";

	@Override
	public Boolean apply() {
		return Validator.isNotNull(
			_ddmExpressionParameterAccessor.getGooglePlacesAPIKey());
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}