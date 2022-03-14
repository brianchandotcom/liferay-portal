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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

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

	public static SafeCloseable setBackgroundTaskIdWithSafeCloseable(
		long backgroundTaskId) {

		if (backgroundTaskId > 0) {
			return _backgroundTaskId.setWithSafeCloseable(backgroundTaskId);
		}

		return () -> {
		};
	}

	private static final CentralizedThreadLocal<Long> _backgroundTaskId =
		new CentralizedThreadLocal<>(
			BackgroundTaskThreadLocal.class + "._backgroundTaskId", () -> 0L);

}