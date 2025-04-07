/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public class ObjectEntryThreadLocal {

	public static void addValidatedObjectEntryId(long objectEntryId) {
		Set<Long> validatedObjectEntryIds = _validatedObjectEntryIds.get();

		validatedObjectEntryIds.add(objectEntryId);
	}

	public static void clearExpandoValues() {
		_expandoValues.remove();
	}

	public static Map<String, Serializable> getExpandoValues() {
		return _expandoValues.get();
	}

	public static boolean isDisassociateRelatedModels() {
		return _disassociateRelatedModels.get();
	}

	public static boolean isSkipObjectEntryResourcePermission() {
		return _skipObjectEntryResourcePermission.get();
	}

	public static boolean isSkipObjectValidationRules() {
		return _skipObjectValidationRules.get();
	}

	public static boolean isSkipReadOnlyObjectFieldsValidation() {
		return _skipReadOnlyObjectFieldsValidation.get();
	}

	public static boolean isValidatedObjectEntry(long objectEntryId) {
		Set<Long> validatedObjectEntryIds = _validatedObjectEntryIds.get();

		return validatedObjectEntryIds.contains(objectEntryId);
	}

	public static SafeCloseable setDisassociateRelatedModelsWithSafeCloseable(
		boolean disassociateRelatedModels) {

		return _disassociateRelatedModels.setWithSafeCloseable(
			disassociateRelatedModels);
	}

	public static void setExpandoValues(
		Map<String, Serializable> expandoValues) {

		_expandoValues.set(expandoValues);
	}

	public static void setSkipObjectEntryResourcePermission(
		boolean skipObjectEntryResourcePermission) {

		_skipObjectEntryResourcePermission.set(
			skipObjectEntryResourcePermission);
	}

	public static void setSkipObjectValidationRules(
		boolean skipObjectValidationRules) {

		_skipObjectValidationRules.set(skipObjectValidationRules);
	}

	public static void setSkipReadOnlyObjectFieldsValidation(
		boolean skipReadOnlyValidation) {

		_skipReadOnlyObjectFieldsValidation.set(skipReadOnlyValidation);
	}

	private static final CentralizedThreadLocal<Boolean>
		_disassociateRelatedModels = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._disassociateRelatedModels",
			() -> false);
	private static final ThreadLocal<Map<String, Serializable>> _expandoValues =
		new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._expandoValues");
	private static final ThreadLocal<Boolean>
		_skipObjectEntryResourcePermission = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class +
				"._skipObjectEntryResourcePermission",
			() -> false);
	private static final ThreadLocal<Boolean> _skipObjectValidationRules =
		new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._skipObjectValidationRules",
			() -> false);
	private static final ThreadLocal<Boolean>
		_skipReadOnlyObjectFieldsValidation = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class +
				"._skipReadOnlyObjectFieldsValidation",
			() -> false);
	private static final ThreadLocal<Set<Long>> _validatedObjectEntryIds =
		new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._validatedObjectEntryIds",
			HashSet::new);

}