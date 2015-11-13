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

package com.liferay.workflow.task.web.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
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

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof WorkflowException ||
			cause instanceof PrincipalException) {

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

			WorkflowTask workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
				themeDisplay.getCompanyId(), workflowTaskId);

			renderRequest.setAttribute(WebKeys.WORKFLOW_TASK, workflowTask);
		}
	}

}