/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class SynchronousDestination extends BaseDestination {

	@Override
	public DestinationStatistics getDestinationStatistics() {
		DestinationStatistics destinationStatistics =
			new DestinationStatistics();

		destinationStatistics.setSentMessageCount(_sentMessageCounter.get());

		return destinationStatistics;
	}

	@Override
	public void send(Message message) {
		long companyId = message.getLong("companyId");

		if (companyId == CompanyThreadLocal.getCompanyId()) {
			_send(message);

			return;
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			_send(message);
		}
	}

	private void _send(Message message) {
		for (MessageListener messageListener :
				messageListenerRegistry.getMessageListeners(name)) {

			try {
				messageListener.receive(message);
			}
			catch (MessageListenerException messageListenerException) {
				_log.error(
					"Unable to process message " + message,
					messageListenerException);
			}
		}

		_sentMessageCounter.incrementAndGet();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynchronousDestination.class);

	private final AtomicLong _sentMessageCounter = new AtomicLong();

}