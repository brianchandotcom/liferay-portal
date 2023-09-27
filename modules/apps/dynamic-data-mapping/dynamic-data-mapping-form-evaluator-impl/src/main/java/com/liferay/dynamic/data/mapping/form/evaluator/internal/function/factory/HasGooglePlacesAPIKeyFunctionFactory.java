/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function.factory;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.HasGooglePlacesAPIKeyFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "name=" + HasGooglePlacesAPIKeyFunction.NAME,
	service = DDMExpressionFunctionFactory.class
)
public class HasGooglePlacesAPIKeyFunctionFactory
	implements DDMExpressionFunctionFactory {

	@Override
	public DDMExpressionFunction create() {
		return _HAS_GOOGLE_PLACES_API_KEY_FUNCTION;
	}

	private static final HasGooglePlacesAPIKeyFunction
		_HAS_GOOGLE_PLACES_API_KEY_FUNCTION =
			new HasGooglePlacesAPIKeyFunction();

}