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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

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
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/edit_account_entry_address"
	},
	service = MVCActionCommand.class
)
public class EditAccountEntryAddressMVCActionCommand
	extends BaseMVCActionCommand {

	protected Address addAccountEntryAddress(ActionRequest actionRequest)
		throws Exception {

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long addressRegionId = ParamUtil.getLong(
			actionRequest, "addressRegionId");
		long addressCountryId = ParamUtil.getLong(
			actionRequest, "addressCountryId");
		long addressTypeId = ParamUtil.getLong(actionRequest, "addressTypeId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		return _addressLocalService.addAddress(
			null, themeDisplay.getUserId(), AccountEntry.class.getName(),
			accountEntryId, name, description, street1, street2, street3, city,
			zip, addressRegionId, addressCountryId, addressTypeId, false, false,
			phoneNumber, serviceContext);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		Address accountEntryAddress = null;

		if (cmd.equals(Constants.ADD)) {
			accountEntryAddress = addAccountEntryAddress(actionRequest);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateAccountEntryAddress(actionRequest);
		}

		String defaultType = ParamUtil.getString(actionRequest, "defaultType");

		if (Objects.equals("billing", defaultType) ||
			Objects.equals("shipping", defaultType)) {

			long accountEntryId = ParamUtil.getLong(
				actionRequest, "accountEntryId");

			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(accountEntryId);

			long addressId = 0;

			if (accountEntryAddress != null) {
				addressId = accountEntryAddress.getAddressId();
			}

			if (Objects.equals("billing", defaultType)) {
				accountEntry.setDefaultBillingAddressId(addressId);
			}
			else if (Objects.equals("shipping", defaultType)) {
				accountEntry.setDefaultShippingAddressId(addressId);
			}

			_accountEntryLocalService.updateAccountEntry(accountEntry);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	protected void updateAccountEntryAddress(ActionRequest actionRequest)
		throws Exception {

		long accountEntryAddressId = ParamUtil.getLong(
			actionRequest, "accountEntryAddressId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long addressRegionId = ParamUtil.getLong(
			actionRequest, "addressRegionId");
		long addressCountryId = ParamUtil.getLong(
			actionRequest, "addressCountryId");
		long addressTypeId = ParamUtil.getLong(actionRequest, "addressTypeId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		_addressLocalService.updateAddress(
			accountEntryAddressId, name, description, street1, street2, street3,
			city, zip, addressRegionId, addressCountryId, addressTypeId, false,
			false, phoneNumber);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

}