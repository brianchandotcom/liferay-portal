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

package com.liferay.workflow.definition.web.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.workflow.definition.web.portlet.constants.WorkflowDefinitionConstants;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=updateWorkflowDefinition",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_DEFINITIONS
	},
	service = ActionCommand.class
)
public class UpdateWorkflowDefitionActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			portletRequest, WorkflowDefinitionConstants.TITLE);

		File file = uploadPortletRequest.getFile(
			WorkflowDefinitionConstants.FILE);

		WorkflowDefinition workflowDefinition = null;

		String name = ParamUtil.getString(
			portletRequest, WorkflowDefinitionConstants.NAME);
		int version = ParamUtil.getInteger(
			portletRequest, WorkflowDefinitionConstants.VERSION);

		if (Validator.isNotNull(name)) {
			workflowDefinition =
				WorkflowDefinitionManagerUtil.getWorkflowDefinition(
					themeDisplay.getCompanyId(), name, version);

			WorkflowDefinitionManagerUtil.updateTitle(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, getTitle(titleMap));
		}
		else {
			if (file.length() == 0) {
				throw new WorkflowDefinitionFileException();
			}

			workflowDefinition =
				WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					getTitle(titleMap), FileUtil.getBytes(file));
		}

		portletRequest.setAttribute(
			WebKeys.WORKFLOW_DEFINITION, workflowDefinition);
	}

	protected String getTitle(Map<Locale, String> titleMap) {
		if (Validator.isNull(titleMap)) {
			return null;
		}

		String value = StringPool.BLANK;

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);
			String title = titleMap.get(locale);

			if (Validator.isNotNull(title)) {
				value = LocalizationUtil.updateLocalization(
					value, WorkflowDefinitionConstants.TITLE2, title,
					languageId);
			}
			else {
				value = LocalizationUtil.removeLocalization(
					value, WorkflowDefinitionConstants.TITLE2, languageId);
			}
		}

		return value;
	}

}