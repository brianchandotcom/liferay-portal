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

package com.liferay.commerce.account.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.NoSuchAccountOrganizationRelException;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT,
		"mvc.command.name=editCommerceAccountOrganizationRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountOrganizationRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void assignOrganization(ActionRequest actionRequest)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addOrganizationIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountOrganizationRel.class.getName(), actionRequest);

		_commerceAccountOrganizationRelService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, addOrganizationIds, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.REMOVE)) {
				removeOrganizations(actionRequest);
			}
			else if (cmd.equals(Constants.ASSIGN)) {
				assignOrganization(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountOrganizationRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected void removeOrganizations(ActionRequest actionRequest)
		throws PortalException {

		long[] removeOrganizationIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		if (organizationId > 0) {
			removeOrganizationIds = new long[] {organizationId};
		}
		else {
			removeOrganizationIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "removeOrganizationIds"),
				0L);
		}

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, removeOrganizationIds);
	}

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

}