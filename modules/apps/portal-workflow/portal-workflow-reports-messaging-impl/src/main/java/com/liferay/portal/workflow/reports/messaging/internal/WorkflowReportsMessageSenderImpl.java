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
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;
import com.liferay.portal.workflow.reports.messaging.constants.WorkflowReportsDestinationNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowReportsMessageSender.class)
public class WorkflowReportsMessageSenderImpl
	implements WorkflowReportsMessageSender {

	public void sendMessage(WorkflowReportsMessage workflowReportsMessage) {
		try {
			Message message = new Message();

			message.setPayload(workflowReportsMessage);

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