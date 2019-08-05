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

package com.liferay.change.tracking.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;

/**
 * @author Preston Crary
 */
public class CTPersistenceHelperThreadLocal {

	public static boolean isEnabled() {
		return _enabled.get();
	}

	public static SafeClosable setEnabled(boolean enabled) {
		return _enabled.setWithSafeClosable(enabled);
	}

	private CTPersistenceHelperThreadLocal() {
	}

	private static final CentralizedThreadLocal<Boolean> _enabled =
		new CentralizedThreadLocal<>(
			CTPersistenceHelperThreadLocal.class + "._enabled",
			() -> Boolean.TRUE);

}