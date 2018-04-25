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

import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountUserSegmentRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountUserSegmentRelLocalServiceImpl
	extends CommerceDiscountUserSegmentRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
			long commerceDiscountId, long commerceUserSegmentEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceDiscountUserSegmentRelId = counterLocalService.increment();

		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel =
			commerceDiscountUserSegmentRelPersistence.create(
				commerceDiscountUserSegmentRelId);

		commerceDiscountUserSegmentRel.setGroupId(groupId);
		commerceDiscountUserSegmentRel.setCompanyId(user.getCompanyId());
		commerceDiscountUserSegmentRel.setUserId(user.getUserId());
		commerceDiscountUserSegmentRel.setUserName(user.getFullName());
		commerceDiscountUserSegmentRel.setCommerceDiscountId(
			commerceDiscountId);
		commerceDiscountUserSegmentRel.setCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);

		commerceDiscountUserSegmentRelPersistence.update(
			commerceDiscountUserSegmentRel);

		return commerceDiscountUserSegmentRel;
	}

	@Override
	public void deleteCommerceDiscountUserSegmentRelsByCommerceDiscountId(
		long commerceDiscountId) {

		commerceDiscountUserSegmentRelPersistence.removeBycommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public void
		deleteCommerceDiscountUserSegmentRelsByCommerceUserSegmentEntryId(
			long commerceUserSegmentEntryId) {

		commerceDiscountUserSegmentRelPersistence.
			removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

}