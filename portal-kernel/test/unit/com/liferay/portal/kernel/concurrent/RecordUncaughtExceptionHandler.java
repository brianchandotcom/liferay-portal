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

package com.liferay.portal.kernel.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class RecordUncaughtExceptionHandler
	implements Thread.UncaughtExceptionHandler {

	public Map<Thread, Throwable> getUncaughtMap() {
		return _uncaughtMap;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		_uncaughtMap.put(thread, throwable);
	}

	private final Map<Thread, Throwable> _uncaughtMap =
		new ConcurrentHashMap<>();

}