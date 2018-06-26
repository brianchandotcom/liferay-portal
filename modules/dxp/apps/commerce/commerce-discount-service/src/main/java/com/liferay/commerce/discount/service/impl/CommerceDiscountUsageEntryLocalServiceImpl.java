/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			long discountUserId, long discountOrganizationId,
			long commerceOrderId, long commerceDiscountId,
			ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		if (user.isDefaultUser()) {
			userId = 0;
		}

		long commerceDiscountUsageEntryId = counterLocalService.increment();

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			commerceDiscountUsageEntryPersistence.create(
				commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setGroupId(groupId);
		commerceDiscountUsageEntry.setCompanyId(user.getCompanyId());
		commerceDiscountUsageEntry.setUserId(userId);
		commerceDiscountUsageEntry.setUserName(user.getFullName());
		commerceDiscountUsageEntry.setDiscountUserId(discountUserId);
		commerceDiscountUsageEntry.setDiscountOrganizationId(
			discountOrganizationId);
		commerceDiscountUsageEntry.setCommerceOrderId(commerceOrderId);
		commerceDiscountUsageEntry.setCommerceDiscountId(commerceDiscountId);

		commerceDiscountUsageEntryPersistence.update(
			commerceDiscountUsageEntry);

		return commerceDiscountUsageEntry;
	}

}