/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Shuyang Zhou
 */
public class CallerRunsPolicy implements RejectedExecutionHandler {

	public void rejectedExecution(
		Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

		if (threadPoolExecutor.isShutdown()) {
			return;
		}

		ThreadPoolHandler threadPoolHandler =
			threadPoolExecutor.getThreadPoolHandler();

		Throwable throwable = null;

		threadPoolHandler.beforeExecute(Thread.currentThread(), runnable);

		try {
			runnable.run();
		}
		catch (RuntimeException re) {
			throwable = re;

			throw re;
		}
		finally {
			threadPoolHandler.afterExecute(runnable, throwable);
		}
	}

}