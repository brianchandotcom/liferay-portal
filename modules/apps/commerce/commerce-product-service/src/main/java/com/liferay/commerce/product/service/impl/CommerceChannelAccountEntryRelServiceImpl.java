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

package com.liferay.commerce.product.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.base.CommerceChannelAccountEntryRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelAccountEntryRelServiceImpl
	extends CommerceChannelAccountEntryRelServiceBaseImpl {

	@Override
	public CommerceChannelAccountEntryRel addCommerceChannelAccountEntryRel(
			long accountEntryId, String className, long classPK,
			long commerceChannelId, boolean overrideEligibility,
			double priority, int type)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.UPDATE);

		return commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				getUserId(), accountEntryId, className, classPK,
				commerceChannelId, overrideEligibility, priority, type);
	}

	@Override
	public void deleteCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceChannelAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		commerceChannelAccountEntryRelLocalService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel);
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		if (commerceChannelAccountEntryRel != null) {
			_accountEntryModelResourcePermission.check(
				getPermissionChecker(),
				commerceChannelAccountEntryRel.getAccountEntryId(),
				ActionKeys.VIEW);
		}

		return commerceChannelAccountEntryRel;
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long accountEntryId, long commerceChannelId, int type)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					accountEntryId, commerceChannelId, type);

		if (commerceChannelAccountEntryRel != null) {
			_accountEntryModelResourcePermission.check(
				getPermissionChecker(),
				commerceChannelAccountEntryRel.getAccountEntryId(),
				ActionKeys.VIEW);
		}

		return commerceChannelAccountEntryRel;
	}

	@Override
	public List<CommerceChannelAccountEntryRel>
			getCommerceChannelAccountEntryRels(
				long accountEntryId, int type, int start, int end,
				OrderByComparator<CommerceChannelAccountEntryRel>
					orderByComparator)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return commerceChannelAccountEntryRelLocalService.
			getCommerceChannelAccountEntryRels(
				accountEntryId, type, start, end, orderByComparator);
	}

	@Override
	public int getCommerceChannelAccountEntryRelsCount(
			long accountEntryId, int type)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return commerceChannelAccountEntryRelLocalService.
			getCommerceChannelAccountEntryRelsCount(accountEntryId, type);
	}

	@Override
	public CommerceChannelAccountEntryRel updateCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId, long commerceChannelId,
			long classPK, boolean overrideEligibility, double priority)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(),
			commerceChannelAccountEntryRel.getAccountEntryId(),
			ActionKeys.UPDATE);

		return commerceChannelAccountEntryRelLocalService.
			updateCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId(),
				commerceChannelId, classPK, overrideEligibility, priority);
	}

	private static volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceChannelAccountEntryRelServiceImpl.class,
				"_accountEntryModelResourcePermission", AccountEntry.class);

}