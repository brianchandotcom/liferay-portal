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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.background.task.internal.SerialBackgroundTaskExecutor}
 */
@Deprecated
public class SerialBackgroundTaskExecutor
	extends DelegatingBackgroundTaskExecutor {

	public SerialBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor) {

		super(backgroundTaskExecutor);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new SerialBackgroundTaskExecutor(getBackgroundTaskExecutor());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Lock lock = null;

		try {
			if (isSerial()) {
				lock = acquireLock(backgroundTask);
			}

			BackgroundTaskExecutor backgroundTaskExecutor =
				getBackgroundTaskExecutor();

			return backgroundTaskExecutor.execute(backgroundTask);
		}
		finally {
			if (lock != null) {
				BackgroundTaskLockHelperUtil.unlockBackgroundTask(
					backgroundTask);
			}
		}
	}

	protected Lock acquireLock(BackgroundTask backgroundTask)
		throws DuplicateLockException {

		Lock lock = BackgroundTaskLockHelperUtil.lockBackgroundTask(
			backgroundTask);

		if (!lock.isNew()) {
			throw new DuplicateLockException(lock);
		}

		return lock;
	}

}