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
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"mvc.command.name=updateWorkflowDefinitionDraft"
	},
	service = MVCActionCommand.class
)
public class UpdateWorkflowDefinitionDraftMVCActionCommand
	extends UpdateWorkflowDefinitionMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		String name = ParamUtil.getString(actionRequest, "name");

		if (Validator.isNull(name)) {
			name = portalUUID.generate();
		}

		String content = ParamUtil.getString(actionRequest, "content");

		WorkflowDefinition workflowDefinition =
			workflowDefinitionManager.draftWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(titleMap), name, content.getBytes());

		addSuccessMessage(actionRequest, actionResponse);

		setRedirectAttribute(actionRequest, workflowDefinition);

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	protected String getSuccessMessage(ResourceBundle resourceBundle) {
		return LanguageUtil.get(resourceBundle, "workflow-saved");
	}

	@Reference
	protected PortalUUID portalUUID;

}