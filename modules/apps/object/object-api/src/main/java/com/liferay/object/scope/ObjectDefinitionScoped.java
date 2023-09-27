/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.scope;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public interface ObjectDefinitionScoped {

	public List<String> getAllowedObjectDefinitionNames();

	public default boolean isAllowedObjectDefinition(
		String objectDefinitionName) {

		List<String> allowedObjectDefinitionNames =
			getAllowedObjectDefinitionNames();

		if (allowedObjectDefinitionNames.isEmpty()) {
			return true;
		}

		return allowedObjectDefinitionNames.contains(objectDefinitionName);
	}

}