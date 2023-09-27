/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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