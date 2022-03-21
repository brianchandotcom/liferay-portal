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

import com.liferay.commerce.exception.DuplicateCommerceShippingOptionAccountEntryRelException;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.service.base.CommerceShippingOptionAccountEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingOptionAccountEntryRelLocalServiceImpl
	extends CommerceShippingOptionAccountEntryRelLocalServiceBaseImpl {

	@Override
	public CommerceShippingOptionAccountEntryRel
			addCommerceShippingOptionAccountEntryRel(
				long userId, long accountEntryId, long commerceChannelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException {

		_validate(accountEntryId, commerceChannelId);

		long commerceShippingOptionAccountEntryRelId =
			counterLocalService.increment();

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				commerceShippingOptionAccountEntryRelPersistence.create(
					commerceShippingOptionAccountEntryRelId);

		User user = userLocalService.getUser(userId);

		commerceShippingOptionAccountEntryRel.setCompanyId(user.getCompanyId());
		commerceShippingOptionAccountEntryRel.setUserId(user.getUserId());
		commerceShippingOptionAccountEntryRel.setUserName(user.getFullName());

		commerceShippingOptionAccountEntryRel.setAccountEntryId(accountEntryId);
		commerceShippingOptionAccountEntryRel.setCommerceChannelId(
			commerceChannelId);
		commerceShippingOptionAccountEntryRel.setCommerceShippingMethodKey(
			commerceShippingMethodKey);
		commerceShippingOptionAccountEntryRel.setCommerceShippingOptionKey(
			commerceShippingOptionKey);

		return commerceShippingOptionAccountEntryRelPersistence.update(
			commerceShippingOptionAccountEntryRel);
	}

	@Override
	public void deleteCommerceShippingOptionAccountEntryRelsByAccountEntryId(
		long accountEntryId) {

		commerceShippingOptionAccountEntryRelPersistence.removeByAccountEntryId(
			accountEntryId);
	}

	@Override
	public void deleteCommerceShippingOptionAccountEntryRelsByCommerceChannelId(
		long commerceChannelId) {

		commerceShippingOptionAccountEntryRelPersistence.
			removeByCommerceChannelId(commerceChannelId);
	}

	@Override
	public void deleteCommerceShippingOptionAccountEntryRelsByCSFixedOptionKey(
		String commerceShippingFixedOptionKey) {

		commerceShippingOptionAccountEntryRelPersistence.
			removeByCommerceShippingOptionKey(commerceShippingFixedOptionKey);
	}

	@Override
	public CommerceShippingOptionAccountEntryRel
		fetchCommerceShippingOptionAccountEntryRel(
			long accountEntryId, long commerceChannelId) {

		return commerceShippingOptionAccountEntryRelPersistence.fetchByA_C(
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
				commerceShippingOptionAccountEntryRelPersistence.
					findByPrimaryKey(commerceShippingOptionAccountEntryRelId);

		commerceShippingOptionAccountEntryRel.setCommerceShippingMethodKey(
			commerceShippingMethodKey);
		commerceShippingOptionAccountEntryRel.setCommerceShippingOptionKey(
			commerceShippingOptionKey);

		return commerceShippingOptionAccountEntryRelPersistence.update(
			commerceShippingOptionAccountEntryRel);
	}

	private void _validate(long accountEntryId, long commerceChannelId)
		throws PortalException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				commerceShippingOptionAccountEntryRelPersistence.fetchByA_C(
					accountEntryId, commerceChannelId);

		if (commerceShippingOptionAccountEntryRel != null) {
			throw new DuplicateCommerceShippingOptionAccountEntryRelException();
		}
	}

}