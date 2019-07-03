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

package com.liferay.portal.vulcan.graphql.query;

import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class GraphQLField {

	public GraphQLField(
		String key, Map<String, Object> parameters,
		GraphQLField... graphQLFields) {

		_key = key;
		_parameters = parameters;
		_graphQLFields = graphQLFields;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(_key);

		if (!_parameters.isEmpty()) {
			sb.append("(");
			Set<Map.Entry<String, Object>> entries = _parameters.entrySet();

			for (Map.Entry<String, Object> entry : entries) {
				sb.append(entry.getKey());
				sb.append(":");
				sb.append(entry.getValue());
				sb.append(",");
			}

			sb.append(")");
		}

		if (_graphQLFields.length > 0) {
			sb.append("{");

			for (GraphQLField graphQLField : _graphQLFields) {
				sb.append(graphQLField.toString());
				sb.append(",");
			}

			sb.append("}");
		}

		return sb.toString();
	}

	private final GraphQLField[] _graphQLFields;
	private final String _key;
	private final Map<String, Object> _parameters;

}