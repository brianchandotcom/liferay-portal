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

package com.liferay.workflow.task.web.portlet.action;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.workflow.task.web.portlet.constants.WorkflowTaskConstants;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=assignWorkflowTask",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASKS
	},
	service = ActionCommand.class
)
public class AssignTaskActionCommand extends WorkflowTaskBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long workflowTaskId = ParamUtil.getLong(
			portletRequest, WorkflowTaskConstants.WORKFLOW_TASK_ID);

		long assigneeUserId = ParamUtil.getLong(
			portletRequest, WorkflowTaskConstants.ASSIGNEE_USER_ID);

		String comment = ParamUtil.getString(portletRequest, 
			WorkflowTaskConstants.COMMENT);

		WorkflowTaskManagerUtil.assignWorkflowTaskToUser(
			getCompanyId(portletRequest), getUserId(portletRequest),
			workflowTaskId, assigneeUserId, comment, null, null);

		super.doProcessCommand(portletRequest, portletResponse);
	}

}