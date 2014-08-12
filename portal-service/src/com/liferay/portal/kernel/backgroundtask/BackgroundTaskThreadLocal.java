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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskThreadLocal {

	public static long getBackgroundTaskId() {
		return _backgroundTaskId.get();
	}

	public static boolean hasBackgroundTask() {
		long backgroundTaskId = getBackgroundTaskId();

		if (backgroundTaskId > 0) {
			return true;
		}

		return false;
	}

	public static void setBackgroundTaskId(long backgroundTaskId) {
		if (backgroundTaskId > 0) {
			_backgroundTaskId.set(backgroundTaskId);
		}
	}

	private static final ThreadLocal<Long> _backgroundTaskId =
		new AutoResetThreadLocal<Long>(
			BackgroundTaskThreadLocal.class + "._backgroundTaskId", 0L);

}