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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/export_user_associated_data"
	},
	service = MVCActionCommand.class
)
public class ExportUserAssociatedDataMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String uadRegistryKey = ParamUtil.getString(
			actionRequest, "uadRegistryKey");

		UADEntityExporter uadEntityExporter = _uadRegistry.getUADEntityExporter(
			uadRegistryKey);

		long selUserId = ParamUtil.getLong(actionRequest, "selUserId");

		uadEntityExporter.exportAll(selUserId);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Reference
	private UADRegistry _uadRegistry;

}