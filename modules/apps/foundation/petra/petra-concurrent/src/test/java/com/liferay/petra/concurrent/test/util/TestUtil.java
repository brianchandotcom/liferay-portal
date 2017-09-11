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

package com.liferay.petra.concurrent.test.util;

import com.liferay.petra.concurrent.ThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class TestUtil {

	public static final long LONG_WAIT = 30 * 1000;

	public static void closePool(ThreadPoolExecutor threadPoolExecutor) {
		closePool(threadPoolExecutor, false);
	}

	public static void closePool(
		ThreadPoolExecutor threadPoolExecutor, boolean force) {

		try {
			if (force) {
				threadPoolExecutor.shutdownNow();
			}
			else {
				threadPoolExecutor.shutdown();
			}

			if (!threadPoolExecutor.awaitTermination(
					LONG_WAIT, TimeUnit.MILLISECONDS)) {

				throw new IllegalStateException();
			}

			if (!threadPoolExecutor.isTerminated()) {
				throw new IllegalStateException();
			}
		}
		catch (InterruptedException ie) {
			throw new RuntimeException();
		}
	}

}