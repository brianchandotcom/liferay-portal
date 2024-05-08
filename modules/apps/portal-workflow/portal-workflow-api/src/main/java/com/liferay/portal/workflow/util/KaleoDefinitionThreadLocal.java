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

	public static boolean isSkipKaleoDefinitionResourcePermissionCheck() {
		return _skipKaleoDefinitionResourcePermissionCheckThreadLocal.get();
	}

	public static SafeCloseable
		setSkipKaleoDefinitionResourcePermissionCheckWithSafeCloseable(
			boolean skipKaleoDefinitionResourcePermissionCheck) {

		return _skipKaleoDefinitionResourcePermissionCheckThreadLocal.
			setWithSafeCloseable(skipKaleoDefinitionResourcePermissionCheck);
	}

	private static final CentralizedThreadLocal<Boolean>
		_skipKaleoDefinitionResourcePermissionCheckThreadLocal =
			new CentralizedThreadLocal<>(
				KaleoDefinitionThreadLocal.class +
					"._skipKaleoDefinitionResourcePermissionCheckThreadLocal",
				() -> Boolean.FALSE);

}