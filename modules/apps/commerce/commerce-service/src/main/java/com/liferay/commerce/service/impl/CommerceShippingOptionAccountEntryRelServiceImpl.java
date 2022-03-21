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

package com.liferay.commerce.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.service.base.CommerceShippingOptionAccountEntryRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingOptionAccountEntryRelServiceImpl
	extends CommerceShippingOptionAccountEntryRelServiceBaseImpl {

	@Override
	public CommerceShippingOptionAccountEntryRel
			addCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException {

		_checkAccountEntry(accountEntryId, ActionKeys.UPDATE);

		return commerceShippingOptionAccountEntryRelLocalService.
			addCommerceShippingOptionAccountEntryRel(
				getUserId(), accountEntryId, commerceChannelId,
				commerceShippingMethodKey, commerceShippingOptionKey);
	}

	@Override
	public void deleteCommerceShippingOptionAccountEntryRel(
			long commerceShippingOptionAccountEntryRelId)
		throws PortalException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				commerceShippingOptionAccountEntryRelLocalService.
					getCommerceShippingOptionAccountEntryRel(
						commerceShippingOptionAccountEntryRelId);

		_checkAccountEntry(
			commerceShippingOptionAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		commerceShippingOptionAccountEntryRelLocalService.
			deleteCommerceShippingOptionAccountEntryRel(
				commerceShippingOptionAccountEntryRel);
	}

	@Override
	public CommerceShippingOptionAccountEntryRel
			fetchCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId)
		throws PortalException {

		_checkAccountEntry(accountEntryId, ActionKeys.VIEW);

		return commerceShippingOptionAccountEntryRelLocalService.
			fetchCommerceShippingOptionAccountEntryRel(
				accountEntryId, commerceChannelId);
	}

	@Override
	public CommerceShippingOptionAccountEntryRel
			updateCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				commerceShippingOptionAccountEntryRelLocalService.
					getCommerceShippingOptionAccountEntryRel(
						commerceShippingOptionAccountEntryRelId);

		_checkAccountEntry(
			commerceShippingOptionAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		return commerceShippingOptionAccountEntryRelLocalService.
			updateCommerceShippingOptionAccountEntryRel(
				commerceShippingOptionAccountEntryRelId,
				commerceShippingMethodKey, commerceShippingOptionKey);
	}

	private void _checkAccountEntry(long accountEntryId, String actionId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, actionId);
	}

	private static volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceShippingOptionAccountEntryRelServiceImpl.class,
				"_accountEntryModelResourcePermission", AccountEntry.class);

}