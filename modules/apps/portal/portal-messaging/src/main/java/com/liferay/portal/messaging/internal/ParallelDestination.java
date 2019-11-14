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

package com.liferay.portal.messaging.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.MessageRunnable;

import java.util.Set;

/**
 * <p>
 * Destination that delivers a message to a list of message listeners in
 * parallel.
 * </p>
 *
 * @author Michael C. Han
 */
public class ParallelDestination extends BaseAsyncDestination {

	@Override
	protected void dispatch(
		Set<MessageListener> messageListeners, final Message message) {

		Thread currentThread = Thread.currentThread();

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		for (final MessageListener messageListener : messageListeners) {
			Runnable runnable = new MessageRunnable(message) {

				@Override
				public void run() {
					try {
						MessageBusThreadLocalHelper.
							populateThreadLocalsFromMessage(
								message, permissionCheckerFactory,
								userLocalService);

						messageListener.receive(message);
					}
					catch (MessageListenerException mle) {
						_log.error("Unable to process message " + message, mle);
					}
					finally {
						if (Thread.currentThread() != currentThread) {
							ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

							CentralizedThreadLocal.
								clearShortLivedThreadLocals();
						}
					}
				}

			};

			threadPoolExecutor.execute(runnable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ParallelDestination.class);

}