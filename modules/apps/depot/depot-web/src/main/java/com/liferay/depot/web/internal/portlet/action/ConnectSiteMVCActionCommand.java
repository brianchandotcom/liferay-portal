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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.exception.DepotEntryGroupRelStagedGroupException;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_SETTINGS,
		"mvc.command.name=/depot/connect_depot_entry"
	},
	service = MVCActionCommand.class
)
public class ConnectSiteMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		long depotEntryId = ParamUtil.getLong(actionRequest, "depotEntryId");
		long toGroupId = ParamUtil.getLong(actionRequest, "toGroupId");

		try {
			_depotEntryGroupRelService.addDepotEntryGroupRel(
				depotEntryId, toGroupId);
		}
		catch (Exception exception) {
			Throwable throwable = exception.getCause();

			if (throwable instanceof DepotEntryGroupRelStagedGroupException) {
				SessionErrors.add(
					actionRequest, DepotEntryGroupRelStagedGroupException.class,
					throwable);

				hideDefaultErrorMessage(actionRequest);

				actionResponse.sendRedirect(
					ParamUtil.getString(actionRequest, "redirect"));
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private DepotEntryGroupRelService _depotEntryGroupRelService;

}