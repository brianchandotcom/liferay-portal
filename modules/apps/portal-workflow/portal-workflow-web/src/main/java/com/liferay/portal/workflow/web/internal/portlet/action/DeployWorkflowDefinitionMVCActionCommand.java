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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionTitleException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"mvc.command.name=/portal_workflow/deploy_workflow_definition"
	},
	service = MVCActionCommand.class
)
public class DeployWorkflowDefinitionMVCActionCommand
	extends BaseWorkflowDefinitionMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Map<Locale, String> titleMap = localization.getLocalizationMap(
			actionRequest, "title");

		validateTitle(actionRequest, titleMap);

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNull(content)) {
			throw new WorkflowDefinitionFileException(
				"please-enter-a-valid-definition-before-publishing");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		validateWorkflowDefinition(actionRequest, content.getBytes());

		String name = ParamUtil.getString(actionRequest, "name");

		WorkflowDefinition latestWorkflowDefinition =
			getLatestWorkflowDefinition(themeDisplay.getCompanyId(), name);

		if ((latestWorkflowDefinition == null) ||
			!latestWorkflowDefinition.isActive()) {

			actionRequest.setAttribute(
				WorkflowWebKeys.WORKFLOW_PUBLISH_DEFINITION_ACTION,
				Boolean.TRUE);
		}

		WorkflowDefinition workflowDefinition =
			unproxiedWorkflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(actionRequest, titleMap), name, content.getBytes());

		setRedirectAttribute(actionRequest, workflowDefinition);

		sendRedirect(actionRequest, actionResponse);
	}

	protected WorkflowDefinition getLatestWorkflowDefinition(
		long companyId, String name) {

		try {
			return unproxiedWorkflowDefinitionManager.
				getLatestWorkflowDefinition(companyId, name);
		}
		catch (WorkflowException workflowException) {
			if (_log.isDebugEnabled()) {
				_log.debug(workflowException);
			}

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
			return language.get(
				resourceBundle, "workflow-published-successfully");
		}

		return language.get(resourceBundle, "workflow-updated-successfully");
	}

	protected void validateTitle(
			ActionRequest actionRequest, Map<Locale, String> titleMap)
		throws WorkflowDefinitionTitleException {

		String title = titleMap.get(LocaleUtil.getDefault());

		if (titleMap.isEmpty() || Validator.isNull(title)) {
			throw new WorkflowDefinitionTitleException();
		}
	}

	protected void validateWorkflowDefinition(
			ActionRequest actionRequest, byte[] bytes)
		throws WorkflowDefinitionFileException {

		try {
			unproxiedWorkflowDefinitionManager.validateWorkflowDefinition(
				bytes);
		}
		catch (WorkflowException workflowException) {
			String message = language.get(
				getResourceBundle(actionRequest),
				"please-enter-a-valid-definition-before-publishing");

			throw new WorkflowDefinitionFileException(
				message, workflowException);
		}
	}

	@Reference
	protected Language language;

	@Reference
	protected Localization localization;

	@Reference
	protected WorkflowDefinitionManager unproxiedWorkflowDefinitionManager;

	private static final Log _log = LogFactoryUtil.getLog(
		DeployWorkflowDefinitionMVCActionCommand.class);

}