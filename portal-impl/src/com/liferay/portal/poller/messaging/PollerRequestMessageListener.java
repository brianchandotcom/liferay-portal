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

package com.liferay.portal.poller.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.poller.PollerProcessorUtil;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PollerRequestMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		PollerRequest pollerRequest = (PollerRequest)message.getPayload();

		String portletId = pollerRequest.getPortletId();

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerRequest.isReceiveRequest()) {
			PollerResponse pollerResponse = null;

			try {
				pollerResponse = pollerProcessor.receive(pollerRequest);
			}
			catch (PollerException pe) {
				_log.error(
					"Unable to receive poller request " + pollerRequest, pe);

				pollerResponse = pollerRequest.createPollerResponse();

				pollerResponse.setParameter("pollerException", pe.getMessage());
			}
			finally {
				if (pollerResponse == null) {
					pollerResponse = pollerRequest.createPollerResponse();
				}

				pollerResponse.close(
					message, pollerRequest.getPollerHeader(),
					pollerRequest.getPortletId(), pollerRequest.getChunkId());
			}
		}
		else {
			try {
				pollerProcessor.send(pollerRequest);
			}
			catch (PollerException pe) {
				_log.error(
					"Unable to send poller request " + pollerRequest, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollerRequestMessageListener.class);

}