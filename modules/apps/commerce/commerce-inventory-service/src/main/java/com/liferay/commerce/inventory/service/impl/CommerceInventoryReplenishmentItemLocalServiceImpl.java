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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.service.base.CommerceInventoryReplenishmentItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryReplenishmentItemLocalServiceImpl
	extends CommerceInventoryReplenishmentItemLocalServiceBaseImpl {

	@Override
	public CommerceInventoryReplenishmentItem
			addCommerceInventoryReplenishmentItem(
				long userId, long commerceInventoryWarehouseId, String sku,
				Date availabilityDate, int quantity)
		throws PortalException {

		if (Validator.isNull(sku)) {
			throw new PortalException("SKU code is null");
		}

		User user = userLocalService.getUser(userId);

		long commerceInventoryReplenishmentItemId =
			counterLocalService.increment();

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItemPersistence.create(
				commerceInventoryReplenishmentItemId);

		commerceInventoryReplenishmentItem.setCompanyId(user.getCompanyId());
		commerceInventoryReplenishmentItem.setUserId(userId);
		commerceInventoryReplenishmentItem.setUserName(user.getFullName());
		commerceInventoryReplenishmentItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceInventoryReplenishmentItem.setSku(sku);
		commerceInventoryReplenishmentItem.setAvailabilityDate(
			availabilityDate);
		commerceInventoryReplenishmentItem.setQuantity(quantity);

		return commerceInventoryReplenishmentItemPersistence.update(
			commerceInventoryReplenishmentItem);
	}

	@Override
	public List<CommerceInventoryReplenishmentItem>
		getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
			long companyId, String sku, int start, int end) {

		return commerceInventoryReplenishmentItemPersistence.findByC_S(
			companyId, sku, start, end);
	}

	@Override
	public long getCommerceInventoryReplenishmentItemsCount(
		long commerceInventoryWarehouseId, String sku) {

		DynamicQuery dynamicQuery =
			commerceInventoryReplenishmentItemLocalService.dynamicQuery();

		Projection projection = ProjectionFactoryUtil.sum("quantity");

		dynamicQuery.setProjection(projection);

		Property commerceInventoryWarehouseIdProperty =
			PropertyFactoryUtil.forName("commerceInventoryWarehouseId");

		dynamicQuery.add(
			commerceInventoryWarehouseIdProperty.eq(
				commerceInventoryWarehouseId));

		Property skuProperty = PropertyFactoryUtil.forName("sku");

		dynamicQuery.add(skuProperty.eq(sku));

		List<Long> results =
			commerceInventoryReplenishmentItemLocalService.dynamicQuery(
				dynamicQuery);

		if (results.get(0) == null) {
			return 0;
		}

		return results.get(0);
	}

	@Override
	public int getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
		long companyId, String sku) {

		return commerceInventoryReplenishmentItemPersistence.countByC_S(
			companyId, sku);
	}

	@Override
	public CommerceInventoryReplenishmentItem
			updateCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId,
				Date availabilityDate, int quantity, long mvccVersion)
		throws PortalException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItemPersistence.findByPrimaryKey(
				commerceInventoryReplenishmentItemId);

		if (commerceInventoryReplenishmentItem.getMvccVersion() !=
				mvccVersion) {

			throw new MVCCException();
		}

		commerceInventoryReplenishmentItem.setAvailabilityDate(
			availabilityDate);
		commerceInventoryReplenishmentItem.setQuantity(quantity);

		return commerceInventoryReplenishmentItemPersistence.update(
			commerceInventoryReplenishmentItem);
	}

}