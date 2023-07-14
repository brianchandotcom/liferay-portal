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

package com.liferay.object.scope;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public interface ObjectDefinitionsScoped {

	public static final String ALL_OBJECT_DEFINITIONS =
		"ALL_OBJECT_DEFINITIONS";

	public List<String> getAllowedObjectDefinitionNames();

	public default boolean isAllowedObjectDefinition(
		String objectDefinitionName) {

		List<String> allowedObjectDefinitionNames =
			getAllowedObjectDefinitionNames();

		if (allowedObjectDefinitionNames.contains(ALL_OBJECT_DEFINITIONS) ||
			allowedObjectDefinitionNames.contains(objectDefinitionName)) {

			return true;
		}

		return false;
	}

}