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

package com.liferay.commerce.account.service.impl;

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.base.CommerceAccountServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountServiceImpl extends CommerceAccountServiceBaseImpl {

	@Override
	public CommerceAccount addBusinessCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, boolean active, String externalReferenceCode,
			long[] userIds, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.ADD_ACCOUNT_GROUP);

		return commerceAccountLocalService.addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	@Override
	public CommerceAccount addCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, int type, boolean active,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.ADD_ACCOUNT_GROUP);

		return commerceAccountLocalService.addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	@Override
	public CommerceAccount addOrUpdateCommerceAccount(
			String name, long parentCommerceAccountId, boolean logo,
			byte[] logoBytes, String email, String taxId, int type,
			boolean active, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		CommerceAccount commerceAccount =
			commerceAccountLocalService.fetchCommerceAccountByReferenceCode(
				permissionChecker.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			PortletResourcePermission portletResourcePermission =
				_commerceAccountModelResourcePermission.
					getPortletResourcePermission();

			portletResourcePermission.check(
				getPermissionChecker(), null,
				CommerceAccountActionKeys.ADD_ACCOUNT);
		}
		else {
			_commerceAccountModelResourcePermission.check(
				permissionChecker, commerceAccount, ActionKeys.UPDATE);
		}

		return commerceAccountLocalService.addOrUpdateCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	@Override
	public void deleteCommerceAccount(long commerceAccountId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.DELETE);

		commerceAccountLocalService.deleteCommerceAccount(commerceAccountId);
	}

	@Override
	public CommerceAccount fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommerceAccount commerceAccount =
			commerceAccountLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (commerceAccount != null) {
			_commerceAccountModelResourcePermission.check(
				getPermissionChecker(), commerceAccount.getCommerceAccountId(),
				ActionKeys.VIEW);
		}

		return commerceAccount;
	}

	@Override
	public CommerceAccount fetchCommerceAccount(long commerceAccountId)
		throws PortalException {

		User user = getUser();

		if ((user == null) || user.isDefaultUser()) {
			return commerceAccountLocalService.getGuestCommerceAccount(
				user.getCompanyId());
		}

		if (_isAccountCompanyAdministrator()) {
			return commerceAccountLocalService.fetchCommerceAccount(
				commerceAccountId);
		}

		return commerceAccountLocalService.getCommerceAccount(
			getUserId(), commerceAccountId);
	}

	@Override
	public CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException {

		User user = getUser();

		if ((user == null) || user.isDefaultUser()) {
			return commerceAccountLocalService.getGuestCommerceAccount(
				user.getCompanyId());
		}

		if (_isAccountCompanyAdministrator()) {
			return commerceAccountLocalService.fetchCommerceAccount(
				commerceAccountId);
		}

		CommerceAccount commerceAccount =
			commerceAccountLocalService.getCommerceAccount(
				getUserId(), commerceAccountId);

		if (commerceAccount == null) {
			throw new NoSuchAccountException();
		}

		return commerceAccount;
	}

	@Override
	public CommerceAccount getPersonalCommerceAccount(long userId)
		throws PortalException {

		CommerceAccount commerceAccount =
			commerceAccountLocalService.getPersonalCommerceAccount(userId);

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccount.getCommerceAccountId(),
			ActionKeys.VIEW);

		return commerceAccount;
	}

	@Override
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active, int start, int end)
		throws PortalException {

		User user = userLocalService.fetchUser(userId);

		if (user == null) {
			return Collections.emptyList();
		}

		if ((userId == getUserId()) && !_isAccountCompanyAdministrator()) {
			return commerceAccountLocalService.getUserCommerceAccounts(
				userId, parentCommerceAccountId, commerceSiteType, keywords,
				active, start, end);
		}
		else if (_isAccountCompanyAdministrator()) {
			int accountType = CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS;

			if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) {
				accountType = CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL;
			}
			else if (commerceSiteType ==
						CommerceAccountConstants.SITE_TYPE_B2X) {

				accountType = -1;
			}

			return commerceAccountLocalService.search(
				user.getCompanyId(), parentCommerceAccountId, keywords,
				accountType, active, start, end,
				SortFactoryUtil.create("name", false));
		}

		return Collections.emptyList();
	}

	@Override
	public List<CommerceAccount> getUserCommerceAccounts(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, int start, int end)
		throws PortalException {

		Boolean active = true;

		if (hasManageCommerceAccountPermissions()) {
			active = null;
		}

		return commerceAccountService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws PortalException {

		Boolean active = true;

		if (hasManageCommerceAccountPermissions()) {
			active = null;
		}

		return commerceAccountService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws PortalException {

		User user = userLocalService.fetchUser(userId);

		if (user == null) {
			return 0;
		}

		if ((userId == getUserId()) && !_isAccountCompanyAdministrator()) {
			return commerceAccountLocalService.getUserCommerceAccountsCount(
				userId, parentCommerceAccountId, commerceSiteType, keywords,
				active);
		}
		else if (_isAccountCompanyAdministrator()) {
			int accountType = CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS;

			if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) {
				accountType = CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL;
			}
			else if (commerceSiteType ==
						CommerceAccountConstants.SITE_TYPE_B2X) {

				accountType = -1;
			}

			return commerceAccountLocalService.searchCommerceAccountsCount(
				user.getCompanyId(), parentCommerceAccountId, keywords,
				accountType, active);
		}

		return 0;
	}

	@Override
	public CommerceAccount setActive(long commerceAccountId, boolean active)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.UPDATE);

		return commerceAccountLocalService.setActive(commerceAccountId, active);
	}

	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.UPDATE);

		return commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.UPDATE);

		return commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	@Override
	public CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active, -1,
			-1, serviceContext);
	}

	@Override
	public CommerceAccount updateDefaultBillingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.UPDATE);

		return commerceAccountLocalService.updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	@Override
	public CommerceAccount updateDefaultShippingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		_commerceAccountModelResourcePermission.check(
			getPermissionChecker(), commerceAccountId, ActionKeys.UPDATE);

		return commerceAccountLocalService.updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	protected boolean hasManageCommerceAccountPermissions()
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS);
	}

	private boolean _isAccountCompanyAdministrator() throws PortalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin(
				permissionChecker.getCompanyId())) {

			return true;
		}

		PortletResourcePermission portletResourcePermission =
			_commerceAccountModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.MANAGE_ALL_ACCOUNTS);
	}

	private static volatile ModelResourcePermission<CommerceAccount>
		_commerceAccountModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceAccountServiceImpl.class,
				"_commerceAccountModelResourcePermission",
				CommerceAccount.class);

}