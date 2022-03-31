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

package com.liferay.portal.kernel.search;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class IndexableThreadLocal {

	public static boolean isForceSync() {
		return _forceSync.get();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setWithSafeCloseable(boolean)}
	 */
	@Deprecated
	public static SafeClosable setWithSafeClosable(boolean forceSync) {
		return _forceSync.setWithSafeClosable(forceSync);
	}

	public static SafeCloseable setWithSafeCloseable(boolean forceSync) {
		return _forceSync.setWithSafeCloseable(forceSync);
	}

	private static final CentralizedThreadLocal<Boolean> _forceSync =
		new CentralizedThreadLocal<>(
			IndexableThreadLocal.class + "_forceSync", () -> Boolean.TRUE);

}