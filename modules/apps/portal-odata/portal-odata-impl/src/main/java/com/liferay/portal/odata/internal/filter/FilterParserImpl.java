/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.factory.ExpressionFactory;
import com.liferay.portal.odata.internal.filter.expression.ExpressionVisitorImpl;

import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.core.Encoder;
import org.apache.olingo.commons.core.edm.EdmProviderImpl;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.core.uri.parser.Parser;
import org.apache.olingo.server.core.uri.parser.UriParserSemanticException;

/**
 * Transforms a string containing an OData filter into a manageable {@code
 * Expression}.
 *
 * @author David Arques
 */
public class FilterParserImpl implements FilterParser {

	public FilterParserImpl(
		EntityModel entityModel, ExpressionFactory expressionFactory) {

		_expressionFactory = expressionFactory;

		_parser = new Parser(
			new EdmProviderImpl(
				new EntityModelSchemaBasedEdmProvider(entityModel)),
			OData.newInstance());
		_path = entityModel.getName();
	}

	@Override
	public Expression parse(String filterString)
		throws ExpressionVisitException {

		if (_log.isDebugEnabled()) {
			_log.debug("Parsing filter: " + filterString);
		}

		if (Validator.isNull(filterString)) {
			throw new ExpressionVisitException("Filter is null");
		}

		UriInfo uriInfo = _getUriInfo(filterString);

		FilterOption filterOption = uriInfo.getFilterOption();

		org.apache.olingo.server.api.uri.queryoption.expression.Expression
			expression = filterOption.getExpression();

		try {
			return expression.accept(
				new ExpressionVisitorImpl(_expressionFactory));
		}
		catch (Exception exception) {
			throw new ExpressionVisitException(
				exception.getMessage(), exception);
		}
	}

	private UriInfo _getUriInfo(String filterString)
		throws ExpressionVisitException {

		try {
			return _parser.parseUri(
				_path, "$filter=" + Encoder.encode(filterString), null, null);
		}
		catch (UriParserSemanticException uriParserSemanticException) {
			String message = uriParserSemanticException.getMessage();

			if (UriParserSemanticException.MessageKeys.
					EXPRESSION_PROPERTY_NOT_IN_TYPE.equals(
						uriParserSemanticException.getMessageKey())) {

				message =
					"A property used in the filter criteria is not " +
						"supported: " + filterString;
			}

			throw new ExpressionVisitException(
				message, uriParserSemanticException);
		}
		catch (ODataException oDataException) {
			throw new ExpressionVisitException(
				oDataException.getMessage(), oDataException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterParserImpl.class);

	private final ExpressionFactory _expressionFactory;
	private final Parser _parser;
	private final String _path;

}