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

package com.liferay.portal.search.internal.buffer;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Michael C. Han
 */
public class BufferOverflowThreadLocal {

	public static boolean isOverflowMode() {
		return _overflowMode.get();
	}

	public static void setOverflowMode(boolean overflowMode) {
		_overflowMode.set(overflowMode);
	}

	private static final ThreadLocal<Boolean> _overflowMode =
		new CentralizedThreadLocal<>(
			BufferOverflowThreadLocal.class + "._overflowMode",
			() -> Boolean.FALSE);

}