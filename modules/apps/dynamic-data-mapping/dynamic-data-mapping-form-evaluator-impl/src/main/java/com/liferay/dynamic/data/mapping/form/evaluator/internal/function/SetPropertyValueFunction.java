/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;

/**
 * @author Carolina Barbosa
 */
public class SetPropertyValueFunction
	implements DDMExpressionFunction.Function3<String, String, Object, Boolean>,
			   DDMExpressionObserverAware {

	public static final String NAME = "setPropertyValue";

	@Override
	public Boolean apply(String field, String property, Object value) {
		if ((_ddmExpressionObserver == null) || (value == null)) {
			return false;
		}

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, property, value);

		_ddmExpressionObserver.updateFieldProperty(builder.build());

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionObserver(
		DDMExpressionObserver ddmExpressionObserver) {

		_ddmExpressionObserver = ddmExpressionObserver;
	}

	private DDMExpressionObserver _ddmExpressionObserver;

}