/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.MessageRunnable;

import java.util.List;

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
		List<MessageListener> messageListeners, final Message message) {

		Thread currentThread = Thread.currentThread();

		for (final MessageListener messageListener : messageListeners) {
			Runnable runnable = new MessageRunnable(message) {

				@Override
				public void run() {
					try {
						MessageBusThreadLocalUtil.
							populateThreadLocalsFromMessage(
								message, permissionCheckerFactory,
								userLocalService);

						messageListener.receive(message);
					}
					catch (MessageListenerException messageListenerException) {
						_log.error(
							"Unable to process message " + message,
							messageListenerException);
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

			execute(runnable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ParallelDestination.class);

}