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
import com.liferay.commerce.account.exception.CommerceAccountNameException;
import com.liferay.commerce.account.exception.CommerceAccountOrdersException;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT_ADMIN,
		"mvc.command.name=editCommerceAccount"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceAccounts(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceAccountIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		if (commerceAccountId > 0) {
			deleteCommerceAccountIds = new long[] {commerceAccountId};
		}
		else {
			deleteCommerceAccountIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceAccountIds"),
				0L);
		}

		for (long deleteCommerceAccountId : deleteCommerceAccountIds) {
			_commerceAccountService.deleteCommerceAccount(
				deleteCommerceAccountId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceAccount commerceAccount = updateCommerceAccount(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceAccount);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAccounts(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof CommerceAccountNameException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceAccount");
			}
			else if (e instanceof NoSuchAccountException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceAccountOrdersException) {
				SessionErrors.add(actionRequest, e.getClass());

				hideDefaultErrorMessage(actionRequest);
			}
			else {
				_log.error(e, e);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, CommerceAccount commerceAccount) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceAccountPortletKeys.COMMERCE_ACCOUNT_ADMIN,
			PortletRequest.RENDER_PHASE);

		if (commerceAccount != null) {
			portletURL.setParameter("backURL", portletURL.toString());

			portletURL.setParameter(
				"mvcRenderCommandName", "editCommerceAccount");
			portletURL.setParameter(
				"commerceAccountId",
				String.valueOf(commerceAccount.getCommerceAccountId()));

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			portletURL.setParameter("redirect", redirect);
		}

		return portletURL.toString();
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long[] commerceAccountIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		if (commerceAccountId > 0) {
			commerceAccountIds = new long[] {commerceAccountId};
		}
		else {
			commerceAccountIds = ParamUtil.getLongValues(
				actionRequest, "commerceAccountIds");
		}

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		for (long curCommerceAccountId : commerceAccountIds) {
			_commerceAccountService.setActive(curCommerceAccountId, active);
		}
	}

	protected CommerceAccount updateCommerceAccount(ActionRequest actionRequest)
		throws Exception {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");
		long parentCommerceAccountId = ParamUtil.getLong(
			actionRequest, "parentCommerceAccountId");
		String email = ParamUtil.getString(actionRequest, "email");
		String taxId = ParamUtil.getString(actionRequest, "taxId");
		int type = ParamUtil.getInteger(actionRequest, "type");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccount.class.getName(), actionRequest);

		CommerceAccount commerceAccount = null;

		if (commerceAccountId <= 0) {
			commerceAccount = _commerceAccountService.addCommerceAccount(
				name, parentCommerceAccountId, email, taxId, type, active,
				StringPool.BLANK, serviceContext);
		}
		else {
			long defaultBillingAddressId = ParamUtil.getLong(
				actionRequest, "defaultBillingAddressId");
			long defaultShippingAddressId = ParamUtil.getLong(
				actionRequest, "defaultShippingAddressId");

			commerceAccount = _commerceAccountService.updateCommerceAccount(
				commerceAccountId, name, !deleteLogo, logoBytes, email, taxId,
				active, defaultBillingAddressId, defaultShippingAddressId,
				serviceContext);
		}

		return commerceAccount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceAccountMVCActionCommand.class);

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}