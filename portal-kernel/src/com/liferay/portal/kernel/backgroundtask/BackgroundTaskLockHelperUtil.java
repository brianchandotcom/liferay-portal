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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;

/**
 * @author     Daniel Kocsis
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.background.task.internal.lock.BackgroundTaskLockHelper}
 */
@Deprecated
public class BackgroundTaskLockHelperUtil {

	public static boolean isLockedBackgroundTask(
		BackgroundTask backgroundTask) {

		return LockManagerUtil.isLocked(
			BackgroundTaskExecutor.class.getName(), getLockKey(backgroundTask));
	}

	public static Lock lockBackgroundTask(BackgroundTask backgroundTask) {
		String owner =
			backgroundTask.getName() + StringPool.POUND +
				backgroundTask.getBackgroundTaskId();

		return LockManagerUtil.lock(
			BackgroundTaskExecutor.class.getName(), getLockKey(backgroundTask),
			owner);
	}

	public static void unlockBackgroundTask(BackgroundTask backgroundTask) {
		String owner =
			backgroundTask.getName() + StringPool.POUND +
				backgroundTask.getBackgroundTaskId();

		LockManagerUtil.unlock(
			BackgroundTaskExecutor.class.getName(), getLockKey(backgroundTask),
			owner);
	}

	protected static String getLockKey(BackgroundTask backgroundTask) {
		BackgroundTaskExecutor backgroundTaskExecutor =
			BackgroundTaskExecutorRegistryUtil.getBackgroundTaskExecutor(
				backgroundTask.getTaskExecutorClassName());

		String lockKey = StringPool.BLANK;

		if (backgroundTaskExecutor.getIsolationLevel() ==
				BackgroundTaskConstants.ISOLATION_LEVEL_CLASS) {

			lockKey = backgroundTask.getTaskExecutorClassName();
		}
		else if (backgroundTaskExecutor.getIsolationLevel() ==
					BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY) {

			lockKey =
				backgroundTask.getTaskExecutorClassName() + StringPool.POUND +
					backgroundTask.getCompanyId();
		}
		else if (backgroundTaskExecutor.getIsolationLevel() ==
					BackgroundTaskConstants.ISOLATION_LEVEL_CUSTOM) {

			lockKey = backgroundTaskExecutor.generateLockKey(backgroundTask);
		}
		else if (backgroundTaskExecutor.getIsolationLevel() ==
					BackgroundTaskConstants.ISOLATION_LEVEL_GROUP) {

			lockKey =
				backgroundTask.getTaskExecutorClassName() + StringPool.POUND +
					backgroundTask.getGroupId();
		}
		else if (backgroundTaskExecutor.getIsolationLevel() ==
					BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME) {

			lockKey =
				backgroundTask.getTaskExecutorClassName() + StringPool.POUND +
					backgroundTask.getName();
		}
		else {
			lockKey =
				backgroundTask.getTaskExecutorClassName() + StringPool.POUND +
					backgroundTaskExecutor.getIsolationLevel();
		}

		return lockKey;
	}

}