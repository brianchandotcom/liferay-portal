/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata.filter.expression;

import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import java.util.Locale;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	property = "result.class.name=java.util.function.Predicate",
	service = ExpressionConvert.class
)
public class PredicateExpressionConvertImpl
	implements ExpressionConvert<Predicate<?>> {

	@Override
	public Predicate<?> convert(
			Expression expression, Locale locale, EntityModel entityModel)
		throws ExpressionVisitException {

		return (Predicate<?>)expression.accept(
			new PredicateExpressionVisitorImpl(entityModel));
	}

}