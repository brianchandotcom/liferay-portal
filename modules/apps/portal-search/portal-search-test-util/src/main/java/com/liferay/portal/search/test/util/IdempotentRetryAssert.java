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

package com.liferay.portal.search.test.util;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author André de Oliveira
 */
public class IdempotentRetryAssert {

	public static <T> T retryAssert(
			long timeout, TimeUnit timeoutTimeUnit, Callable<T> callable)
		throws Exception {

		return retryAssert(
			timeout, timeoutTimeUnit, 0, TimeUnit.SECONDS, callable);
	}

	public static <T> T retryAssert(
			long timeout, TimeUnit timeoutTimeUnit, long pause,
			TimeUnit pauseTimeUnit, Callable<T> callable)
		throws Exception {

		long deadline =
			System.currentTimeMillis() + timeoutTimeUnit.toMillis(timeout);

		while (true) {
			try {
				return callable.call();
			}
			catch (AssertionError ae) {
				if (System.currentTimeMillis() > deadline) {
					throw ae;
				}
			}

			Thread.sleep(pauseTimeUnit.toMillis(pause));
		}
	}

	public static <T> T retryAssert(
			long timeout, TimeUnit timeoutTimeUnit, Runnable runnable)
		throws Exception {

		return retryAssert(
			timeout, timeoutTimeUnit, 0, TimeUnit.SECONDS,
			() -> {
				runnable.run();

				return null;
			});
	}

}