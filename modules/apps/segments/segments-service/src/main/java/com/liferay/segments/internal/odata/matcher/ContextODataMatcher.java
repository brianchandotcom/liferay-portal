/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata.matcher;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.segments.context.Context;
import com.liferay.segments.internal.odata.entity.ContextEntityModel;
import com.liferay.segments.odata.matcher.ODataMatcher;

import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "target.class.name=com.liferay.segments.context.Context",
	service = ODataMatcher.class
)
public class ContextODataMatcher implements ODataMatcher<Context> {

	@Override
	public boolean matches(String filterString, Context pattern)
		throws PortalException {

		try {
			Predicate<Context> predicate = _getPredicate(filterString);

			return predicate.test(pattern);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to match filter: " + exception.getMessage(), exception);
		}
	}

	private Predicate<Context> _getPredicate(String filterString)
		throws Exception {

		FilterParser filterParser = _filterParserProvider.provide(_entityModel);

		Filter filter = new Filter(filterParser.parse(filterString));

		try {
			return _expressionConvert.convert(
				filter.getExpression(), LocaleUtil.getDefault(), _entityModel);
		}
		catch (Exception exception) {
			throw new InvalidFilterException(
				"Invalid filter: " + exception.getMessage(), exception);
		}
	}

	@Reference(target = "(entity.model.name=" + ContextEntityModel.NAME + ")")
	private EntityModel _entityModel;

	@Reference(target = "(result.class.name=java.util.function.Predicate)")
	private ExpressionConvert<Predicate<Context>> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

}