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

package com.liferay.dynamic.data.lists.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletPreferencesException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.StrictPortletPreferencesImpl;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
		"mvc.command.name=addRecordSet"
	},
	service = MVCActionCommand.class
)
public class AddRecordSetMVCActionCommand extends BaseMVCActionCommand {

	protected DDLRecordSet addRecordSet(ActionRequest actionRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), actionRequest);

		return ddlRecordSetService.addRecordSet(
			groupId, ddmStructureId, null, nameMap, descriptionMap,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDLRecordSet recordSet = addRecordSet(actionRequest);

		updateWorkflowDefinitionLink(actionRequest, recordSet);

		updatePortletPreferences(actionRequest, recordSet);
	}

	protected PortletPreferences getStrictPortletSetup(
			ActionRequest actionRequest)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getStrictPortletSetup(themeDisplay.getLayout(), portletResource);
	}

	protected PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws PortalException {

		if (Validator.isNull(portletId)) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			throw new PortletPreferencesException.MustBeStrict(portletId);
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		this.ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		this.workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	protected void updatePortletPreferences(
			ActionRequest actionRequest, DDLRecordSet recordSet)
		throws Exception {

		PortletPreferences portletPreferences = getStrictPortletSetup(
			actionRequest);

		if (portletPreferences == null) {
			return;
		}

		portletPreferences.reset("displayDDMTemplateId");
		portletPreferences.reset("editable");
		portletPreferences.reset("formDDMTemplateId");
		portletPreferences.reset("spreadsheet");

		portletPreferences.setValue(
			"recordSetId", String.valueOf(recordSet.getRecordSetId()));

		portletPreferences.store();
	}

	protected void updateWorkflowDefinitionLink(
			ActionRequest actionRequest, DDLRecordSet recordSet)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String workflowDefinition = ParamUtil.getString(
			actionRequest, "workflowDefinition");

		workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0,
			workflowDefinition);
	}

	protected DDLRecordSetService ddlRecordSetService;
	protected WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

}