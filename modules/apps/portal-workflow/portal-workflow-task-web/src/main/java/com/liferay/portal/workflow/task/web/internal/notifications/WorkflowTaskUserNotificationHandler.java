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

package com.liferay.portal.workflow.task.web.internal.notifications;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.task.web.internal.permission.WorkflowTaskPermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
	service = UserNotificationHandler.class
)
public class WorkflowTaskUserNotificationHandler
	extends BaseUserNotificationHandler {

	public WorkflowTaskUserNotificationHandler() {
		setOpenDialog(true);
		setPortletId(PortletKeys.MY_WORKFLOW_TASK);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long workflowTaskId = jsonObject.getLong("workflowTaskId");

		if (workflowTaskId > 0) {
			WorkflowTask workflowTask = _fetchWorkflowTask(
				workflowTaskId, serviceContext);

			if (workflowTask == null) {
				_userNotificationEventLocalService.deleteUserNotificationEvent(
					userNotificationEvent.getUserNotificationEventId());

				return StringPool.BLANK;
			}
		}

		return HtmlUtil.escape(jsonObject.getString("notificationMessage"));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				jsonObject.getString("entryClassName"));

		long workflowTaskId = jsonObject.getLong("workflowTaskId");

		if ((workflowHandler == null) ||
			!_hasPermission(workflowTaskId, serviceContext)) {

			return StringPool.BLANK;
		}

		serviceContext.setAttribute(
			WorkflowConstants.CONTEXT_URL,
			jsonObject.getString(WorkflowConstants.CONTEXT_URL));

		return workflowHandler.getURLEditWorkflowTask(
			workflowTaskId, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	private WorkflowTask _fetchWorkflowTask(
			long workflowTaskId, ServiceContext serviceContext)
		throws Exception {

		if (workflowTaskId <= 0) {
			return null;
		}

		return WorkflowTaskManagerUtil.fetchWorkflowTask(
			serviceContext.getCompanyId(), workflowTaskId);
	}

	private boolean _hasPermission(
			long workflowTaskId, ServiceContext serviceContext)
		throws Exception {

		WorkflowTask workflowTask = _fetchWorkflowTask(
			workflowTaskId, serviceContext);

		if (workflowTask == null) {
			return false;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		long groupId = MapUtil.getLong(
			workflowTask.getOptionalAttributes(), "groupId",
			themeDisplay.getSiteGroupId());

		return _workflowTaskPermissionChecker.hasPermission(
			groupId, workflowTask, themeDisplay.getPermissionChecker());
	}

	private UserNotificationEventLocalService
		_userNotificationEventLocalService;
	private final WorkflowTaskPermissionChecker _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionChecker();

}