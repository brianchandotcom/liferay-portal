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
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=publishKaleoDefinitionVersion"
	},
	service = MVCActionCommand.class
)
public class PublishKaleoDefinitionVersionMVCActionCommand
	extends BaseKaleoDesignerMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			portal.getLocale(actionRequest));

		String successMessage = getSuccessMessage(
			actionRequest, resourceBundle);

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

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

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNull(content)) {
			throw new WorkflowDefinitionFileException();
		}

		validateWorkflowDefinition(content.getBytes());

		String name = ParamUtil.getString(actionRequest, "name");

		KaleoDefinitionVersion latestKaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.
				fetchLatestKaleoDefinitionVersion(
					themeDisplay.getCompanyId(), name, null);

		if (Validator.isNull(latestKaleoDefinitionVersion) ||
			latestKaleoDefinitionVersion.isDraft()) {

			actionRequest.setAttribute(
				KaleoDesignerWebKeys.PUBLISH_DEFINITION_ACTION, Boolean.TRUE);
		}

		WorkflowDefinition workflowDefinition =
			workflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(titleMap), name, content.getBytes());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), workflowDefinition.getName());

		actionRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);

		setRedirectAttribute(actionRequest, kaleoDefinitionVersion);
	}

	protected String getSuccessMessage(
		ActionRequest actionRequest, ResourceBundle resourceBundle) {

		boolean definitionPublishing = GetterUtil.getBoolean(
			actionRequest.getAttribute(
				KaleoDesignerWebKeys.PUBLISH_DEFINITION_ACTION));

		if (definitionPublishing) {
			return LanguageUtil.get(
				resourceBundle, "workflow-published-successfully");
		}
		else {
			return LanguageUtil.get(
				resourceBundle, "workflow-updated-successfully");
		}
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		return LanguageUtil.get(
			resourceBundle, "workflow-updated-successfully");
	}

	@Override
	protected String getTitle(Map<Locale, String> titleMap)
		throws WorkflowException {

		String title = super.getTitle(titleMap);

		if (Validator.isNull(title)) {
			throw new WorkflowDefinitionTitleException();
		}

		return title;
	}

	protected void validateWorkflowDefinition(byte[] bytes)
		throws WorkflowDefinitionFileException {

		try {
			workflowDefinitionManager.validateWorkflowDefinition(bytes);
		}
		catch (WorkflowException we) {
			throw new WorkflowDefinitionFileException(we);
		}
	}

}