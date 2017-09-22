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

package com.liferay.portal.kernel.cache;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Tina Tian
 */
public class SkipReplicationThreadLocal {

	public static boolean isEnabled() {
		return _skipReplicationThreadLocal.get();
	}

	public static void setEnabled(boolean enabled) {
		_skipReplicationThreadLocal.set(enabled);
	}

	private static final ThreadLocal<Boolean> _skipReplicationThreadLocal =
		new CentralizedThreadLocal<>(
			SkipReplicationThreadLocal.class + "._skipReplicationThreadLocal",
			() -> Boolean.FALSE, false);

}