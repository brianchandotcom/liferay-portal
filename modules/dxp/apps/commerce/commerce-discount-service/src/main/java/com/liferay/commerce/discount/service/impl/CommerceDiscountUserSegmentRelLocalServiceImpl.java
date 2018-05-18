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

import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountUserSegmentRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

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

		commerceDiscountUserSegmentRelPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public void
		deleteCommerceDiscountUserSegmentRelsByCommerceUserSegmentEntryId(
			long commerceUserSegmentEntryId) {

		commerceDiscountUserSegmentRelPersistence.
			removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	@Override
	public List<CommerceDiscountUserSegmentRel>
		getCommerceDiscountUserSegmentRels(
			long commerceDiscountId, int start, int end,
			OrderByComparator<CommerceDiscountUserSegmentRel>
				orderByComparator) {

		return
			commerceDiscountUserSegmentRelPersistence.findByCommerceDiscountId(
				commerceDiscountId, start, end, orderByComparator);
	}

}