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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import java.util.Locale;
import java.util.Map;
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
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"mvc.command.name=duplicateWorkflowDefinition"
	},
	service = MVCActionCommand.class
)
public class DuplicateWorkflowDefinitionMVCActionCommand
	extends DeployWorkflowDefinitionMVCActionCommand {

	@Override
	protected void addDefaultTitle(
		ActionRequest actionRequest, Map<Locale, String> titleMap) {

		String title = titleMap.get(LocaleUtil.getDefault());

		if (titleMap.isEmpty() || Validator.isNull(title)) {
			title = ParamUtil.getString(
				actionRequest, "defaultDuplicationTitle");

			titleMap.put(LocaleUtil.getDefault(), title);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String randomNamespace = ParamUtil.getString(
			actionRequest, "randomNamespace");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, randomNamespace + "title");

		String name = ParamUtil.getString(actionRequest, "name");
		String content = ParamUtil.getString(actionRequest, "content");

		WorkflowDefinition workflowDefinition =
			workflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(actionRequest, titleMap), name, content.getBytes());

		setRedirectAttribute(actionRequest, workflowDefinition);

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		String duplicatedDefinitionName = ParamUtil.getString(
			actionRequest, "duplicatedDefinitionTitle");

		return LanguageUtil.format(
			resourceBundle, "duplicated-from-x",
			StringUtil.quote(duplicatedDefinitionName));
	}

}