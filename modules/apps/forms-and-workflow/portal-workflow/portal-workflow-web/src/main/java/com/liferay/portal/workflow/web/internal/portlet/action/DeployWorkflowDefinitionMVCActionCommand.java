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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionTitleException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"mvc.command.name=deployWorkflowDefinition"
	},
	service = MVCActionCommand.class
)
public class DeployWorkflowDefinitionMVCActionCommand
	extends BaseWorkflowDefinitionMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		String title = titleMap.get(LocaleUtil.getDefault());

		if (titleMap.isEmpty() || Validator.isNull(title)) {
			throw new WorkflowDefinitionTitleException();
		}

		String name = ParamUtil.getString(actionRequest, "name");

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNull(content)) {
			throw new WorkflowDefinitionFileException(
				"please-enter-a-valid-definition-before-publishing");
		}

		validateWorkflowDefinition(actionRequest, content.getBytes());

		WorkflowDefinition latestWorkflowDefinition =
			getLatestWorkflowDefinition(themeDisplay.getCompanyId(), name);

		if ((latestWorkflowDefinition == null) ||
			!latestWorkflowDefinition.isActive()) {

			actionRequest.setAttribute(
				WorkflowWebKeys.WORKFLOW_PUBLISH_DEFINITION_ACTION,
				Boolean.TRUE);
		}

		WorkflowDefinition workflowDefinition =
			workflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(titleMap), name, content.getBytes());

		setRedirectAttribute(actionRequest, workflowDefinition);

		sendRedirect(actionRequest, actionResponse);
	}

	protected WorkflowDefinition getLatestWorkflowDefinition(
		long companyId, String name) {

		try {
			return workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId, name);
		}
		catch (WorkflowException we) {
			return null;
		}
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		boolean definitionPublishing = GetterUtil.getBoolean(
			actionRequest.getAttribute(
				WorkflowWebKeys.WORKFLOW_PUBLISH_DEFINITION_ACTION));

		if (definitionPublishing) {
			return LanguageUtil.get(
				resourceBundle, "workflow-published-successfully");
		}
		else {
			return LanguageUtil.get(
				resourceBundle, "workflow-updated-successfully");
		}
	}

	protected void setRedirectAttribute(
			ActionRequest actionRequest, WorkflowDefinition workflowDefinition)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, themeDisplay.getPpid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcPath", "/definition/edit_workflow_definition.jsp");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect, false);

		portletURL.setParameter("name", workflowDefinition.getName(), false);
		portletURL.setParameter(
			"version", String.valueOf(workflowDefinition.getVersion()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	protected void validateWorkflowDefinition(
			ActionRequest actionRequest, byte[] bytes)
		throws WorkflowDefinitionFileException {

		try {
			workflowDefinitionManager.validateWorkflowDefinition(bytes);
		}
		catch (WorkflowException we) {
			String message = LanguageUtil.get(
				getResourceBundle(actionRequest),
				"please-enter-a-valid-definition-before-publishing");

			throw new WorkflowDefinitionFileException(message, we);
		}
	}

}