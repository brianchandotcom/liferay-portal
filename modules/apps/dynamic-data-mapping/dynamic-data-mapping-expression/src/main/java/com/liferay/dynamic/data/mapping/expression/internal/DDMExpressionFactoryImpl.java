/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDMExpressionFactory.class)
public class DDMExpressionFactoryImpl implements DDMExpressionFactory {

	@Override
	public <T> DDMExpression<T> createExpression(
			CreateExpressionRequest createExpressionRequest)
		throws DDMExpressionException {

		DDMExpressionImpl<T> ddmExpressionImpl = new DDMExpressionImpl<>(
			ddmExpressionFunctionRegistry,
			createExpressionRequest.getExpression());

		ddmExpressionImpl.setDDMExpressionActionHandler(
			createExpressionRequest.getDDMExpressionActionHandler());
		ddmExpressionImpl.setDDMExpressionFieldAccessor(
			createExpressionRequest.getDDMExpressionFieldAccessor());
		ddmExpressionImpl.setDDMExpressionObserver(
			createExpressionRequest.getDDMExpressionObserver());
		ddmExpressionImpl.setDDMExpressionParameterAccessor(
			createExpressionRequest.getDDMExpressionParameterAccessor());

		return ddmExpressionImpl;
	}

	@Reference
	protected DDMExpressionFunctionRegistry ddmExpressionFunctionRegistry;

}