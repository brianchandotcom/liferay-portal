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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Michael C. Han
 */
public class SerialBackgroundTaskExecutor
	extends DelegatingBackgroundTaskExecutor {

	public SerialBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor) {

		super(backgroundTaskExecutor);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		BackgroundTaskExecutor backgroundTaskExecutor =
			new SerialBackgroundTaskExecutor(getBackgroundTaskExecutor());

		return backgroundTaskExecutor;
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

		Lock lock = null;

		while (true) {
			try {
				lock = BackgroundTaskLockHelperUtil.lockBackgroundTask(
					backgroundTask);

				break;
			}
			catch (SystemException se) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to acquire acquiring lock", se);
				}

				try {
					Thread.sleep(50);
				}
				catch (InterruptedException ie) {
				}
			}
		}

		if (!lock.isNew()) {
			throw new DuplicateLockException(lock);
		}

		return lock;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SerialBackgroundTaskExecutor.class);

}