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

package com.liferay.portal.workflow.reports.messaging.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;
import com.liferay.portal.workflow.reports.messaging.constants.WorkflowReportsDestinationNames;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowReportsMessageSender.class)
public class WorkflowReportsMessageSenderImpl
	implements WorkflowReportsMessageSender {

	public void sendMessage(
		long companyId, String eventId, long userId,
		Map<String, String> properties) {

		try {
			Message message = new Message();

			Map<String, String> values = new HashMap<>();

			values.put("applicationId", "workflow:1.0.0");
			values.put("companyId", String.valueOf(companyId));
			values.put("eventId", eventId);
			values.put("userId", String.valueOf(userId));

			values.putAll(properties);

			message.setPayload(values);

			_messageBus.sendMessage(
				WorkflowReportsDestinationNames.WORKFLOW_REPORTS, message);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowReportsMessageSenderImpl.class);

	@Reference
	private MessageBus _messageBus;

}