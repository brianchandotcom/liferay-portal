/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class ObjectFieldBusinessTypeUtil {

	public static List<Map<String, String>> getObjectFieldBusinessTypeMaps(
		Locale locale, List<ObjectFieldBusinessType> objectFieldBusinessTypes) {

		List<Map<String, String>> objectFieldBusinessTypeMaps =
			new ArrayList<>();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				objectFieldBusinessTypes) {

			objectFieldBusinessTypeMaps.add(
				HashMapBuilder.put(
					"businessType", objectFieldBusinessType.getName()
				).put(
					"dbType", objectFieldBusinessType.getDBType()
				).put(
					"description",
					objectFieldBusinessType.getDescription(locale)
				).put(
					"label", objectFieldBusinessType.getLabel(locale)
				).build());
		}

		return objectFieldBusinessTypeMaps;
	}

}