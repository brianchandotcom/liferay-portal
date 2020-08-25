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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListDiscountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceListDiscountRelLocalServiceBaseImpl
 */
public class CommercePriceListDiscountRelLocalServiceImpl
	extends CommercePriceListDiscountRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListDiscountRel addCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			commercePriceListDiscountRelPersistence.create(
				counterLocalService.increment());

		commercePriceListDiscountRel.setCompanyId(user.getCompanyId());
		commercePriceListDiscountRel.setUserId(user.getUserId());
		commercePriceListDiscountRel.setUserName(user.getFullName());
		commercePriceListDiscountRel.setCommerceDiscountId(commerceDiscountId);
		commercePriceListDiscountRel.setCommercePriceListId(
			commercePriceListId);
		commercePriceListDiscountRel.setOrder(order);
		commercePriceListDiscountRel.setExpandoBridgeAttributes(serviceContext);

		// Cache

		commercePriceListLocalService.cleanPriceListCache(
			serviceContext.getCompanyId());

		return commercePriceListDiscountRelPersistence.update(
			commercePriceListDiscountRel);
	}

	@Override
	public CommercePriceListDiscountRel deleteCommercePriceListDiscountRel(
			CommercePriceListDiscountRel commercePriceListDiscountRel)
		throws PortalException {

		commercePriceListDiscountRelPersistence.remove(
			commercePriceListDiscountRel);

		// Cache

		commercePriceListLocalService.cleanPriceListCache(
			commercePriceListDiscountRel.getCompanyId());

		return commercePriceListDiscountRel;
	}

	@Override
	public CommercePriceListDiscountRel deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws PortalException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			commercePriceListDiscountRelPersistence.findByPrimaryKey(
				commercePriceListDiscountRelId);

		return commercePriceListDiscountRelLocalService.
			deleteCommercePriceListDiscountRel(commercePriceListDiscountRel);
	}

	@Override
	public void deleteCommercePriceListDiscountRels(long commercePriceListId) {
		commercePriceListDiscountRelPersistence.removeByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public CommercePriceListDiscountRel fetchCommercePriceListDiscountRel(
		long commerceDiscountId, long commercePriceListId) {

		return commercePriceListDiscountRelPersistence.fetchByC_C(
			commerceDiscountId, commercePriceListId);
	}

	@Override
	public List<CommercePriceListDiscountRel> getCommercePriceListDiscountRels(
		long commercePriceListId) {

		return commercePriceListDiscountRelPersistence.
			findByCommercePriceListId(commercePriceListId);
	}

	@Override
	public List<CommercePriceListDiscountRel> getCommercePriceListDiscountRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		return commercePriceListDiscountRelPersistence.
			findByCommercePriceListId(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListDiscountRelsCount(long commercePriceListId) {
		return commercePriceListDiscountRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

}