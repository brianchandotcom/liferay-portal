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

package com.liferay.dynamic.data.lists.form.web.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=updateRecordSetSettings"
	},
	service = MVCActionCommand.class
)
public class UpdateRecordSetSettingsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultErrorMessage(actionRequest);

		updateRecordSetSettings(actionRequest);
		updateWorkflowDefinitionLink(actionRequest);
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	protected void updateRecordSetEmailNotificationSettings(
		ActionRequest actionRequest, UnicodeProperties settingsProperties) {

		boolean sendEmailNotification = ParamUtil.getBoolean(
			actionRequest, "sendEmailNotification");

		String emailFromName = ParamUtil.getString(
			actionRequest, "emailFromName");
		String emailFromAddress = ParamUtil.getString(
			actionRequest, "emailFromAddress");
		String emailToAddress = ParamUtil.getString(
			actionRequest, "emailToAddress");
		String emailSubject = ParamUtil.getString(
			actionRequest, "emailSubject");

		settingsProperties.setProperty(
			"sendEmailNotification", String.valueOf(sendEmailNotification));

		if (sendEmailNotification) {
			settingsProperties.setProperty(
				"emailFromAddress", emailFromAddress);
			settingsProperties.setProperty("emailFromName", emailFromName);
			settingsProperties.setProperty("emailToAddress", emailToAddress);
			settingsProperties.setProperty("emailSubject", emailSubject);
		}
	}

	protected void updateRecordSetRedirectURLSettings(
		ActionRequest actionRequest, UnicodeProperties settingsProperties) {

		String redirectURL = ParamUtil.getString(actionRequest, "redirectURL");

		settingsProperties.setProperty("redirectURL", redirectURL);
	}

	protected void updateRecordSetRequireCaptchaSettings(
		ActionRequest actionRequest, UnicodeProperties settingsProperties) {

		boolean requireCaptcha = ParamUtil.getBoolean(
			actionRequest, "requireCaptcha");

		settingsProperties.setProperty(
			"requireCaptcha", String.valueOf(requireCaptcha));
	}

	protected void updateRecordSetSettings(ActionRequest actionRequest)
		throws PortalException {

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		UnicodeProperties settingsProperties = new UnicodeProperties(true);

		updateRecordSetRedirectURLSettings(actionRequest, settingsProperties);
		updateRecordSetRequireCaptchaSettings(
			actionRequest, settingsProperties);
		updateRecordSetEmailNotificationSettings(
			actionRequest, settingsProperties);

		_ddlRecordSetService.updateRecordSet(
			recordSetId, settingsProperties.toString());
	}

	protected void updateWorkflowDefinitionLink(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String workflowDefinition = ParamUtil.getString(
			actionRequest, "workflowDefinition");

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0,
			workflowDefinition);
	}

	private volatile DDLRecordSetService _ddlRecordSetService;
	private volatile WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}