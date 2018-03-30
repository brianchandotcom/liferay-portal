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

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyListTypeUtil {

	static Map listTypesmap = new HashMap<>();

	static List<ListType> getListTypes(String className) {
		if (!listTypesmap.containsKey(className)) {
			List<ListType> listTypes = ListTypeLocalServiceUtil.getListTypes(
				className)

			listTypesmap.put(className, listTypes)
		}

		return listTypesmap.get(className);
	}

	static int getListType(String className, String name) {
		int typeId = 0;

		List<ListType> listTypes = getListTypes(className);

		for (ListType listType : listTypes) {
			if (name.equalsIgnoreCase(listType.getName())) {
				typeId = listType.getListTypeId();

				break;
			}
		}

		return typeId;
	}

}