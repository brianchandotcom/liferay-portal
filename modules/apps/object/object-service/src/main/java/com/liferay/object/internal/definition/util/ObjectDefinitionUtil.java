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

package com.liferay.object.internal.definition.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public class ObjectDefinitionUtil {

	public static String getModifiableSystemObjectDefinitionRESTContextPath(
		String name) {

		return _allowedModifiableSystemObjectDefinitionNames.get(name);
	}

	public static boolean isAllowedModifiableSystemObjectDefinitionName(
		String name) {

		return _allowedModifiableSystemObjectDefinitionNames.containsKey(name);
	}

	private static final Map<String, String>
		_allowedModifiableSystemObjectDefinitionNames = HashMapBuilder.put(
			"APIApplication", "/headless-builder/applications"
		).put(
			"APIEndpoint", "/headless-builder/endpoints"
		).put(
			"APIFilter", "/headless-builder/filters"
		).put(
			"APIProperty", "/headless-builder/properties"
		).put(
			"APISchema", "/headless-builder/schemas"
		).put(
			"APISort", "/headless-builder/sorts"
		).build();

}