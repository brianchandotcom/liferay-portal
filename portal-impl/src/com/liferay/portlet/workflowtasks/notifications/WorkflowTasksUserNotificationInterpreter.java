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
import com.liferay.portal.kernel.notifications.BaseUserNotificationInterpreter;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * @author Jonathan Lee
 */
public class WorkflowTasksUserNotificationInterpreter
	extends BaseUserNotificationInterpreter {

	public WorkflowTasksUserNotificationInterpreter() {
		setPortletId(PortletKeys.MY_WORKFLOW_TASKS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		return HtmlUtil.escape(jsonObject.getString("notificationMessage"));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			serviceContext.getRequest(), PortletKeys.MY_WORKFLOW_TASKS,
			PortalUtil.getControlPanelPlid(serviceContext.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);
		portletURL.setControlPanelCategory("my");

		portletURL.setParameter(
			"struts_action", "/my_workflow_tasks/edit_workflow_task");
		portletURL.setParameter(
			"workflowTaskId", jsonObject.getString("workflowInstanceId"));

		return portletURL.toString();
	}

}