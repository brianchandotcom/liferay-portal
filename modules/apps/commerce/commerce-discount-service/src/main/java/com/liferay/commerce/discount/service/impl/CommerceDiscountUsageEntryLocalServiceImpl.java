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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.service.base.CommerceDiscountUsageEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountUsageEntryLocalServiceImpl
	extends CommerceDiscountUsageEntryLocalServiceBaseImpl {

	@Override
	public CommerceDiscountUsageEntry addCommerceDiscountUsageEntry(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId, ServiceContext serviceContext)
		throws PortalException {

		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		if (user.isDefaultUser()) {
			userId = 0;
		}

		long commerceDiscountUsageEntryId = counterLocalService.increment();

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			commerceDiscountUsageEntryPersistence.create(
				commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setCompanyId(user.getCompanyId());
		commerceDiscountUsageEntry.setUserId(userId);
		commerceDiscountUsageEntry.setUserName(user.getFullName());
		commerceDiscountUsageEntry.setCommerceAccountId(commerceAccountId);
		commerceDiscountUsageEntry.setCommerceOrderId(commerceOrderId);
		commerceDiscountUsageEntry.setCommerceDiscountId(commerceDiscountId);

		return commerceDiscountUsageEntryPersistence.update(
			commerceDiscountUsageEntry);
	}

}