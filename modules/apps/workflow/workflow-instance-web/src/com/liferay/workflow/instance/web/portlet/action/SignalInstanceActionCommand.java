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
package com.liferay.workflow.instance.web.portlet.action;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true,
	property = {
		"action.command.name=signalInstance",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_INSTANCE
	},
	service = ActionCommand.class)
public class SignalInstanceActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {

		try {
			signalInstance(portletRequest);
		} catch (Exception e) {
			if (e instanceof PrincipalException ||
				e instanceof WorkflowException) {
				
				SessionErrors.add(portletRequest, e.getClass());

				PortletSession portletSession =
						portletRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(
						portletRequest, portletResponse);

			} else {
				throw e;
			}
		}
	}

	protected void signalInstance(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowInstanceId = ParamUtil.getLong(
			portletRequest, "workflowInstanceId");

		String transitionName = ParamUtil.getString(
			portletRequest, "transitionName");

		WorkflowInstanceManagerUtil.signalWorkflowInstance(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowInstanceId, transitionName, null);
	}

}