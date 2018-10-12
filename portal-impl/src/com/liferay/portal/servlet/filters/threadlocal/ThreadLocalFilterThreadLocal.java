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

package com.liferay.portal.servlet.filters.threadlocal;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Tina Tian
 */
public class ThreadLocalFilterThreadLocal {

	public static boolean isFilterInvoked() {
		return _filterInvoked.get();
	}

	public static void setFilterInvoked() {
		_filterInvoked.set(true);
	}

	private static final ThreadLocal<Boolean> _filterInvoked =
		new CentralizedThreadLocal<>(
			ThreadLocalFilterThreadLocal.class + "._filterInvoked",
			() -> Boolean.FALSE);

}