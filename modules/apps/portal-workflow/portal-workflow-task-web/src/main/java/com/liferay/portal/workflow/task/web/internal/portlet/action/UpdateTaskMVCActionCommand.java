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

package com.liferay.portal.workflow.task.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
		"mvc.command.name=updateWorkflowTask"
	},
	service = MVCResourceCommand.class
)
public class UpdateTaskMVCActionCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowTaskId = ParamUtil.getLong(
			resourceRequest, "workflowTaskId");

		String comment = ParamUtil.getString(resourceRequest, "comment");

		int dueDateMonth = ParamUtil.getInteger(
			resourceRequest, "dueDateMonth");
		int dueDateDay = ParamUtil.getInteger(resourceRequest, "dueDateDay");
		int dueDateYear = ParamUtil.getInteger(resourceRequest, "dueDateYear");
		int dueDateHour = ParamUtil.getInteger(resourceRequest, "dueDateHour");
		int dueDateMinute = ParamUtil.getInteger(
			resourceRequest, "dueDateMinute");
		int dueDateAmPm = ParamUtil.getInteger(resourceRequest, "dueDateAmPm");

		if (dueDateAmPm == Calendar.PM) {
			dueDateHour += 12;
		}

		Date dueDate = _portal.getDate(
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute,
			themeDisplay.getTimeZone(), WorkflowTaskDueDateException.class);

		WorkflowTask workflowTask = workflowTaskManager.getWorkflowTask(
			themeDisplay.getCompanyId(), workflowTaskId);

		Date createDate = workflowTask.getCreateDate();

		if (createDate.after(dueDate)) {
			throw new WorkflowTaskDueDateException();
		}

		workflowTaskManager.updateDueDate(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowTaskId, comment, dueDate);
	}

	@Reference
	protected WorkflowTaskManager workflowTaskManager;

	@Reference
	private Portal _portal;

}