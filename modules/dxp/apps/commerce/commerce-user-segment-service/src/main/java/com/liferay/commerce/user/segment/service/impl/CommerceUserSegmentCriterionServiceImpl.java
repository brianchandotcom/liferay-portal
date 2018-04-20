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

package com.liferay.commerce.user.segment.service.impl;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentActionKeys;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentCriterionServiceBaseImpl;
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
public class CommerceUserSegmentCriterionServiceImpl
	extends CommerceUserSegmentCriterionServiceBaseImpl {

	@Override
	public CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
			long commerceUserSegmentEntryId, String type, String typeSettings,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			CommerceUserSegmentActionKeys.
				ADD_COMMERCE_USER_SEGMENTATION_CRITERION);

		return commerceUserSegmentCriterionLocalService.
			addCommerceUserSegmentCriterion(
				commerceUserSegmentEntryId, type, typeSettings, priority,
				serviceContext);
	}

	@Override
	public void deleteCommerceUserSegmentCriterion(
			long commerceUserSegmentCriterionId)
		throws PortalException {

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionLocalService.
				getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(),
			commerceUserSegmentCriterion.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentActionKeys.
				DELETE_COMMERCE_USER_SEGMENTATION_CRITERION);

		commerceUserSegmentCriterionLocalService.
			deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterion);
	}

	@Override
	public List<CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
			long commerceUserSegmentEntryId, int start, int end,
			OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.VIEW);

		return commerceUserSegmentCriterionLocalService.
			getCommerceUserSegmentCriteria(
				commerceUserSegmentEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentCriteriaCount(
			long commerceUserSegmentEntryId)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.VIEW);

		return commerceUserSegmentCriterionLocalService.
			getCommerceUserSegmentCriteriaCount(commerceUserSegmentEntryId);
	}

	@Override
	public CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
			long commerceUserSegmentCriterionId)
		throws PortalException {

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionLocalService.
				getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(),
			commerceUserSegmentCriterion.getCommerceUserSegmentEntryId(),
			ActionKeys.VIEW);

		return commerceUserSegmentCriterion;
	}

	@Override
	public CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
			long commerceUserSegmentCriterionId, String type,
			String typeSettings, double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionLocalService.
				getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(),
			commerceUserSegmentCriterion.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentActionKeys.
				ADD_COMMERCE_USER_SEGMENTATION_CRITERION);

		return commerceUserSegmentCriterionLocalService.
			updateCommerceUserSegmentCriterion(
				commerceUserSegmentCriterionId, type, typeSettings, priority,
				serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceUserSegmentEntry>
		_commerceUserSegmentEntryResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceUserSegmentCriterionServiceImpl.class,
				"_commerceUserSegmentEntryResourcePermission",
				CommerceUserSegmentEntry.class);

}