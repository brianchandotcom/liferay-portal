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

	public static void addValidatedObjectEntryId(long objectEntryId) {
		Set<Long> validatedObjectEntryIds = _validatedObjectEntryIds.get();

		validatedObjectEntryIds.add(objectEntryId);
	}

	public static boolean isValidatedObjectEntry(long objectEntryId) {
		Set<Long> validatedObjectEntryIds = _validatedObjectEntryIds.get();

		return validatedObjectEntryIds.contains(objectEntryId);
	}

	private static final ThreadLocal<Set<Long>> _validatedObjectEntryIds =
		new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._validatedObjectEntryIds",
			HashSet::new);

}