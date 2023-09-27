/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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