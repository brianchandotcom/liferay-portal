/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.backgroundtask.executor;

import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class SerialBackgroundTaskExecutor implements BackgroundTaskExecutor {

	public SerialBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor) {

		_backgroundTaskExecutor = backgroundTaskExecutor;
	}

	@Override
	public void execute(BackgroundTask backgroundTask) throws Exception {
		Lock lock = null;

		try {
			if (isSerial()) {
				String owner =
					backgroundTask.getName() +
						backgroundTask.getBackgroundTaskId();

				lock = LockLocalServiceUtil.lock(
					backgroundTask.getUserId(),
					BackgroundTaskExecutor.class.getName(),
					backgroundTask.getTaskExecutorClassName(), owner, false, 0);
			}

			_backgroundTaskExecutor.execute(backgroundTask);
		}
		finally {
			if (lock != null) {
				LockLocalServiceUtil.unlock(
					BaseBackgroundTaskExecutor.class.getName(),
					backgroundTask.getTaskExecutorClassName()
				);
			}
		}
	}

	@Override
	public boolean isSerial() {
		return _backgroundTaskExecutor.isSerial();
	}

	private BackgroundTaskExecutor _backgroundTaskExecutor;

}