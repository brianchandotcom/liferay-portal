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