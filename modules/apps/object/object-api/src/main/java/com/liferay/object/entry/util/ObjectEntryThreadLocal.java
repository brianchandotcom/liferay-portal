/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Marcela Cunha
 */
public class ObjectEntryThreadLocal {

	public static boolean isDisassociateRelatedModels() {
		return _disassociateRelatedModelsThreadLocal.get();
	}

	public static boolean isSkipObjectEntryResourcePermission() {
		return _skipObjectEntryResourcePermissionThreadLocal.get();
	}

	public static boolean isSkipObjectValidationRules() {
		return _skipObjectValidationRules.get();
	}

	public static boolean isSkipReadOnlyObjectFieldsValidation() {
		return _skipReadOnlyObjectFieldsValidationThreadLocal.get();
	}

	public static void setDisassociateRelatedModels(
		boolean disassociateRelatedModels) {

		_disassociateRelatedModelsThreadLocal.set(disassociateRelatedModels);
	}

	public static void setSkipObjectEntryResourcePermission(
		boolean skipObjectEntryResourcePermission) {

		_skipObjectEntryResourcePermissionThreadLocal.set(
			skipObjectEntryResourcePermission);
	}

	public static void setSkipObjectValidationRules(
		boolean skipObjectValidationRules) {

		_skipObjectValidationRules.set(skipObjectValidationRules);
	}

	public static void setSkipReadOnlyObjectFieldsValidation(
		boolean skipReadOnlyValidation) {

		_skipReadOnlyObjectFieldsValidationThreadLocal.set(
			skipReadOnlyValidation);
	}

	private static final ThreadLocal<Boolean>
		_disassociateRelatedModelsThreadLocal = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class +
				"._disassociateRelatedModelsThreadLocal",
			() -> false);
	private static final ThreadLocal<Boolean>
		_skipObjectEntryResourcePermissionThreadLocal =
			new CentralizedThreadLocal<>(
				ObjectEntryThreadLocal.class +
					"._skipObjectEntryResourcePermissionThreadLocal",
				() -> false);
	private static final ThreadLocal<Boolean> _skipObjectValidationRules =
		new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class + "._skipObjectValidationRules",
			() -> false);
	private static final ThreadLocal<Boolean>
		_skipReadOnlyObjectFieldsValidationThreadLocal =
			new CentralizedThreadLocal<>(
				ObjectEntryThreadLocal.class +
					"._skipReadOnlyObjectFieldsValidationThreadLocal",
				() -> false);

}