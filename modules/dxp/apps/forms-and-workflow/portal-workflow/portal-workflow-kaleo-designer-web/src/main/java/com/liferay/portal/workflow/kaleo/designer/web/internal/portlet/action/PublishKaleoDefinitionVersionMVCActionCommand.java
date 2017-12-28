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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
	extends AddKaleoDefinitionVersionMVCActionCommand {

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

		WorkflowDefinition workflowDefinition =
			workflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(titleMap), content.getBytes());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), workflowDefinition.getName());

		actionRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);
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