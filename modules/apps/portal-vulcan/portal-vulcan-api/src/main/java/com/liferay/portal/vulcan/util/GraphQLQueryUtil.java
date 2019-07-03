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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.vulcan.graphql.query.GraphQLField;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class GraphQLQueryUtil {

	public static GraphQLField field(String key, GraphQLField... fields) {
		return new GraphQLField(key, new HashMap<>(), fields);
	}

	public static GraphQLField field(
		String key, Map<String, Object> parameters, GraphQLField... fields) {

		return new GraphQLField(key, parameters, fields);
	}

}