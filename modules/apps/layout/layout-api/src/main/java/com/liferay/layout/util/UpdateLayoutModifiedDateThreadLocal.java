/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Rubén Pulido
 */
public class UpdateLayoutModifiedDateThreadLocal {

	public static Boolean isUpdateLayoutModifiedDate() {
		return _updateLayoutModifiedDate.get();
	}

	public static SafeCloseable setUpdateLayoutModifiedDateWithSafeCloseable(
		Boolean updateLayoutModifiedDate) {

		boolean currentUpdateLayoutModifiedDate =
			_updateLayoutModifiedDate.get();

		_updateLayoutModifiedDate.set(updateLayoutModifiedDate);

		return () -> _updateLayoutModifiedDate.set(
			currentUpdateLayoutModifiedDate);
	}

	private static final ThreadLocal<Boolean> _updateLayoutModifiedDate =
		new CentralizedThreadLocal<>(
			UpdateLayoutModifiedDateThreadLocal.class +
				"._updateLayoutModifiedDate",
			() -> Boolean.TRUE);

}