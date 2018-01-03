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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=revertKaleoDefinitionVersion"
	},
	service = MVCActionCommand.class
)
public class RevertWorkflowDefinitionMVCActionCommand
	extends AddKaleoDefinitionVersionMVCActionCommand {

	/**
	 * Adds a success message to the workflow definition reversion action
	 *
	 * @param  actionRequest The actionRequest object of the action
	 * @review
	 */
	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		DateFormat dateTimeFormat = null;

		if (DateUtil.isFormatAmPm(locale)) {
			dateTimeFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy, hh:mm a", locale);
		}
		else {
			dateTimeFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy, HH:mm", locale);
		}

		Date workflowDefinitionModifiedDate = ParamUtil.getDate(
			actionRequest, WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE,
			dateTimeFormat);

		String dateTime = dateTimeFormat.format(workflowDefinitionModifiedDate);

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		SessionMessages.add(
			actionRequest, "requestProcessed",
			LanguageUtil.format(
				resourceBundle, "restored-to-revision-from-x", dateTime));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String draftVersion = ParamUtil.getString(
			actionRequest, "draftVersion");

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), name, draftVersion);

		actionRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE,
			kaleoDefinitionVersion.getModifiedDate());

		String content = kaleoDefinitionVersion.getContent();

		workflowDefinitionManager.deployWorkflowDefinition(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			kaleoDefinitionVersion.getTitle(), content.getBytes());

		sendRedirect(actionRequest, actionResponse);
	}

}