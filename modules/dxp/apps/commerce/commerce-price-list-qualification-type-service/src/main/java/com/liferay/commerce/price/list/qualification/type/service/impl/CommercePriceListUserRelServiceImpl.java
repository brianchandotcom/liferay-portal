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

package com.liferay.commerce.price.list.qualification.type.service.impl;

import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.base.CommercePriceListUserRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListUserRelServiceImpl
	extends CommercePriceListUserRelServiceBaseImpl {

	@Override
	public CommercePriceListUserRel addCommercePriceListUserRel(
			long commercePriceListQualificationTypeRelId, String className,
			long classPK, ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListUserRelLocalService.addCommercePriceListUserRel(
			commercePriceListQualificationTypeRelId, className, classPK,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceListUserRel(long commercePriceListUserRelId)
		throws PortalException {

		commercePriceListUserRelLocalService.deleteCommercePriceListUserRel(
			commercePriceListUserRelId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		long classPK) {

		commercePriceListUserRelLocalService.deleteCommercePriceListUserRels(
			commercePriceListQualificationTypeRelId, className, classPK);
	}

	@Override
	public CommercePriceListUserRel getCommercePriceListUserRel(
			long commercePriceListUserRelId)
		throws PortalException {

		return commercePriceListUserRelLocalService.getCommercePriceListUserRel(
			commercePriceListUserRelId);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className) {

		return
			commercePriceListUserRelLocalService.getCommercePriceListUserRels(
				commercePriceListQualificationTypeRelId, className);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		int start, int end) {

		return
			commercePriceListUserRelLocalService.getCommercePriceListUserRels(
				commercePriceListQualificationTypeRelId, className, start, end,
				null);
	}

	@Override
	public List<CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, String className,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {

		return
			commercePriceListUserRelLocalService.getCommercePriceListUserRels(
				commercePriceListQualificationTypeRelId, className, start, end,
				orderByComparator);
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, String className) {

		return commercePriceListUserRelLocalService.
			getCommercePriceListUserRelsCount(
				commercePriceListQualificationTypeRelId, className);
	}

	@Override
	public CommercePriceListUserRel updateCommercePriceListUserRel(
			long commercePriceListUserRelId,
			long commercePriceListQualificationTypeRelId,
			ServiceContext serviceContext)
		throws PortalException {

		return
			commercePriceListUserRelLocalService.updateCommercePriceListUserRel(
				commercePriceListUserRelId,
				commercePriceListQualificationTypeRelId, serviceContext);
	}

}