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

package com.liferay.portal.workflow.kaleo.designer.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.designer.util.KaleoDesignerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kenneth Chang
 */
@Component(immediate = true, service = KaleoDefinitionMessageListener.class)
public class KaleoDefinitionMessageListener implements MessageListener {

	@Override
	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");

		if (command.equals("create")) {
			onCreate(message);
		}
		else if (command.equals("delete")) {
			onDelete(message);
		}
	}

	protected void onCreate(Message message) throws Exception {
		String name = message.getString("name");
		int version = message.getInteger("version");

		ServiceContext serviceContext = (ServiceContext)message.get(
			"serviceContext");

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				serviceContext.getCompanyId(), name, version);

		KaleoDesignerUtil.addMissingKaleoDraftDefinition(
			workflowDefinition.getName(), workflowDefinition.getVersion(),
			workflowDefinition.getTitle(), workflowDefinition.getContent(),
			serviceContext);
	}

	protected void onDelete(Message message) throws Exception {
		String name = message.getString("name");
		int version = message.getInteger("version");

		ServiceContext serviceContext = (ServiceContext)message.get(
			"serviceContext");

		KaleoDraftDefinitionLocalServiceUtil.deleteKaleoDraftDefinitions(
			name, version, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionMessageListener.class);

}