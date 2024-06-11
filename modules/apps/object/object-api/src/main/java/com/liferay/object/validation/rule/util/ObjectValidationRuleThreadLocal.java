/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.validation.rule.util;

import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Feliphe Marinho
 */
public class ObjectValidationRuleThreadLocal {

	public static void addExecutedObjectValidationRuleId(
		long objectValidationRuleId) {

		Set<Long> executedObjectValidationRuleIds =
			_executedObjectValidationRuleIds.get();

		executedObjectValidationRuleIds.add(objectValidationRuleId);

		_executedObjectValidationRuleIds.set(executedObjectValidationRuleIds);
	}

	public static boolean isExecutedObjectValidationRuleId(
		long objectValidationRuleId) {

		Set<Long> executedObjectValidationRuleIds =
			_executedObjectValidationRuleIds.get();

		return executedObjectValidationRuleIds.contains(objectValidationRuleId);
	}

	private static final ThreadLocal<Set<Long>>
		_executedObjectValidationRuleIds = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._executedObjectValidationRuleIds",
			HashSet::new);

}