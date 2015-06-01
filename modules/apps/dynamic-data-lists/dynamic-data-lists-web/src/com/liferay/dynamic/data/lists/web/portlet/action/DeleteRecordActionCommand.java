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

package com.liferay.dynamic.data.lists.web.portlet.action;

import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.lists.web.constants.DDLPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=deleteRecord",
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS
	},
	service = ActionCommand.class
)
public class DeleteRecordActionCommand extends BaseActionCommand {

	@Reference
	public void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long recordId = ParamUtil.getLong(portletRequest, "recordId");

		_ddlRecordService.deleteRecord(recordId);
	}

	private DDLRecordService _ddlRecordService;

}