/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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