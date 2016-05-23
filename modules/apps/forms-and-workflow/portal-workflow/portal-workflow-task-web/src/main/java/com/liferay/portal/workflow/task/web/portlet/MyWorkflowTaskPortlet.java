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

package com.liferay.portal.workflow.task.web.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.task.web.configuration.WorkflowTaskWebConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Leonardo Barros
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.task.web.configuration.WorkflowTaskWebConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-workflow-tasks",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"com.liferay.portlet.friendly-url-mapping=my_workflow_tasks",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/my_workflow_task.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Workflow Tasks",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MyWorkflowTaskPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (StringUtil.equalsIgnoreCase(actionName, "invokeTaglibDiscussion")) {
			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		try {
			setWorkflowTaskRenderRequestAttribute(request);
		}
		catch (Exception e) {
			if (isSessionErrorException(e)) {
				hideDefaultErrorMessage(request);

				SessionErrors.add(request, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(request, response);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_workflowTaskWebConfiguration = ConfigurableUtil.createConfigurable(
			WorkflowTaskWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			hideDefaultErrorMessage(renderRequest);

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected boolean hasViewPermission(
		long groupId, WorkflowTask workflowTask,
		PermissionChecker permissionChecker) {

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isContentReviewer(
				permissionChecker.getCompanyId(), groupId)) {

			return true;
		}

		long[] roleIds = permissionChecker.getRoleIds(
			permissionChecker.getUserId(), groupId);

		for (WorkflowTaskAssignee workflowTaskAssignee :
				workflowTask.getWorkflowTaskAssignees()) {

			if (isWorkflowTaskAssignableToRoles(
					workflowTaskAssignee, roleIds) ||
				isWorkflowTaskAssignableToUser(
					workflowTaskAssignee, permissionChecker.getUserId())) {

				return true;
			}
		}

		return false;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof WorkflowException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

	protected boolean isWorkflowTaskAssignableToRoles(
		WorkflowTaskAssignee workflowTaskAssignee, long[] roleIds) {

		String assigneeClassName = workflowTaskAssignee.getAssigneeClassName();

		if (!assigneeClassName.equals(Role.class.getName())) {
			return false;
		}

		if (ArrayUtil.contains(
				roleIds, workflowTaskAssignee.getAssigneeClassPK())) {

			return true;
		}

		return false;
	}

	protected boolean isWorkflowTaskAssignableToUser(
		WorkflowTaskAssignee workflowTaskAssignee, long userId) {

		String assigneeClassName = workflowTaskAssignee.getAssigneeClassName();

		if (!assigneeClassName.equals(User.class.getName())) {
			return false;
		}

		if (workflowTaskAssignee.getAssigneeClassPK() == userId) {
			return true;
		}

		return false;
	}

	protected void setWorkflowTaskRenderRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long workflowTaskId = ParamUtil.getLong(
			renderRequest, "workflowTaskId");

		if (workflowTaskId > 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			WorkflowTask workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
				themeDisplay.getCompanyId(), workflowTaskId);

			if (!hasViewPermission(
					themeDisplay.getScopeGroupId(), workflowTask,
					permissionChecker)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, ActionKeys.VIEW);
			}

			renderRequest.setAttribute(WebKeys.WORKFLOW_TASK, workflowTask);
		}

		renderRequest.setAttribute(
			WorkflowTaskWebConfiguration.class.getName(),
			_workflowTaskWebConfiguration);
	}

	private volatile WorkflowTaskWebConfiguration _workflowTaskWebConfiguration;

}