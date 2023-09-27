/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.rules.engine;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class Query implements Serializable {

	public static Query createCustomQuery(String identifier, String queryName) {
		if (Validator.isNull(identifier)) {
			throw new IllegalArgumentException("Query idenfier is null");
		}

		if (Validator.isNull(queryName)) {
			throw new IllegalArgumentException("Query string is null");
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
			Collections.addAll(_arguments, arguments);
		}
	}

	public Object[] getArguments() {
		return _arguments.toArray(new Object[0]);
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

	private final List<Object> _arguments = new ArrayList<>();
	private final String _identifier;
	private final String _queryName;
	private final QueryType _queryType;

}