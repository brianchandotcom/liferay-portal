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

package com.liferay.commerce.account.admin.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT_ADMIN,
		"mvc.command.name=editCommerceAccountOrganizationRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountOrganizationRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceAccountOrganizationRel(
			ActionRequest actionRequest)
		throws Exception {

		long[] addOrganizationIds;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		if (organizationId > 0) {
			addOrganizationIds = new long[] {organizationId};
		}
		else {
			addOrganizationIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "organizationIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountOrganizationRel.class.getName(), actionRequest);

		_commerceAccountOrganizationRelService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, addOrganizationIds, serviceContext);
	}

	protected void deleteCommerceAccountOrganizationRels(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceAccountOrganizationRelIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		if (organizationId > 0) {
			deleteCommerceAccountOrganizationRelIds = new long[] {
				organizationId
			};
		}
		else {
			deleteCommerceAccountOrganizationRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceAccountOrganizationRelIds"),
				0L);
		}

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, deleteCommerceAccountOrganizationRelIds);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceAccountOrganizationRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAccountOrganizationRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceAccountOrganizationRelMVCActionCommand.class);

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

}