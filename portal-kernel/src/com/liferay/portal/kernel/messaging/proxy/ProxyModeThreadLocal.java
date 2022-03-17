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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class ProxyModeThreadLocal {

	public static boolean isForceSync() {
		return _forceSync.get();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setWithSafeClosable(boolean)}
	 */
	@Deprecated
	public static void setForceSync(boolean forceSync) {
		_forceSync.set(forceSync);
	}

	public static SafeCloseable setWithSafeCloseable(boolean forceSync) {
		return _forceSync.setWithSafeCloseable(forceSync);
	}

	private static final CentralizedThreadLocal<Boolean> _forceSync =
		new CentralizedThreadLocal<>(
			ProxyModeThreadLocal.class + "_forceSync", () -> Boolean.TRUE);

}