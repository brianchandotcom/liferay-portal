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

package com.liferay.portal.scripting.executor.groovy;

import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;

class GroovyListTypeUtil {

	static Map listCache = new HashMap<String, List>();

	static List<ListType> getListForType(String className){
		if(!listCache.containsKey(className)){
			List<ListType> types = ListTypeServiceUtil.getListTypes(className)
			listCache.put(className, types)
		}
		return listCache.get(className);
	}

	static int getListTypeForName(String className, String name){
		List<ListType> types = getListForType(className);
		int typeId;
		for(int i = 0; i<types.size(); i++){
			if(types.get(i).getName().equalsIgnoreCase(name)){
				typeId = types.get(i).getListTypeId();
				continue;
			}
		}
		return typeId;
	}
}
