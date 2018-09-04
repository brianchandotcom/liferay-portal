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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.structured.content.apio.architect.filter.ExpressionParser;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.structured.content.apio.internal.architect.filter.expression.ODataExpressionToExpressionVisitor;

import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.core.Encoder;
import org.apache.olingo.commons.core.edm.EdmProviderImpl;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.core.uri.parser.Parser;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * <code>ExpressionParserImpl</code> transforms a String containing an oData filter
 * in a manageable expression {@link Expression}.
 *
 * @author David Arques
 * @review
 */
@Component(immediate = true, service = ExpressionParser.class)
public class ExpressionParserImpl implements ExpressionParser {

	@Override
	public Expression parse(String expressionString)
		throws ExpressionVisitException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Parsing the expression '%s'", expressionString));
		}

		if (Validator.isNull(expressionString)) {
			throw new InvalidFilterException("Expression is empty");
		}

		UriInfo uriInfo = _getUriInfo(expressionString);

		FilterOption filterOption = uriInfo.getFilterOption();

		org.apache.olingo.server.api.uri.queryoption.expression.Expression
			expression = filterOption.getExpression();

		try {
			return expression.accept(new ODataExpressionToExpressionVisitor());
		}
		catch (Exception e) {
			throw new ExpressionVisitException(e.getMessage(), e);
		}
	}

	@Activate
	protected void activate() {
		_singleEntitySchemaBaseProvider = new StructuredContentEdmProvider();

		Edm edm = new EdmProviderImpl(_singleEntitySchemaBaseProvider);

		_parser = new Parser(edm, OData.newInstance());
	}

	private UriInfo _getUriInfo(String expressionString) {
		String encodedFilter =
			_FILTER_EXPRESSION_PREFIX + Encoder.encode(expressionString);

		try {
			return _parser.parseUri(
				_singleEntitySchemaBaseProvider.getSingleEntityTypeName(),
				encodedFilter, null, null);
		}
		catch (ODataException ode) {
			throw new InvalidFilterException(
				String.format(
					"Invalid query computed from expression '%s': '%s'",
					expressionString, ode.getMessage()),
				ode);
		}
	}

	private static final String _FILTER_EXPRESSION_PREFIX = "$filter=";

	private static final Log _log = LogFactoryUtil.getLog(
		ExpressionParserImpl.class);

	private Parser _parser;
	private SingleEntitySchemaBaseProvider _singleEntitySchemaBaseProvider;

}