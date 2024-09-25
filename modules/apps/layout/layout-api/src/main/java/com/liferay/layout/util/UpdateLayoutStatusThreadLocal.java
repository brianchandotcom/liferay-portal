/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Lourdes Fernández Besada
 */
public class UpdateLayoutStatusThreadLocal {

	public static Boolean isUpdateLayoutStatus() {
		return _updateLayoutStatus.get();
	}

	public static SafeCloseable setUpdateLayoutStatusWithSafeCloseable(
		Boolean updateLayoutStatus) {

		boolean currentUpdateLayoutStatus = _updateLayoutStatus.get();

		_updateLayoutStatus.set(updateLayoutStatus);

		return () -> _updateLayoutStatus.set(currentUpdateLayoutStatus);
	}

	private static final CentralizedThreadLocal<Boolean> _updateLayoutStatus =
		new CentralizedThreadLocal<>(
			UpdateLayoutStatusThreadLocal.class + "._updateLayoutStatus",
			() -> Boolean.TRUE);

}