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

package com.liferay.portal.workflow.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.display.context.WorkflowNavigationDisplayContext;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-workflow",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"com.liferay.portlet.friendly-url-mapping=my_workflow",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/workflow.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Submissions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + WorkflowPortletKeys.USER_WORKFLOW,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UserWorkflowPortlet extends BaseWorkflowPortlet {

	@Override
	public List<String> getWorkflowPortletTabNames() {
		return Arrays.asList(WorkflowWebKeys.WORKFLOW_TAB_MY_SUBMISSIONS);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			_checkWorkflowInstanceViewPermission(renderRequest);
		}
		catch (PortalException portalException) {
			if (portalException instanceof PrincipalException ||
				portalException instanceof WorkflowException) {

				SessionErrors.add(renderRequest, portalException.getClass());
			}
			else {
				throw new PortletException(portalException);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	protected void addRenderRequestAttributes(RenderRequest renderRequest) {
		super.addRenderRequestAttributes(renderRequest);

		WorkflowNavigationDisplayContext workflowNavigationDisplayContext =
			new WorkflowNavigationDisplayContext(
				renderRequest,
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader());

		renderRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_NAVIGATION_DISPLAY_CONTEXT,
			workflowNavigationDisplayContext);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			include("/instance/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	private void _checkWorkflowInstanceViewPermission(
			RenderRequest renderRequest)
		throws PortalException {

		long workflowInstanceId = ParamUtil.getLong(
			renderRequest, "workflowInstanceId");

		if (workflowInstanceId != 0) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					permissionChecker.getCompanyId(),
					permissionChecker.getUserId(), workflowInstanceId);

			if (workflowInstance == null) {
				throw new PrincipalException.MustHavePermission(
					permissionChecker, WorkflowInstance.class.getName(),
					workflowInstanceId, ActionKeys.VIEW);
			}
		}
	}

}