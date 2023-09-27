/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.helper.DDMFormEvaluatorHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 * @author Leonardo Barros
 */
@Component(service = DDMFormEvaluator.class)
public class DDMFormEvaluatorImpl implements DDMFormEvaluator {

	@Override
	public DDMFormEvaluatorEvaluateResponse evaluate(
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest) {

		DDMFormEvaluatorHelper formEvaluatorHelper = new DDMFormEvaluatorHelper(
			ddmExpressionFactory, ddmFormEvaluatorEvaluateRequest,
			ddmFormFieldTypeServicesRegistry, ddmFormPageChangeRegistry);

		return formEvaluatorHelper.evaluate();
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	@Reference
	protected DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry;

	@Reference
	protected DDMFormPageChangeRegistry ddmFormPageChangeRegistry;

}