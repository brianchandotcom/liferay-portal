/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ObjectEntryFieldNameUtil {

	public static String getObjectRelationshipIdFieldName(
		String objectRelationshipName,
		String parentObjectDefinitionPKFieldName) {

		return StringBundler.concat(
			"r_", objectRelationshipName, "_",
			parentObjectDefinitionPKFieldName);
	}

}