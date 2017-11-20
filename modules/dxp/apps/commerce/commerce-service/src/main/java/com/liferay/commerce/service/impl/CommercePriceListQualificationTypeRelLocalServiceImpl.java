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
import com.liferay.commerce.service.base.CommercePriceListQualificationTypeRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualificationTypeRelLocalServiceImpl
	extends CommercePriceListQualificationTypeRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListQualificationTypeRel
			addCommercePriceListQualificationTypeRel(
				long commercePriceListId,
				String commercePriceListQualificationType, int order,
				ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceListQualificationTypeRelId =
			counterLocalService.increment();

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				commercePriceListQualificationTypeRelPersistence.create(
					commercePriceListQualificationTypeRelId);

		commercePriceListQualificationTypeRel.setUuid(serviceContext.getUuid());
		commercePriceListQualificationTypeRel.setGroupId(groupId);
		commercePriceListQualificationTypeRel.setCompanyId(user.getCompanyId());
		commercePriceListQualificationTypeRel.setUserId(user.getUserId());
		commercePriceListQualificationTypeRel.setUserName(user.getFullName());
		commercePriceListQualificationTypeRel.setCommercePriceListId(
			commercePriceListId);
		commercePriceListQualificationTypeRel.
			setCommercePriceListQualificationType(
				commercePriceListQualificationType);
		commercePriceListQualificationTypeRel.setOrder(order);
		commercePriceListQualificationTypeRel.setExpandoBridgeAttributes(
			serviceContext);

		return commercePriceListQualificationTypeRelPersistence.update(
			commercePriceListQualificationTypeRel);
	}

	@Override
	public void deleteCommercePriceListQualificationTypeRels(
		long commercePriceListId) {

		commercePriceListQualificationTypeRelPersistence.
			removeByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListQualificationTypeRel
		fetchCommercePriceListQualificationTypeRel(
			String commercePriceListQualificationType,
			long commercePriceListId) {

		return commercePriceListQualificationTypeRelPersistence.fetchByC_C(
			commercePriceListQualificationType, commercePriceListId);
	}

	@Override
	public List<CommercePriceListQualificationTypeRel>
		getCommercePriceListQualificationTypeRels(long commercePriceListId) {

		return commercePriceListQualificationTypeRelPersistence.
			findByCommercePriceListId(commercePriceListId);
	}

	@Override
	public List<CommercePriceListQualificationTypeRel>
		getCommercePriceListQualificationTypeRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListQualificationTypeRel>
				orderByComparator) {

		return commercePriceListQualificationTypeRelPersistence.
			findByCommercePriceListId(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {

		return commercePriceListQualificationTypeRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListQualificationTypeRel
			updateCommercePriceListQualificationTypeRel(
				long commercePriceListQualificationTypeRelId, int order,
				ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				commercePriceListQualificationTypeRelPersistence.
					findByPrimaryKey(commercePriceListQualificationTypeRelId);

		commercePriceListQualificationTypeRel.setOrder(order);
		commercePriceListQualificationTypeRel.setExpandoBridgeAttributes(
			serviceContext);

		return commercePriceListQualificationTypeRelPersistence.update(
			commercePriceListQualificationTypeRel);
	}

}