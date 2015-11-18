/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflowtasks.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * @author Jonathan Lee
 */
public class WorkflowTasksUserNotificationHandler
	extends BaseUserNotificationHandler {

	public WorkflowTasksUserNotificationHandler() {
		setPortletId(PortletKeys.MY_WORKFLOW_TASKS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long workflowTaskId = jsonObject.getLong("workflowTaskId");

		try {
			WorkflowTaskManagerUtil.getWorkflowTask(
				serviceContext.getCompanyId(), workflowTaskId);
		}
		catch (WorkflowException we) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		return HtmlUtil.escape(jsonObject.getString("notificationMessage"));
	}

	protected String getKaleoProcessLink(
			long workflowTaskId, ServiceContext serviceContext)
		throws Exception {

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			serviceContext.getRequest(), PortletKeys.KALEO_FORMS,
			serviceContext.getPlid(), PortletRequest.RENDER_PHASE);

		String currentURL = portletURL.toString();

		portletURL.setParameter("tabs2", "edit-workflow-task");
		portletURL.setParameter("backURL", currentURL);
		portletURL.setParameter(
			"workflowTaskId", String.valueOf(workflowTaskId));
		portletURL.setWindowState(WindowState.NORMAL);

		return portletURL.toString();
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		String entryClassName = jsonObject.getString("entryClassName");

		if (Validator.equals(entryClassName, _KALEO_PROCESS_CLASS_NAME)) {
			return getKaleoProcessLink(
				jsonObject.getLong("workflowTaskId"), serviceContext);
		}

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			serviceContext.getRequest(), PortletKeys.MY_WORKFLOW_TASKS,
			PortalUtil.getControlPanelPlid(serviceContext.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setControlPanelCategory("my");
		portletURL.setParameter(
			"struts_action", "/my_workflow_tasks/edit_workflow_task");
		portletURL.setParameter(
			"workflowTaskId", jsonObject.getString("workflowTaskId"));
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL.toString();
	}

	private static final String _KALEO_PROCESS_CLASS_NAME =
		"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess";

}