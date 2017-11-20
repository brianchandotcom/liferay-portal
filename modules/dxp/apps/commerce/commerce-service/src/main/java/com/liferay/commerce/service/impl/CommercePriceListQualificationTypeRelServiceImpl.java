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

import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.service.base.CommercePriceListQualificationTypeRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualificationTypeRelServiceImpl
	extends CommercePriceListQualificationTypeRelServiceBaseImpl {

	@Override
	public CommercePriceListQualificationTypeRel
			addCommercePriceListQualificationTypeRel(
				long commercePriceListId,
				String commercePriceListQualificationType, int order,
				ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListQualificationTypeRelLocalService.
			addCommercePriceListQualificationTypeRel(
				commercePriceListId, commercePriceListQualificationType, order,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListQualificationTypeRel(
			long commercePriceListQualificationTypeRelId)
		throws PortalException {

		commercePriceListQualificationTypeRelLocalService.
			deleteCommercePriceListQualificationTypeRel(
				commercePriceListQualificationTypeRelId);
	}

	@Override
	public CommercePriceListQualificationTypeRel
		fetchCommercePriceListQualificationTypeRel(
			String commercePriceListQualificationType,
			long commercePriceListId) {

		return commercePriceListQualificationTypeRelLocalService.
			fetchCommercePriceListQualificationTypeRel(
				commercePriceListQualificationType, commercePriceListId);
	}

	@Override
	public List<CommercePriceListQualificationTypeRel>
		getCommercePriceListQualificationTypeRels(long commercePriceListId) {

		return commercePriceListQualificationTypeRelLocalService.
			getCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	@Override
	public List<CommercePriceListQualificationTypeRel>
		getCommercePriceListQualificationTypeRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListQualificationTypeRel>
				orderByComparator) {

		return commercePriceListQualificationTypeRelLocalService.
			getCommercePriceListQualificationTypeRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {

		return commercePriceListQualificationTypeRelLocalService.
			getCommercePriceListQualificationTypeRelsCount(commercePriceListId);
	}

	@Override
	public CommercePriceListQualificationTypeRel
			updateCommercePriceListQualificationTypeRel(
				long commercePriceListQualificationTypeRelId, int order,
				ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListQualificationTypeRelLocalService.
			updateCommercePriceListQualificationTypeRel(
				commercePriceListQualificationTypeRelId, order, serviceContext);
	}

}