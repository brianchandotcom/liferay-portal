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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentAssertUtil {

	public static void caughtError(String message) {
		Thread currentThread = Thread.currentThread();

		if (currentThread != _assertingThread) {
			_concurrentErrors.put(currentThread, message);

			_assertingThread.interrupt();
		}
		else {
			Assert.fail(message);
		}
	}

	public static void endAssert() {
		_assertingThread = null;

		for (Map.Entry<Thread, String> entry : _concurrentErrors.entrySet()) {
			Assert.fail(
				"Caught concurrent error : " + entry.getValue() +
					" at thread : " + entry.getKey());
		}

		_concurrentErrors.clear();
	}

	public static void startAssert() {
		_assertingThread = Thread.currentThread();
	}

	private static final Map<Thread, String> _concurrentErrors =
		new ConcurrentHashMap<Thread, String>();

	private static volatile Thread _assertingThread;

}