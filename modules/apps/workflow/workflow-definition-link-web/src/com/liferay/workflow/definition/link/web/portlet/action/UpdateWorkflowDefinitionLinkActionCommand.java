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

package com.liferay.workflow.definition.link.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.workflow.definition.link.web.portlet.constants.WorkflowDefinitionLinkPortletKeys;

import java.util.Enumeration;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=updateWorkflowDefinitionLink",
		"javax.portlet.name=" +
			WorkflowDefinitionLinkPortletKeys.WORKFLOW_DEFINITION_LINK
	},
	service = ActionCommand.class
)
public class UpdateWorkflowDefinitionLinkActionCommand
	extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(_PREFIX)) {
				continue;
			}

			String className = name.substring(_PREFIX.length());
			String workflowDefinition = ParamUtil.getString(
				portletRequest, name);

			WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
				className, 0, 0, workflowDefinition);
		}
	}

	private static final String _PREFIX = "workflowDefinitionName@";

}