/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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