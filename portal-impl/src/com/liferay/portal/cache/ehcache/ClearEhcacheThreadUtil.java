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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ThreadUtil;

/**
 * @author Shuyang Zhou
 */
public class ClearEhcacheThreadUtil {

	public static void clearEhcacheReplicationThread()
		throws InterruptedException {

		Thread[] threads = ThreadUtil.getThreads();

		for (Thread thread : threads) {
			if (thread == null) {
				continue;
			}

			String name = thread.getName();

			if (name.equals(_THREAD_NAME)) {
				thread.interrupt();

				thread.join(_WAIT_TIME);

				if (thread.isAlive() && _log.isWarnEnabled()) {
					_log.warn(
						"Give up waiting on thread " + thread +
							" after waiting for " + _WAIT_TIME + "ms");
				}
			}
		}
	}

	private static final String _THREAD_NAME = "Replication Thread";

	private static final long _WAIT_TIME = 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		ClearEhcacheThreadUtil.class);

}