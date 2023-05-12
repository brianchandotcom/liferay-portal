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