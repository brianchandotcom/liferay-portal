/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static void addObjectEntryId(
		long objectActionId, long objectEntryId) {

		Map<Long, Set<Long>> objectEntryIdsMap = getObjectEntryIdsMap();

		Set<Long> objectEntryIds = objectEntryIdsMap.get(objectActionId);

		if (objectEntryIds == null) {
			objectEntryIds = new HashSet<>();

			objectEntryIdsMap.put(objectActionId, objectEntryIds);
		}

		objectEntryIds.add(objectEntryId);
	}

	public static void clearObjectEntryIdsMap() {
		Map<Long, Set<Long>> objectEntryIdsMap = getObjectEntryIdsMap();

		objectEntryIdsMap.clear();
	}

	public static Map<Long, Set<Long>> getObjectEntryIdsMap() {
		return _objectEntryIdsMapThreadLocal.get();
	}

	private static final ThreadLocal<Map<Long, Set<Long>>>
		_objectEntryIdsMapThreadLocal = new CentralizedThreadLocal<>(
			ObjectActionThreadLocal.class.getName() +
				"._objectEntryIdsMapThreadLocal",
			HashMap::new);

}