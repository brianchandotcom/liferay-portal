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
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY,
		"mvc.command.name=/dynamic_data_lists/update_record_set"
	},
	service = MVCActionCommand.class
)
public class UpdateRecordSetMVCActionCommand
	extends AddRecordSetMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDLRecordSet recordSet = _updateRecordSet(actionRequest);

		updateWorkflowDefinitionLink(actionRequest, recordSet);

		updatePortletPreferences(actionRequest, recordSet);
	}

	private DDLRecordSet _updateRecordSet(ActionRequest actionRequest)
		throws Exception {

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), actionRequest);

		return ddlRecordSetService.updateRecordSet(
			recordSetId, ddmStructureId, nameMap, descriptionMap,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, serviceContext);
	}

}