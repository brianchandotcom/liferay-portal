/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json.transformer;

import flexjson.JSONContext;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Igor Spasic
 */
public class SortedHashMapJSONTransformer extends BaseJSONTransformer {

	@Override
	public void transform(Object object) {
		if (object instanceof HashMap) {
			HashMap hashMap = (HashMap)object;

			TreeMap treeMap = new TreeMap();

			treeMap.putAll(hashMap);

			object = treeMap;
		}

		JSONContext jsonContext = getContext();

		jsonContext.transform(object);
	}

}