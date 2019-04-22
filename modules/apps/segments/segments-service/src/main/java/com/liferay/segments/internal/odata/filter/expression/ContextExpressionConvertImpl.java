/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.internal.odata.filter.expression;

import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.segments.context.Context;

import java.util.Locale;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = "result.class.name=java.util.function.Predicate<Context>",
	service = ExpressionConvert.class
)
public class ContextExpressionConvertImpl
	implements ExpressionConvert<Predicate<Context>> {

	@Override
	public Predicate<Context> convert(
			Expression expression, Locale locale, EntityModel entityModel)
		throws ExpressionVisitException {

		return (Predicate<Context>)expression.accept(
			new ContextExpressionVisitorImpl(entityModel));
	}

}