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

package com.liferay.on.demand.admin.web.internal.portlet.action;

import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.on.demand.admin.web.internal.constants.OnDemandAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + OnDemandAdminPortletKeys.ON_DEMAND_ADMIN,
		"mvc.command.name=/on_demand_admin/request_admin_access"
	},
	service = MVCActionCommand.class
)
public class RequestAdminAccessMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		String loginURL = _onDemandAdminManager.getLoginURL(
			_companyLocalService.getCompany(companyId),
			_portal.getUserId(actionRequest));

		if (Validator.isNotNull(loginURL)) {
			sendRedirect(actionRequest, actionResponse, loginURL);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private OnDemandAdminManager _onDemandAdminManager;

	@Reference
	private Portal _portal;

}