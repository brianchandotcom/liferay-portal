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

package com.liferay.headless.batch.engine.internal.util;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ivica Cardic
 */
public class ParametersUtil {

	public static Map<String, Serializable> toParameters(
		UriInfo contextUriInfo, Set<String> ignoredParameters) {

		Map<String, Serializable> parameters = new HashMap<>();

		MultivaluedMap<String, String> queryParameters =
			contextUriInfo.getQueryParameters();

		for (Map.Entry<String, List<String>> entry :
				queryParameters.entrySet()) {

			String key = entry.getKey();

			if (ignoredParameters.contains(key)) {
				continue;
			}

			List<String> values = entry.getValue();

			if (!values.isEmpty()) {
				parameters.put(key, values.get(0));
			}
		}

		return parameters;
	}

}