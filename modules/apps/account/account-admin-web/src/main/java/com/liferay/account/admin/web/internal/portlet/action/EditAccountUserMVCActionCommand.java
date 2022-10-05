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

import com.liferay.account.admin.web.internal.security.permission.resource.AccountUserPermission;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/edit_account_user"
	},
	service = AopService.class
)
public class EditAccountUserMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User accountUser = _userLocalService.fetchUser(
			ParamUtil.getLong(actionRequest, "accountUserId"));
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			ParamUtil.getLong(actionRequest, "accountEntryId"));

		AccountUserPermission.checkEditUserPermission(
			_permissionCheckerFactory.create(_portal.getUser(actionRequest)),
			AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT, accountEntry,
			accountUser);

		_editAccountUser(accountUser, actionRequest);
	}

	private void _editAccountUser(User accountUser, ActionRequest actionRequest)
		throws Exception {

		String firstName = ParamUtil.getString(
			actionRequest, "firstName", accountUser.getFirstName());
		String languageId = ParamUtil.getString(
			actionRequest, "languageId", accountUser.getLanguageId());
		String lastName = ParamUtil.getString(
			actionRequest, "lastName", accountUser.getLastName());
		String middleName = ParamUtil.getString(
			actionRequest, "middleName", accountUser.getMiddleName());

		accountUser.setLanguageId(languageId);
		accountUser.setFirstName(firstName);
		accountUser.setMiddleName(middleName);
		accountUser.setLastName(lastName);

		accountUser = _userLocalService.updateUser(accountUser);

		Contact accountUserContact = accountUser.getContact();

		accountUserContact.setPrefixListTypeId(
			_getListTypeId(
				actionRequest, "prefixValue",
				ListTypeConstants.CONTACT_PREFIX));
		accountUserContact.setSuffixListTypeId(
			_getListTypeId(
				actionRequest, "suffixValue",
				ListTypeConstants.CONTACT_SUFFIX));

		_contactLocalService.updateContact(accountUserContact);
	}

	private long _getListTypeId(
		PortletRequest portletRequest, String parameterName, String type) {

		String parameterValue = ParamUtil.getString(
			portletRequest, parameterName);

		ListType listType = _listTypeLocalService.addListType(
			parameterValue, type);

		return listType.getListTypeId();
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}