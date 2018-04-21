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

import com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.service.base.CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommercePriceListUserSegmentEntryRelLocalServiceImpl
	extends CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListUserSegmentEntryRel
		addCommercePriceListUserSegmentEntryRel(
			long commercePriceListId,
			long commerceUserSegmentEntryId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceListUserSegmentEntryRelId =
			counterLocalService.increment();

		CommercePriceListUserSegmentEntryRel
			commercePriceListUserSegmentEntryRel =
			commercePriceListUserSegmentEntryRelPersistence.create(
				commercePriceListUserSegmentEntryRelId);

		commercePriceListUserSegmentEntryRel.setUuid(serviceContext.getUuid());
		commercePriceListUserSegmentEntryRel.setGroupId(groupId);
		commercePriceListUserSegmentEntryRel.setCompanyId(user.getCompanyId());
		commercePriceListUserSegmentEntryRel.setUserId(user.getUserId());
		commercePriceListUserSegmentEntryRel.setUserName(user.getFullName());
		commercePriceListUserSegmentEntryRel.setCommercePriceListId(
			commercePriceListId);
		commercePriceListUserSegmentEntryRel.setCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);
		commercePriceListUserSegmentEntryRel.setOrder(order);
		commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(
			serviceContext);

		return commercePriceListUserSegmentEntryRelPersistence.update(
			commercePriceListUserSegmentEntryRel);
	}

	@Override
	public void deleteCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {

		commercePriceListUserSegmentEntryRelPersistence.
			removeByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
		fetchCommercePriceListUserSegmentEntryRel(
			long commercePriceListId,
			long commerceUserSegmentEntryId) {

		return commercePriceListUserSegmentEntryRelPersistence.fetchByC_C(
			commercePriceListId, commerceUserSegmentEntryId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
	getCommercePriceListUserSegmentEntryRels(long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelPersistence.
			findByCommercePriceListId(commercePriceListId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
	getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel>
			orderByComparator) {

		return commercePriceListUserSegmentEntryRelPersistence.
			findByCommercePriceListId(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
	updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListUserSegmentEntryRel
			commercePriceListUserSegmentEntryRel =
				commercePriceListUserSegmentEntryRelPersistence.
					findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		commercePriceListUserSegmentEntryRel.setOrder(order);
		commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(
			serviceContext);

		return commercePriceListUserSegmentEntryRelPersistence.update(
			commercePriceListUserSegmentEntryRel);
	}
}