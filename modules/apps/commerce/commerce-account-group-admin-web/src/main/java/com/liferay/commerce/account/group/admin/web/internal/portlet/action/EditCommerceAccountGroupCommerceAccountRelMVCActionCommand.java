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

package com.liferay.commerce.account.group.admin.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.NoSuchAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelService;
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
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT_GROUP_ADMIN,
		"mvc.command.name=editCommerceAccountGroupCommerceAccountRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountGroupCommerceAccountRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceAccountGroupCommerceAccountRel(
			ActionRequest actionRequest)
		throws Exception {

		long[] addCommerceAccountIds;

		long commerceAccountGroupId = ParamUtil.getLong(
			actionRequest, "commerceAccountGroupId");

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		if (commerceAccountId > 0) {
			addCommerceAccountIds = new long[] {commerceAccountId};
		}
		else {
			addCommerceAccountIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commerceAccountIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountGroupCommerceAccountRel.class.getName(),
			actionRequest);

		for (long addCommerceAccountId : addCommerceAccountIds) {
			_commerceAccountGroupCommerceAccountRelService.
				addCommerceAccountGroupCommerceAccountRel(
					commerceAccountGroupId, addCommerceAccountId,
					serviceContext);
		}
	}

	protected void deleteCommerceAccountGroupCommerceAccountRels(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceAccountGroupCommerceAccountRelIds = null;

		long commerceAccountGroupCommerceAccountRelId = ParamUtil.getLong(
			actionRequest, "commerceAccountGroupCommerceAccountRelId");

		if (commerceAccountGroupCommerceAccountRelId > 0) {
			deleteCommerceAccountGroupCommerceAccountRelIds = new long[] {
				commerceAccountGroupCommerceAccountRelId
			};
		}
		else {
			deleteCommerceAccountGroupCommerceAccountRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest,
					"deleteCommerceAccountGroupCommerceAccountRelIds"),
				0L);
		}

		for (long deleteCommerceAccountGroupCommerceAccountRelId :
				deleteCommerceAccountGroupCommerceAccountRelIds) {

			_commerceAccountGroupCommerceAccountRelService.
				deleteCommerceAccountGroupCommerceAccountRel(
					deleteCommerceAccountGroupCommerceAccountRelId);
		}
	}

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceAccountGroupCommerceAccountRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAccountGroupCommerceAccountRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountGroupCommerceAccountRelException ||
				e instanceof NoSuchAccountGroupException ||
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
		EditCommerceAccountGroupCommerceAccountRelMVCActionCommand.class);

	@Reference
	private CommerceAccountGroupCommerceAccountRelService
		_commerceAccountGroupCommerceAccountRelService;

}