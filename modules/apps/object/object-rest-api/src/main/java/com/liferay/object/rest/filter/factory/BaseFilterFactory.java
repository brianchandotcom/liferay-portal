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

package com.liferay.object.rest.filter.factory;

import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
public abstract class BaseFilterFactory {

	protected Expression getExpression(
			EntityModel entityModel, String filterString)
		throws ExpressionVisitException {

		FilterParser filterParser = filterParserProvider.provide(entityModel);

		Filter oDataFilter = new Filter(filterParser.parse(filterString));

		return oDataFilter.getExpression();
	}

	@Reference
	protected FilterParserProvider filterParserProvider;

}