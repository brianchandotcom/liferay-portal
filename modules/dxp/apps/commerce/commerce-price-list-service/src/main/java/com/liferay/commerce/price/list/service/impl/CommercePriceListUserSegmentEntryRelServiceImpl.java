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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelServiceBaseImpl;
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
				long commercePriceListId, long commerceUserSegmentEntryId,
				int order, ServiceContext serviceContext)
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
			long commercePriceListId, long commerceUserSegmentEntryId) {

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