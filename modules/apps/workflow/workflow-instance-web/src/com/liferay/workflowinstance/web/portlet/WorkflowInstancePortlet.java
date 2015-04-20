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

package com.liferay.workflowinstance.web.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.workflowinstance.web.portlet.action.ActionUtil;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true,
	property = {
		"com.liferay.portlet.icon=/icons/workflow_instances.png",
		"com.liferay.portlet.control-panel-entry-category=configuration",
		"com.liferay.portlet.control-panel-entry-weight=4.0",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Workflow Instances",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_INSTANCES,
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = { WorkflowInstancePortlet.class, Portlet.class })
public class WorkflowInstancePortlet extends MVCPortlet {
	
	@Override
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) 
					throws IOException, PortletException {
		
		String actionName = ParamUtil.getString(
				actionRequest, ActionRequest.ACTION_NAME);
		
		if(Validator.isNotNull(actionName) && StringUtil.equalsIgnoreCase(
				actionName, _DISCUSSION_ACTION)) {
			SessionMessages.add(actionRequest, 
					getPortletConfig().getPortletName() + 
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
		
		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		try {
			ActionUtil.getWorkflowInstance(request);
		} catch (Exception e) {
			if (e instanceof WorkflowException) {
				
				SessionErrors.add(request, e.getClass());

				PortletSession portletSession =
					request.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(
					request, response);

			} else {
				throw new PortletException(e);
			}
		}

		super.render(request, response);
	}
	
	private static final String _DISCUSSION_ACTION = "invokeTaglibDiscussion";

}
