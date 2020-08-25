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

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListChannelRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceListChannelRelLocalServiceBaseImpl
 */
public class CommercePriceListChannelRelLocalServiceImpl
	extends CommercePriceListChannelRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListChannelRel addCommercePriceListChannelRel(
			long commercePriceListId, long commerceChannelId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelPersistence.create(
				counterLocalService.increment());

		commercePriceListChannelRel.setCompanyId(user.getCompanyId());
		commercePriceListChannelRel.setUserId(user.getUserId());
		commercePriceListChannelRel.setUserName(user.getFullName());
		commercePriceListChannelRel.setCommerceChannelId(commerceChannelId);
		commercePriceListChannelRel.setCommercePriceListId(commercePriceListId);
		commercePriceListChannelRel.setOrder(order);
		commercePriceListChannelRel.setExpandoBridgeAttributes(serviceContext);

		// Cache

		commercePriceListLocalService.cleanPriceListCache(
			serviceContext.getCompanyId());

		return commercePriceListChannelRelPersistence.update(
			commercePriceListChannelRel);
	}

	@Override
	public CommercePriceListChannelRel deleteCommercePriceListChannelRel(
			CommercePriceListChannelRel commercePriceListChannelRel)
		throws PortalException {

		commercePriceListChannelRelPersistence.remove(
			commercePriceListChannelRel);

		// Cache

		commercePriceListLocalService.cleanPriceListCache(
			commercePriceListChannelRel.getCompanyId());

		return commercePriceListChannelRel;
	}

	@Override
	public CommercePriceListChannelRel deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelPersistence.findByPrimaryKey(
				commercePriceListChannelRelId);

		return commercePriceListChannelRelLocalService.
			deleteCommercePriceListChannelRel(commercePriceListChannelRel);
	}

	@Override
	public void deleteCommercePriceListChannelRels(long commercePriceListId) {
		commercePriceListChannelRelPersistence.removeByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public CommercePriceListChannelRel fetchCommercePriceListChannelRel(
		long commerceChannelId, long commercePriceListId) {

		return commercePriceListChannelRelPersistence.fetchByC_C(
			commerceChannelId, commercePriceListId);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId) {

		return commercePriceListChannelRelPersistence.findByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return commercePriceListChannelRelPersistence.findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(long commercePriceListId) {
		return commercePriceListChannelRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

}