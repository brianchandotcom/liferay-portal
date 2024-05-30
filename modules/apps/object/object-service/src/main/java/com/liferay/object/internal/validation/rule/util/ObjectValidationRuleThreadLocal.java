/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class ObjectValidationRuleThreadLocal {

	public static void addObjectEntryId(
		long objectEntryId, long objectValidationRuleId) {

		Map<Long, Set<Long>> objectEntryIdsMap =
			_objectEntryIdsMapThreadLocal.get();

		Set<Long> objectEntryIds = objectEntryIdsMap.computeIfAbsent(
			objectValidationRuleId, key -> new HashSet<>());

		objectEntryIds.add(objectEntryId);
	}

	public static boolean isSkipObjectValidationRuleExecution(
		long objectEntryId, long objectValidationRuleId) {

		Map<Long, Set<Long>> objectEntryIdsMap =
			_objectEntryIdsMapThreadLocal.get();

		Set<Long> objectEntryIds = objectEntryIdsMap.get(
			objectValidationRuleId);

		if ((objectEntryIds != null) &&
			objectEntryIds.contains(objectEntryId)) {

			return true;
		}

		return false;
	}

	private static final ThreadLocal<Map<Long, Set<Long>>>
		_objectEntryIdsMapThreadLocal = new CentralizedThreadLocal<>(
			ObjectValidationRuleThreadLocal.class.getName() +
				"._objectEntryIdsMapThreadLocal",
			HashMap::new);

}