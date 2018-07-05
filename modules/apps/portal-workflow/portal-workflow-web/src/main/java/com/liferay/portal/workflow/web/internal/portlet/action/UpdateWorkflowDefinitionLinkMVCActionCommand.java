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

package com.liferay.portal.workflow.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.definition.link.update.handler.WorkflowDefinitionLinkUpdateHandler;
import com.liferay.portal.workflow.definition.link.update.handler.WorkflowDefinitionLinkUpdateHandlerRegistryUtil;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

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
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"javax.portlet.name=" + WorkflowPortletKeys.SITE_ADMINISTRATION_WORKFLOW,
		"mvc.command.name=updateWorkflowDefinitionLink"
	},
	service = MVCActionCommand.class
)
public class UpdateWorkflowDefinitionLinkMVCActionCommand
	extends BaseWorkflowDefinitionMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String workflowDefinition = (String)actionRequest.getAttribute(
			WorkflowWebKeys.WORKFLOW_DEFINITION_NAME);

		String resource = ParamUtil.getString(actionRequest, "resource");

		String successMessage = StringPool.BLANK;

		if (Validator.isNull(workflowDefinition)) {
			successMessage = LanguageUtil.format(
				getResourceBundle(actionRequest), "workflow-unassigned-from-x",
				resource);
		}
		else {
			successMessage = LanguageUtil.format(
				getResourceBundle(actionRequest), "workflow-assigned-to-x",
				resource);
		}

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String[] classNameAndWorkflowDefinition =
			getClassNameAndWorkflowDefinition(actionRequest);

		String className = classNameAndWorkflowDefinition[0];
		String workflowDefinition = classNameAndWorkflowDefinition[1];

		WorkflowDefinitionLinkUpdateHandler
			workflowDefinitionLinkUpdateHandler =
				WorkflowDefinitionLinkUpdateHandlerRegistryUtil.
					getWorkflowDefinitionLinkUpdateHandler(className);

		if (workflowDefinitionLinkUpdateHandler != null) {
			workflowDefinitionLinkUpdateHandler.updatedWorkflowDefinitionLink(
				workflowDefinition);
		}

		if (Validator.isNotNull(className)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
				className, 0, 0, workflowDefinition);

			actionRequest.setAttribute(
				WorkflowWebKeys.WORKFLOW_DEFINITION_NAME, workflowDefinition);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	protected String[] getClassNameAndWorkflowDefinition(
		ActionRequest actionRequest) {

		String className = StringPool.BLANK;
		String workflowDefinition = StringPool.BLANK;

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(_PREFIX)) {
				continue;
			}

			className = name.substring(_PREFIX.length());
			workflowDefinition = ParamUtil.getString(actionRequest, name);

			break;
		}

		return new String[] {className, workflowDefinition};
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	private static final String _PREFIX = "workflowDefinitionName@";

	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}