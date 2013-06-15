/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Alexander Chow
 */
public class UpgradeProcessThreadLocal {

	public static Integer getThreshold() {
		return _threshold.get();
	}

	public static void setThreshold(int threshold) {
		_threshold.set(threshold);
	}

	private static ThreadLocal<Integer> _threshold =
		new AutoResetThreadLocal<Integer>(
			UpgradeProcessThreadLocal.class + "._threshold", 0);

}