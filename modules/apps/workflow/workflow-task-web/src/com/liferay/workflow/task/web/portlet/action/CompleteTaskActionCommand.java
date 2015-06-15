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

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"command.name=completeWorkflowTask",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK
	},
	service = ActionCommand.class
)
public class CompleteTaskActionCommand extends WorkflowTaskBaseActionCommand {

	@Override
	protected void doProcessCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");

		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");
		String comment = ParamUtil.getString(actionRequest, "comment");

		WorkflowTaskManagerUtil.completeWorkflowTask(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowTaskId, transitionName, comment, null);
	}

}