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

package com.liferay.commerce.account.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.web.internal.display.context.helper.CommerceAccountRelRequestHelper;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountDisplayContext {

	public CommerceAccountDisplayContext(
			ModelResourcePermission<AccountEntry>
				accountEntryModelResourcePermission,
			AccountEntryService accountEntryService,
			CommerceChannelAccountEntryRelService
				commerceChannelAccountEntryRelService,
			CommerceChannelService commerceChannelService,
			HttpServletRequest httpServletRequest, Language language,
			UserService userService)
		throws PortalException {

		_accountEntryModelResourcePermission =
			accountEntryModelResourcePermission;
		_accountEntryService = accountEntryService;
		_commerceChannelAccountEntryRelService =
			commerceChannelAccountEntryRelService;
		_commerceChannelService = commerceChannelService;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_userService = userService;

		long accountEntryId = ParamUtil.getLong(
			_httpServletRequest, "accountEntryId");

		_accountEntry = _accountEntryService.fetchAccountEntry(accountEntryId);

		_commerceAccountRelRequestHelper = new CommerceAccountRelRequestHelper(
			httpServletRequest);
	}

	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel()
		throws PortalException {

		long commerceChannelAccountEntryRelId = ParamUtil.getLong(
			_httpServletRequest, "commerceChannelAccountEntryRelId");

		return _commerceChannelAccountEntryRelService.
			fetchCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRelId);
	}

	public AccountEntry getAccountEntry() {
		return _accountEntry;
	}

	public long getAccountEntryId() {
		if (_accountEntry == null) {
			return 0;
		}

		return _accountEntry.getAccountEntryId();
	}

	public String getAddCommerceChannelAccountEntryRelRenderURL() {
		return PortletURLBuilder.createRenderURL(
			_commerceAccountRelRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/account_entries_admin/edit_account_entry_user"
		).setParameter(
			"accountEntryId", _accountEntry.getAccountEntryId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public List<User> getAllowedUsers() throws PortalException {
		try {
			List<User> companyUsers = _userService.getCompanyUsers(
				_commerceAccountRelRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<User> filteredUsers = new ArrayList<>();

			for (User user : companyUsers) {
				if (_accountEntryModelResourcePermission.contains(
						PermissionCheckerFactoryUtil.create(user), 0,
						CommerceAccountActionKeys.
							MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL)) {

					filteredUsers.add(user);
				}
			}

			return filteredUsers;
		}
		catch (PrincipalException principalException) {
			_log.error(principalException);
		}

		return Collections.emptyList();
	}

	public List<CommerceChannel> getCommerceChannels() throws PortalException {
		return _commerceChannelService.findCommerceChannels(
			_commerceAccountRelRequestHelper.getCompanyId());
	}

	public String getCommerceChannelsEmptyOptionKey() throws PortalException {
		int commerceChannelAccountEntryRelsCount =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRelsCount(
					_accountEntry.getAccountEntryId(),
					CommerceChannelAccountEntryRelConstants.TYPE_USER);

		if (commerceChannelAccountEntryRelsCount > 0) {
			return "all-other-channels";
		}

		return "all-channels";
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasPermission(ActionKeys.UPDATE) &&
			ListUtil.isNotEmpty(getAllowedUsers())) {

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						getAddCommerceChannelAccountEntryRelRenderURL());
					dropdownItem.setLabel(
						_language.get(_httpServletRequest, "add-user"));
					dropdownItem.setTarget("modal");
				});
		}

		return creationMenu;
	}

	public String getModalTitle() {
		return _language.get(
			_commerceAccountRelRequestHelper.getRequest(), "set-user");
	}

	public String getName() {
		return _accountEntry.getName();
	}

	public boolean hasPermission(String actionId) throws PortalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (_accountEntryModelResourcePermission.contains(
				permissionChecker, _accountEntry.getAccountEntryId(),
				actionId) &&
			permissionChecker.hasPermission(
				null, CommerceChannel.class.getName(),
				CompanyThreadLocal.getCompanyId(), ActionKeys.VIEW) &&
			permissionChecker.hasPermission(
				null, CommerceChannel.class.getName(),
				CompanyThreadLocal.getCompanyId(),
				CommerceAccountActionKeys.MANAGE_CHANNEL_ACCOUNT_MANAGERS) &&
			permissionChecker.hasPermission(
				null, User.class.getName(), CompanyThreadLocal.getCompanyId(),
				ActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	public boolean isCommerceChannelSelected(long commerceChannelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchCommerceChannelAccountEntryRel();

		if (commerceChannelAccountEntryRel == null) {
			if (commerceChannelId == 0) {
				return true;
			}

			return false;
		}

		if (commerceChannelAccountEntryRel.getCommerceChannelId() ==
				commerceChannelId) {

			return true;
		}

		return false;
	}

	public boolean isUserSelected(long userId) throws PortalException {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			fetchCommerceChannelAccountEntryRel();

		if (commerceChannelAccountEntryRel == null) {
			return false;
		}

		if (commerceChannelAccountEntryRel.getClassPK() == userId) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountDisplayContext.class);

	private final AccountEntry _accountEntry;
	private final ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;
	private final AccountEntryService _accountEntryService;
	private final CommerceAccountRelRequestHelper
		_commerceAccountRelRequestHelper;
	private final CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;
	private final CommerceChannelService _commerceChannelService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final UserService _userService;

}