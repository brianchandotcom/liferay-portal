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
 * @author Leonardo Barros
 */
public abstract class BaseSetPropertyFunction<V>
	implements DDMExpressionFunction.Function2<String, V, Boolean>,
			   DDMExpressionObserverAware {

	@Override
	public Boolean apply(String field, V value) {
		if (_ddmExpressionObserver == null) {
			return false;
		}

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, getPropertyName(), value);

		_ddmExpressionObserver.updateFieldProperty(builder.build());

		return true;
	}

	@Override
	public void setDDMExpressionObserver(
		DDMExpressionObserver ddmExpressionObserver) {

		_ddmExpressionObserver = ddmExpressionObserver;
	}

	protected abstract String getPropertyName();

	private DDMExpressionObserver _ddmExpressionObserver;

}