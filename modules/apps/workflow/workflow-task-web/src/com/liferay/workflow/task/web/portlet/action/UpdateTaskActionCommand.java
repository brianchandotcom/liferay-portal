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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=updateTask",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASKS
	},
	service = ActionCommand.class
)
public class UpdateTaskActionCommand extends WorkflowTaskBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long workflowTaskId = ParamUtil.getLong(
				portletRequest, "workflowTaskId");

			String comment = ParamUtil.getString(portletRequest, "comment");

			int dueDateMonth = ParamUtil.getInteger(
				portletRequest, "dueDateMonth");

			int dueDateDay = ParamUtil.getInteger(portletRequest, "dueDateDay");

			int dueDateYear = ParamUtil.getInteger(
				portletRequest, "dueDateYear");

			int dueDateHour = ParamUtil.getInteger(
				portletRequest, "dueDateHour");

			int dueDateMinute = ParamUtil.getInteger(
				portletRequest, "dueDateMinute");

			int dueDateAmPm = ParamUtil.getInteger(
				portletRequest, "dueDateAmPm");

			if (dueDateAmPm == Calendar.PM) {
				dueDateHour += 12;
			}

			Date dueDate = PortalUtil.getDate(
				dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
				dueDateMinute, WorkflowTaskDueDateException.class);

			WorkflowTaskManagerUtil.updateDueDate(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				workflowTaskId, comment, dueDate);

			super.doProcessCommand(portletRequest, portletResponse);
		}
		catch (Exception e) {
			if (e instanceof WorkflowTaskDueDateException) {
				SessionErrors.add(portletRequest, e.getClass());
			}
			else if (e instanceof PrincipalException ||
					 e instanceof WorkflowException) {

				SessionErrors.add(portletRequest, e.getClass());

				PortletSession portletSession =
					portletRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				portletContext.getRequestDispatcher(
						portletResponse.encodeURL("/error.jsp")).include(
							portletRequest, portletResponse);
			}
			else {
				throw e;
			}
		}
	}

}