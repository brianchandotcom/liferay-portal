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

import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.workflow.task.web.portlet.constants.WorkflowTaskConstants;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=updateWorkflowTask",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASKS
	},
	service = ActionCommand.class
)
public class UpdateTaskActionCommand extends WorkflowTaskBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long workflowTaskId = ParamUtil.getLong(
			portletRequest, WorkflowTaskConstants.WORKFLOW_TASK_ID);

		String comment = ParamUtil.getString(portletRequest, 
			WorkflowTaskConstants.COMMENT);

		int dueDateMonth = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_MONTH);

		int dueDateDay = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_DAY);

		int dueDateYear = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_YEAR);

		int dueDateHour = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_HOUR);

		int dueDateMinute = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_MINUTE);

		int dueDateAmPm = ParamUtil.getInteger(
			portletRequest, WorkflowTaskConstants.DUE_DATE_AM_PM);

		if (dueDateAmPm == Calendar.PM) {
			dueDateHour += 12;
		}

		Date dueDate = PortalUtil.getDate(
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
			dueDateMinute, WorkflowTaskDueDateException.class);

		WorkflowTaskManagerUtil.updateDueDate(
			getCompanyId(portletRequest), getUserId(portletRequest),
			workflowTaskId, comment, dueDate);

		super.doProcessCommand(portletRequest, portletResponse);
	}

}