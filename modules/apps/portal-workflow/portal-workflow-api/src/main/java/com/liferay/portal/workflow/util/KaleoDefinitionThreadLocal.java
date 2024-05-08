/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Pedro Leite
 */
public class KaleoDefinitionThreadLocal {

	public static boolean isSkipKaleoDefinitionResourcePermission() {
		return _skipKaleoDefinitionResourcePermissionThreadLocal.get();
	}

	public static SafeCloseable
		setSkipKaleoDefinitionResourcePermissionWithSafeCloseable(
			boolean skipKaleoDefinitionResourcePermission) {

		return _skipKaleoDefinitionResourcePermissionThreadLocal.
			setWithSafeCloseable(skipKaleoDefinitionResourcePermission);
	}

	private static final CentralizedThreadLocal<Boolean>
		_skipKaleoDefinitionResourcePermissionThreadLocal =
			new CentralizedThreadLocal<>(
				KaleoDefinitionThreadLocal.class +
					"._skipKaleoDefinitionResourcePermissionThreadLocal",
				() -> Boolean.FALSE);

}