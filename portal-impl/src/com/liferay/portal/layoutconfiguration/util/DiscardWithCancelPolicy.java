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

package com.liferay.portal.layoutconfiguration.util;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Shuyang Zhou
 */
public class DiscardWithCancelPolicy implements RejectedExecutionHandler {

	/**
	 * Rejects execution of the {@link Runnable} task by canceling it
	 * immediately.
	 *
	 * <p>
	 * Important: The task can only be canceled if it is a subtype of {@link
	 * Future}.
	 * </p>
	 *
	 * @param runnable the task
	 * @param threadPoolExecutor the executor
	 */
	@Override
	public void rejectedExecution(
		Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

		if (runnable instanceof Future<?>) {
			Future<?> future = (Future<?>)runnable;

			// There is no point to try and interrupt the runner thread since
			// being rejected means it is not yet running

			future.cancel(false);
		}
	}

}