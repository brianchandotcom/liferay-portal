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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShippingOptionAccountEntryRel"
	},
	service = AopService.class
)
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
			getCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId)
		throws PortalException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				commerceShippingOptionAccountEntryRelLocalService.
					getCommerceShippingOptionAccountEntryRel(
						commerceShippingOptionAccountEntryRelId);

		_checkAccountEntry(
			commerceShippingOptionAccountEntryRel.getAccountEntryId(),
			ActionKeys.VIEW);

		return commerceShippingOptionAccountEntryRel;
	}

	@Override
	public List<CommerceShippingOptionAccountEntryRel>
			getCommerceShippingOptionAccountEntryRels(long accountEntryId)
		throws Exception {

		_checkAccountEntry(accountEntryId, ActionKeys.VIEW);

		return commerceShippingOptionAccountEntryRelLocalService.
			getCommerceShippingOptionAccountEntryRels(accountEntryId);
	}

	@Override
	public int getCommerceShippingOptionAccountEntryRelsCount(
			long accountEntryId)
		throws Exception {

		_checkAccountEntry(accountEntryId, ActionKeys.VIEW);

		return commerceShippingOptionAccountEntryRelLocalService.
			getCommerceShippingOptionAccountEntryRelsCount(accountEntryId);
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

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

}