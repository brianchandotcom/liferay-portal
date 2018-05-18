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
import com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl;
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
				long commercePriceListId, long commerceUserSegmentEntryId,
				int order, ServiceContext serviceContext)
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
			long commercePriceListId, long commerceUserSegmentEntryId) {

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