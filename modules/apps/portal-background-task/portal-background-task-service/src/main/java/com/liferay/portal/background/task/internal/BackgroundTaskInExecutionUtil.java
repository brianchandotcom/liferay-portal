/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal;

import com.liferay.petra.lang.SafeCloseable;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dante Wang
 */
public class BackgroundTaskInExecutionUtil {

	public static boolean isInExecution(long backgroundTaskId) {
		return _backgroundTaskIds.contains(backgroundTaskId);
	}

	public static SafeCloseable setInExecution(long backgroundTaskId) {
		_backgroundTaskIds.add(backgroundTaskId);

		return () -> _backgroundTaskIds.remove(backgroundTaskId);
	}

	private static final Set<Long> _backgroundTaskIds =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

}