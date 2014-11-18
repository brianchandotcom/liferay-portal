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

package com.liferay.portal.test.log;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentAssertUtil {

	public static void caughtFailure(Exception exception) {
		Thread currentThread = Thread.currentThread();

		if (currentThread != _thread) {
			_concurrentFailures.put(currentThread, exception);

			_thread.interrupt();
		}
		else {
			ReflectionUtil.throwException(exception);
		}
	}

	public static void endAssert() {
		_thread = null;

		try {
			for (Map.Entry<Thread, Exception> entry :
					_concurrentFailures.entrySet()) {

				Thread thread = entry.getKey();
				Exception exception = entry.getValue();

				Assert.fail(
					"Thread " + thread + " caught concurrent failure: " +
						exception);

				ReflectionUtil.throwException(exception);
			}
		}
		finally {
			_concurrentFailures.clear();
		}
	}

	public static void startAssert() {
		_thread = Thread.currentThread();
	}

	private static final Map<Thread, Exception> _concurrentFailures =
		new ConcurrentHashMap<Thread, Exception>();
	private static volatile Thread _thread;

}