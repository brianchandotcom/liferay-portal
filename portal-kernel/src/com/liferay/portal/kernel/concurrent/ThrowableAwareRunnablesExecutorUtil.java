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

import com.liferay.portal.kernel.exception.BulkException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ThrowableAwareRunnablesExecutorUtil {

	public static void execute(
			Collection<? extends ThrowableAwareRunnable>
				throwableAwareRunnables)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(
			throwableAwareRunnables.size());

		List<Callable<Object>> jobs = new ArrayList<>(
			throwableAwareRunnables.size());

		for (ThrowableAwareRunnable throwableAwareRunnable :
				throwableAwareRunnables) {

			jobs.add(Executors.callable(throwableAwareRunnable));
		}

		try {
			List<Future<Object>> futures = executorService.invokeAll(jobs);

			for (Future<Object> future : futures) {
				future.get();
			}
		}
		finally {
			executorService.shutdown();
		}

		List<Throwable> throwables = new ArrayList<>();

		for (ThrowableAwareRunnable throwableAwareRunnable :
				throwableAwareRunnables) {

			if (throwableAwareRunnable.hasException()) {
				throwables.add(throwableAwareRunnable.getThrowable());
			}
		}

		if (!throwables.isEmpty()) {
			throw new BulkException(throwables);
		}
	}

}