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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"mvc.command.name=revertWorkflowDefinition"
	},
	service = MVCActionCommand.class
)
public class RevertWorkflowDefinitionMVCActionCommand
	extends DeployWorkflowDefinitionMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			doProcessAction(actionRequest, actionResponse);

			addSuccessMessage(actionRequest, actionResponse);

			return SessionErrors.isEmpty(actionRequest);
		}
		catch (WorkflowException we) {
			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, we.getClass(), we);

			actionResponse.setRenderParameter(
				"mvcPath", "/definition/edit_workflow_definition.jsp");

			return false;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	/**
	 * Reverts a workflow definition to the published state, creating a new
	 * version of it.
	 *
	 * @param actionRequest the action request from which to retrieve the
	 *        workflow definition name and version
	 * @param actionResponse the action response
	 */
	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String previousName = ParamUtil.getString(
			actionRequest, "previousName");
		int previousVersion = ParamUtil.getInteger(
			actionRequest, "previousVersion");

		WorkflowDefinition previousWorkflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				themeDisplay.getCompanyId(), previousName, previousVersion);

		actionRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE,
			previousWorkflowDefinition.getModifiedDate());

		String content = GetterUtil.get(
			previousWorkflowDefinition.getContent(), StringPool.BLANK);

		WorkflowDefinition workflowDefinition = null;

		if (previousWorkflowDefinition.isActive()) {
			validateWorkflowDefinition(
				actionRequest, content.getBytes("UTF-8"),
				themeDisplay.getLocale(),
				previousWorkflowDefinition.getModifiedDate());

			workflowDefinition =
				workflowDefinitionManager.deployWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					previousWorkflowDefinition.getTitle(), previousName,
					content.getBytes());
		}
		else {
			workflowDefinition =
				workflowDefinitionManager.saveWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					previousWorkflowDefinition.getTitle(), previousName,
					content.getBytes());
		}

		setRedirectAttribute(actionRequest, workflowDefinition);

		sendRedirect(actionRequest, actionResponse);
	}

	/**
	 * Returns a success message for the revert workflow definition action
	 *
	 * @param  actionRequest the action request
	 * @return the success message
	 */
	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		DateFormat dateFormat = _getDateFormat(locale);

		Date workflowDefinitionModifiedDate = GetterUtil.getDate(
			actionRequest.getAttribute(
				WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE),
			dateFormat);

		return LanguageUtil.format(
			resourceBundle, "restored-to-revision-from-x",
			dateFormat.format(workflowDefinitionModifiedDate));
	}

	protected void validateWorkflowDefinition(
			ActionRequest actionRequest, byte[] bytes, Locale locale,
			Date previousDateModification)
		throws WorkflowDefinitionFileException {

		try {
			workflowDefinitionManager.validateWorkflowDefinition(bytes);
		}
		catch (WorkflowException we) {
			DateFormat dateFormat = _getDateFormat(locale);

			String message = LanguageUtil.format(
				getResourceBundle(actionRequest),
				"the-version-from-x-is-not-valid-for-publication",
				dateFormat.format(previousDateModification));

			throw new WorkflowDefinitionFileException(message, we);
		}
	}

	private DateFormat _getDateFormat(Locale locale) {
		if (DateUtil.isFormatAmPm(locale)) {
			return DateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy, hh:mm a", locale);
		}

		return DateFormatFactoryUtil.getSimpleDateFormat(
			"MMM d, yyyy, HH:mm", locale);
	}

}