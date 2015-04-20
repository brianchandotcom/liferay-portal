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

package com.liferay.workflowdefinitionlinks.web.portlet.action;

import java.util.Enumeration;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true,
	property = {
		"action.command.name=updateWorkflowDefinitionLinks",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_CONFIGURATION
	},
	service = ActionCommand.class)
public class UpdateWorkflowDefinitionLinksActionCommand extends
		BaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		try {
			updateWorkflowDefinitionLinks(portletRequest);
		}catch (Exception e) {
			if (e instanceof WorkflowException) {
				SessionErrors.add(portletRequest, e.getClass());

				PortletSession portletSession = portletRequest
					.getPortletSession();

				PortletContext portletContext = portletSession
					.getPortletContext();

				portletContext.getRequestDispatcher(
						portletResponse.encodeURL("/error.jsp")).include(
						portletRequest, portletResponse);
			}
			else {
				throw e;
			}
		}
	}

	protected void updateWorkflowDefinitionLinks(PortletRequest actionRequest)
			throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(_PREFIX)) {
				continue;
			}

			String className = name.substring(_PREFIX.length());
			String workflowDefinition = ParamUtil.getString(
				actionRequest, name);

			WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
				className, 0, 0, workflowDefinition);
		}
	}

	private static final String _PREFIX = "workflowDefinitionName@";
}
