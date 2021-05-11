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

package com.liferay.portal.kernel.increment;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Dante Wang
 */
public class BufferedIncrementThreadLocal {

	public static boolean isForceSync() {
		return _forceSync.get();
	}

	public static SafeCloseable setWithSafeCloseable(boolean forceSync) {
		return _forceSync.setWithSafeCloseable(forceSync);
	}

	private static final CentralizedThreadLocal<Boolean> _forceSync =
		new CentralizedThreadLocal<>(
			BufferedIncrementThreadLocal.class + "_forceSync",
			() -> Boolean.FALSE);

}