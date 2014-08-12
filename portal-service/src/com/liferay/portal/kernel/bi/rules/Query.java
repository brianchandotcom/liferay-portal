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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class Query implements Serializable {

	public static Query createCustomQuery(String identifier, String queryName) {
		if (Validator.isNull(identifier)) {
			throw new IllegalArgumentException("Query idenfier is null.");
		}

		if (Validator.isNull(queryName)) {
			throw new IllegalArgumentException("Query string is null.");
		}

		return new Query(identifier, QueryType.CUSTOM, queryName);
	}

	public static Query createStandardQuery() {
		return new Query(null, QueryType.STANDARD, null);
	}

	public void addArgument(Object object) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		_arguments.add(object);
	}

	public void addArguments(List<?> arguments) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		_arguments.addAll(arguments);
	}

	public void addArguments(Object[] arguments) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		if (ArrayUtil.isNotEmpty(arguments)) {
			_arguments.addAll(Arrays.asList(arguments));
		}
	}

	public Object[] getArguments() {
		return _arguments.toArray(new Object[_arguments.size()]);
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getQueryName() {
		return _queryName;
	}

	public QueryType getQueryType() {
		return _queryType;
	}

	private Query(String identifier, QueryType queryType, String queryName) {
		_identifier = identifier;
		_queryType = queryType;
		_queryName = queryName;
	}

	private final List<Object> _arguments = new ArrayList<Object>();
	private final String _identifier;
	private final String _queryName;
	private final QueryType _queryType;

}