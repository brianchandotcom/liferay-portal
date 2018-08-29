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
import com.liferay.structured.content.apio.architect.filter.FilterParser;

import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.core.Encoder;
import org.apache.olingo.commons.core.edm.EdmProviderImpl;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.core.uri.parser.Parser;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * <code>FilterParser</code> transforms a sequence of characters in an manageable
 * OData {@link Expression}.
 *
 * @author David Arques
 * @review
 */
@Component(immediate = true, service = FilterParser.class)
public class FilterParserImpl implements FilterParser {

	@Override
	public Expression parse(String rawFilter) {
		if (_log.isDebugEnabled()) {
			_log.debug(String.format("Parsing the filter '%s'", rawFilter));
		}

		if (Validator.isNull(rawFilter)) {
			throw new InvalidFilterException("Filter is empty");
		}

		UriInfo uriInfo = _getUriInfo(rawFilter);

		FilterOption filterOption = uriInfo.getFilterOption();

		return filterOption.getExpression();
	}

	@Activate
	protected void activate() {
		_singleEntitySchemaBaseProvider = new StructuredContentEdmProvider();

		Edm edm = new EdmProviderImpl(_singleEntitySchemaBaseProvider);

		_parser = new Parser(edm, OData.newInstance());
	}

	private UriInfo _getUriInfo(String filter) {
		String encodedFilter =
			_FILTER_EXPRESSION_PREFIX + Encoder.encode(filter);

		try {
			return _parser.parseUri(
				_singleEntitySchemaBaseProvider.getSingleEntityTypeName(),
				encodedFilter, null, null);
		}
		catch (ODataException ode) {
			throw new InvalidFilterException(
				String.format(
					"Invalid query computed from filter '%s': '%s'", filter,
					ode.getMessage()),
				ode);
		}
	}

	private static final String _FILTER_EXPRESSION_PREFIX = "$filter=";

	private static final Log _log = LogFactoryUtil.getLog(
		FilterParserImpl.class);

	private Parser _parser;
	private SingleEntitySchemaBaseProvider _singleEntitySchemaBaseProvider;

}