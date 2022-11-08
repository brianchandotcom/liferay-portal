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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Guilherme Camacho
 */
public class ObjectActionThreadLocal {

	public static void addObjectActionId(long objectActionId) {
		Set<Long> objectActionIds = getObjectActionIds();

		objectActionIds.add(objectActionId);
	}

	public static void clearObjectActionIds() {
		Set<Long> objectActionIds = getObjectActionIds();

		objectActionIds.clear();
	}

	public static Set<Long> getObjectActionIds() {
		return _objectActionIdsThreadLocal.get();
	}

	public static void setObjectActionIds(Set<Long> objectActionIds) {
		_objectActionIdsThreadLocal.set(objectActionIds);
	}

	private static final ThreadLocal<Set<Long>> _objectActionIdsThreadLocal =
		new CentralizedThreadLocal<>(
			ObjectActionThreadLocal.class.getName() +
				"._objectActionIdsThreadLocal",
			HashSet::new);

}