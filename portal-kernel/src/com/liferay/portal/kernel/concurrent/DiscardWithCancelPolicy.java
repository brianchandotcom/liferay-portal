/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import java.util.concurrent.Future;

/**
 * Handles rejected tasks by canceling them immediately.
 *
 * <p>
 * Use this policy for efficiently discarding rejected tasks. Unlike {@link
 * CallerRunsPolicy}, this policy maintains the order of tasks in the task
 * queue. Unlike {@link DiscardOldestPolicy} and {@link DiscardPolicy}, which
 * ultimately call {@link Future#get()}, threads do not block waiting for a
 * timeout.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class DiscardWithCancelPolicy implements RejectedExecutionHandler {

	/**
	 * Rejects execution of the {@link Runnable} task by canceling it
	 * immediately.
	 *
	 * <p>
	 * <strong>Important:</strong> The task can only be canceled if it is a
	 * subtype of {@link Future}.
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