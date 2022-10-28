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
import com.liferay.portal.kernel.poller.PollerResponseHandler;
import com.liferay.portal.poller.PollerProcessorUtil;

import java.util.List;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PollerRequestMessageListener extends BaseMessageListener {

	public PollerRequestMessageListener(
		List<PollerResponseHandler> pollerResponseHandlers) {

		_pollerResponseHandlers = pollerResponseHandlers;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		PollerRequest pollerRequest = (PollerRequest)message.getPayload();

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(
				pollerRequest.getPortletId());

		if (pollerRequest.isReceiveRequest()) {
			PollerResponse pollerResponse = null;

			try {
				pollerResponse = pollerProcessor.receive(pollerRequest);
			}
			catch (PollerException pollerException) {
				_log.error(
					"Unable to receive poller request " + pollerRequest,
					pollerException);

				pollerResponse = new PollerResponse();

				pollerResponse.setParameter(
					"pollerException", pollerException.getMessage());
			}
			finally {
				if (pollerResponse == null) {
					pollerResponse = new PollerResponse();
				}

				pollerResponse.populate(
					message.getResponseId(), pollerRequest.getPollerHeader(),
					pollerRequest.getPortletId(), pollerRequest.getChunkId());

				for (PollerResponseHandler pollerResponseHandler :
						_pollerResponseHandlers) {

					pollerResponseHandler.handle(pollerResponse);
				}
			}
		}
		else {
			try {
				pollerProcessor.send(pollerRequest);
			}
			catch (PollerException pollerException) {
				_log.error(
					"Unable to send poller request " + pollerRequest,
					pollerException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollerRequestMessageListener.class);

	private final List<PollerResponseHandler> _pollerResponseHandlers;

}