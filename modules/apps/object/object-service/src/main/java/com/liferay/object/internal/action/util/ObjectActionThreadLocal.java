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

package com.liferay.object.internal.action.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Guilherme Camacho
 */
public class ObjectActionThreadLocal {

	public static void addObjectActionId(
		long objectActionId, long objectEntryId) {

		Map<Long, Set<Long>> objectActionIds = getObjectActionIds();

		Set<Long> objectEntryIds = objectActionIds.get(objectActionId);

		if (objectEntryIds == null) {
			objectEntryIds = new HashSet<>();

			objectActionIds.put(objectActionId, objectEntryIds);
		}

		objectEntryIds.add(objectEntryId);
	}

	public static void clearObjectActionIds() {
		Map<Long, Set<Long>> objectActionIds = getObjectActionIds();

		objectActionIds.clear();
	}

	public static Map<Long, Set<Long>> getObjectActionIds() {
		return _objectActionIdsThreadLocal.get();
	}

	public static void setObjectActionIds(
		Map<Long, Set<Long>> objectActionIds) {

		_objectActionIdsThreadLocal.set(objectActionIds);
	}

	private static final ThreadLocal<Map<Long, Set<Long>>>
		_objectActionIdsThreadLocal = new CentralizedThreadLocal<>(
			ObjectActionThreadLocal.class.getName() +
				"._objectActionIdsThreadLocal",
			HashMap::new);

}