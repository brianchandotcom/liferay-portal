/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * @author Pedro Leite
 */
public class KaleoDefinitionResourcePermissionThreadLocal {

	public static <T> T getWithoutKaleoDefinitionResourcePermission(
			UnsafeSupplier<T, WorkflowException> unsafeSupplier)
		throws WorkflowException {

		try (SafeCloseable safeCloseable =
				_enabledThreadLocal.setWithSafeCloseable(false)) {

			return unsafeSupplier.get();
		}
	}

	public static boolean isDisabled() {
		return !_enabledThreadLocal.get();
	}

	private static final CentralizedThreadLocal<Boolean> _enabledThreadLocal =
		new CentralizedThreadLocal<>(
			KaleoDefinitionResourcePermissionThreadLocal.class + "._enabled",
			() -> Boolean.TRUE);

}