/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.internal.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Adolfo Pérez
 */
public class AMCleanUpOnUpdateAndCheckInThreadLocal {

	public static <T, E extends Throwable> T enable(
			UnsafeSupplier<T, E> unsafeSupplier)
		throws E {

		boolean enabled = isEnabled();

		try {
			_cleanUpOnUpdateAndCheckIn.set(true);

			return unsafeSupplier.get();
		}
		finally {
			_cleanUpOnUpdateAndCheckIn.set(enabled);
		}
	}

	public static boolean isEnabled() {
		return _cleanUpOnUpdateAndCheckIn.get();
	}

	private static final ThreadLocal<Boolean> _cleanUpOnUpdateAndCheckIn =
		new CentralizedThreadLocal<>(
			AMCleanUpOnUpdateAndCheckInThreadLocal.class +
				"._cleanUpOnUpdateAndCheckIn",
			() -> Boolean.FALSE);

}