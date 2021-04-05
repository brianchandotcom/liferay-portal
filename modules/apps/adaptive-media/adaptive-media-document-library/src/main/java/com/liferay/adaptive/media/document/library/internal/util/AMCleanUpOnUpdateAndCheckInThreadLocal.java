/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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