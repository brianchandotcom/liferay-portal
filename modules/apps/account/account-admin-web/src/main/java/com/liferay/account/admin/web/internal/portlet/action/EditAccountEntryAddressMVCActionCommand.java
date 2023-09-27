/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/edit_account_entry_address"
	},
	service = MVCActionCommand.class
)
public class EditAccountEntryAddressMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_checkPermission(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		Address accountEntryAddress = null;

		if (cmd.equals(Constants.ADD)) {
			accountEntryAddress = _addAccountEntryAddress(actionRequest);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			_updateAccountEntryAddress(actionRequest);
		}

		String defaultType = ParamUtil.getString(actionRequest, "defaultType");

		if (Objects.equals(defaultType, "billing") ||
			Objects.equals(defaultType, "shipping")) {

			long accountEntryId = ParamUtil.getLong(
				actionRequest, "accountEntryId");

			AccountEntry accountEntry = _accountEntryService.getAccountEntry(
				accountEntryId);

			long addressId = 0;

			if (accountEntryAddress != null) {
				addressId = accountEntryAddress.getAddressId();
			}

			if (Objects.equals(defaultType, "billing")) {
				accountEntry.setDefaultBillingAddressId(addressId);
			}
			else if (Objects.equals(defaultType, "shipping")) {
				accountEntry.setDefaultShippingAddressId(addressId);
			}

			_accountEntryService.updateAccountEntry(accountEntry);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	private Address _addAccountEntryAddress(ActionRequest actionRequest)
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
		long addressListTypeId = ParamUtil.getLong(
			actionRequest, "addressListTypeId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		return _addressLocalService.addAddress(
			null, themeDisplay.getUserId(), AccountEntry.class.getName(),
			accountEntryId, name, description, street1, street2, street3, city,
			zip, addressRegionId, addressCountryId, addressListTypeId, false,
			false, phoneNumber, serviceContext);
	}

	private void _checkPermission(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_accountEntryModelResourcePermission.check(
			themeDisplay.getPermissionChecker(),
			ParamUtil.getLong(actionRequest, "accountEntryId"),
			AccountActionKeys.MANAGE_ADDRESSES);
	}

	private void _updateAccountEntryAddress(ActionRequest actionRequest)
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
		long addressListTypeId = ParamUtil.getLong(
			actionRequest, "addressListTypeId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		_addressLocalService.updateAddress(
			accountEntryAddressId, name, description, street1, street2, street3,
			city, zip, addressRegionId, addressCountryId, addressListTypeId,
			false, false, phoneNumber);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AddressLocalService _addressLocalService;

}