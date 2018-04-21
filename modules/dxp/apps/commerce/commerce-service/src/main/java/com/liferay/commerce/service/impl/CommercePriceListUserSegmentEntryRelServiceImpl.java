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
import com.liferay.commerce.service.base.CommercePriceListUserSegmentEntryRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommercePriceListUserSegmentEntryRelServiceImpl
	extends CommercePriceListUserSegmentEntryRelServiceBaseImpl {

	@Override
	public CommercePriceListUserSegmentEntryRel
		addCommercePriceListUserSegmentEntryRel(
			long commercePriceListId,
			long commerceUserSegmentEntryId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListUserSegmentEntryRelLocalService.
			addCommercePriceListUserSegmentEntryRel(
				commercePriceListId, commerceUserSegmentEntryId, order,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId)
		throws PortalException {

		commercePriceListUserSegmentEntryRelLocalService.
			deleteCommercePriceListUserSegmentEntryRel(
				commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
		fetchCommercePriceListUserSegmentEntryRel(
			long commercePriceListId,
			long commerceUserSegmentEntryId) {

		return commercePriceListUserSegmentEntryRelLocalService.
			fetchCommercePriceListUserSegmentEntryRel(
				commercePriceListId, commerceUserSegmentEntryId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
	getCommercePriceListUserSegmentEntryRels(long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelLocalService.
			getCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
		getCommercePriceListUserSegmentEntryRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListUserSegmentEntryRel>
				orderByComparator) {

		return commercePriceListUserSegmentEntryRelLocalService.
			getCommercePriceListUserSegmentEntryRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelLocalService.
			getCommercePriceListUserSegmentEntryRelsCount(commercePriceListId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
		updateCommercePriceListUserSegmentEntryRel(
			long commercePriceListUserSegmentEntryRelId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListUserSegmentEntryRelLocalService.
			updateCommercePriceListUserSegmentEntryRel(
				commercePriceListUserSegmentEntryRelId, order, serviceContext);
	}

}