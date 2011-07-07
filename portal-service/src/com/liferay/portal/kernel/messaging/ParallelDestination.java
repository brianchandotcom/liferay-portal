/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.security.auth.CompanyThreadLocal;

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

	public ParallelDestination() {
	}

	/**
	 * @deprecated
	 */
	public ParallelDestination(String name) {
		super(name);
	}

	/**
	 * @deprecated
	 */
	public ParallelDestination(
		String name, int workersCoreSize, int workersMaxSize) {

		super(name, workersCoreSize, workersMaxSize);
	}

	@Override
	protected void dispatch(
		Set<MessageListener> messageListeners, final Message message) {

		if (!message.contains("companyId")) {
			message.put("companyId", CompanyThreadLocal.getCompanyId());
		}

		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		for (final MessageListener messageListener : messageListeners) {
			Runnable runnable = new MessageRunnable(message) {

				public void run() {
					try {
						Long companyId = message.getLong("companyId");

						CompanyThreadLocal.setCompanyId(companyId);

						messageListener.receive(message);
					}
					catch (MessageListenerException mle) {
						_log.error("Unable to process message " + message, mle);
					}
					finally {
						CompanyThreadLocal.setCompanyId(0);

						CentralizedThreadLocal.clearShortLivedThreadLocals();
					}
				}

			};

			threadPoolExecutor.execute(runnable);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ParallelDestination.class);

}