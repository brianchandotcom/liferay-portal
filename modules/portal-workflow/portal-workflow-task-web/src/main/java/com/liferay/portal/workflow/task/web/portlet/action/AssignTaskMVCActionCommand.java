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

package com.liferay.portal.workflow.task.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;

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
		"mvc.command.name=assignWorkflowTask"
	},
	service = MVCActionCommand.class
)
public class AssignTaskMVCActionCommand
	extends WorkflowTaskBaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");

		long assigneeUserId = ParamUtil.getLong(
			actionRequest, "assigneeUserId");
		String comment = ParamUtil.getString(actionRequest, "comment");

		WorkflowTaskManagerUtil.assignWorkflowTaskToUser(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowTaskId, assigneeUserId, comment, null, null);
	}

}