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
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=updateRecordSet"
	},
	service = MVCActionCommand.class
)
public class UpdateRecordSetMVCActionCommand
	extends AddRecordSetMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDMStructure ddmStructure = updateDDMStructure(actionRequest);

		DDLRecordSet recordSet = updateRecordSet(
			actionRequest, ddmStructure.getStructureId());

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			actionRequest);

		updateRecordSetSettings(
			actionRequest, recordSet, settingsDDMFormValues);
	}

	protected DDMStructure updateDDMStructure(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		DDMForm ddmForm = getDDMForm(actionRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		return ddmStructureService.updateStructure(
			ddmStructureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			getLocalizedMap(themeDisplay.getLocale(), name),
			getLocalizedMap(themeDisplay.getLocale(), description), ddmForm,
			ddmFormLayout, serviceContext);
	}

	protected DDLRecordSet updateRecordSet(
			ActionRequest actionRequest, long ddmStructureId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), actionRequest);

		return ddlRecordSetService.updateRecordSet(
			recordSetId, ddmStructureId,
			getLocalizedMap(themeDisplay.getLocale(), name),
			getLocalizedMap(themeDisplay.getLocale(), description),
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, serviceContext);
	}

}