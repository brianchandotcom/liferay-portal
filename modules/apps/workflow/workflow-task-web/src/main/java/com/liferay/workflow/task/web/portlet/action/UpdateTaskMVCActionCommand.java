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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
		"mvc.command.name=updateWorkflowTask"
	},
	service = MVCActionCommand.class
)
public class UpdateTaskMVCActionCommand
	extends WorkflowTaskBaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");

		String comment = ParamUtil.getString(actionRequest, "comment");

		int dueDateMonth = ParamUtil.getInteger(actionRequest, "dueDateMonth");
		int dueDateDay = ParamUtil.getInteger(actionRequest, "dueDateDay");
		int dueDateYear = ParamUtil.getInteger(actionRequest, "dueDateYear");
		int dueDateHour = ParamUtil.getInteger(actionRequest, "dueDateHour");
		int dueDateMinute = ParamUtil.getInteger(
			actionRequest, "dueDateMinute");
		int dueDateAmPm = ParamUtil.getInteger(actionRequest, "dueDateAmPm");

		if (dueDateAmPm == Calendar.PM) {
			dueDateHour += 12;
		}

		Date dueDate = PortalUtil.getDate(
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute,
			WorkflowTaskDueDateException.class);

		WorkflowTaskManagerUtil.updateDueDate(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowTaskId, comment, dueDate);
	}

}