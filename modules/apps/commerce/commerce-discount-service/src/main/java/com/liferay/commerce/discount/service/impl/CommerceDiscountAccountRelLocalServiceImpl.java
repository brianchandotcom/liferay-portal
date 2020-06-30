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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountAccountRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommerceDiscountAccountRelLocalServiceBaseImpl
 */
public class CommerceDiscountAccountRelLocalServiceImpl
	extends CommerceDiscountAccountRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountAccountRel addCommerceDiscountAccountRel(
			long commerceDiscountId, long commerceAccountId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceDiscountAccountRelId = counterLocalService.increment();

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			commerceDiscountAccountRelPersistence.create(
				commerceDiscountAccountRelId);

		commerceDiscountAccountRel.setCompanyId(user.getCompanyId());
		commerceDiscountAccountRel.setUserId(user.getUserId());
		commerceDiscountAccountRel.setUserName(user.getFullName());
		commerceDiscountAccountRel.setCommerceAccountId(commerceAccountId);
		commerceDiscountAccountRel.setCommerceDiscountId(commerceDiscountId);

		return commerceDiscountAccountRelPersistence.update(
			commerceDiscountAccountRel);
	}

	@Override
	public void deleteCommerceDiscountAccountRelsBycommerceAccountId(
		long commerceAccountId) {

		commerceDiscountAccountRelPersistence.removeByCommerceAccountId(
			commerceAccountId);
	}

	@Override
	public void deleteCommerceDiscountAccountRelsByCommerceDiscountId(
		long commerceDiscountId) {

		commerceDiscountAccountRelPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public CommerceDiscountAccountRel fetchCommerceDiscountAccountRel(
		long commerceDiscountId, long commerceAccountId) {

		return commerceDiscountAccountRelPersistence.fetchByC_C(
			commerceAccountId, commerceDiscountId);
	}

	@Override
	public List<CommerceDiscountAccountRel> getCommerceDiscountAccountRels(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return commerceDiscountAccountRelPersistence.findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountAccountRelsCount(long commerceDiscountId) {
		return commerceDiscountAccountRelPersistence.countByCommerceDiscountId(
			commerceDiscountId);
	}

}