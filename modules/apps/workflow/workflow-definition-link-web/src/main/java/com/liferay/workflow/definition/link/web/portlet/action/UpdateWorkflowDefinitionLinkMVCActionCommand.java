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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.workflow.definition.link.web.portlet.constants.WorkflowDefinitionLinkPortletKeys;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowDefinitionLinkPortletKeys.WORKFLOW_DEFINITION_LINK,
		"javax.portlet.name=" + WorkflowDefinitionLinkPortletKeys.WORKFLOW_DEFINITION_LINK_CONTROL_PANEL,
		"mvc.command.name=updateWorkflowDefinitionLink"
	},
	service = MVCActionCommand.class
)
public class UpdateWorkflowDefinitionLinkMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
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

			_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
				className, 0, 0, workflowDefinition);
		}
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	private static final String _PREFIX = "workflowDefinitionName@";

	private volatile WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}