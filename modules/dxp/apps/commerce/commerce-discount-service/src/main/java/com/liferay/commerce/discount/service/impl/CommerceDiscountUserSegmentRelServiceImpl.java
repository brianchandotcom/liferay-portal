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

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountUserSegmentRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountUserSegmentRelServiceImpl
	extends CommerceDiscountUserSegmentRelServiceBaseImpl {

	@Override
	public CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
			long commerceDiscountId, long commerceUserSegmentEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountUserSegmentRelLocalService.
			addCommerceDiscountUserSegmentRel(
				commerceDiscountId, commerceUserSegmentEntryId, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountUserSegmentRel(
			long commerceDiscountUserSegmentRelId)
		throws PortalException {

		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel =
			commerceDiscountUserSegmentRelLocalService.
				getCommerceDiscountUserSegmentRel(
					commerceDiscountUserSegmentRelId);

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountUserSegmentRel.getCommerceDiscountId(),
			ActionKeys.UPDATE);

		commerceDiscountUserSegmentRelLocalService.
			deleteCommerceDiscountUserSegmentRel(
				commerceDiscountUserSegmentRel);
	}

	@Override
	public List<CommerceDiscountUserSegmentRel>
			getCommerceDiscountUserSegmentRels(
				long commerceDiscountId, int start, int end,
				OrderByComparator<CommerceDiscountUserSegmentRel>
					orderByComparator)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountUserSegmentRelLocalService.
			getCommerceDiscountUserSegmentRels(
				commerceDiscountId, start, end, orderByComparator);
	}

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountUserSegmentRelServiceImpl.class,
				"_commerceDiscountResourcePermission", CommerceDiscount.class);

}