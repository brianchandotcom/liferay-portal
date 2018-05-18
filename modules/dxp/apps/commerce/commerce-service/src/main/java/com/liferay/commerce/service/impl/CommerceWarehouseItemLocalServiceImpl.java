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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.base.CommerceWarehouseItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemLocalServiceImpl
	extends CommerceWarehouseItemLocalServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehouseLocalService.getCommerceWarehouse(
				commerceWarehouseId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceWarehouseItemId = counterLocalService.increment();

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemPersistence.create(commerceWarehouseItemId);

		commerceWarehouseItem.setGroupId(commerceWarehouse.getGroupId());
		commerceWarehouseItem.setCompanyId(user.getCompanyId());
		commerceWarehouseItem.setUserId(user.getUserId());
		commerceWarehouseItem.setUserName(user.getFullName());
		commerceWarehouseItem.setCommerceWarehouseId(commerceWarehouseId);
		commerceWarehouseItem.setCPInstanceId(cpInstanceId);
		commerceWarehouseItem.setQuantity(quantity);

		commerceWarehouseItemPersistence.update(commerceWarehouseItem);

		return commerceWarehouseItem;
	}

	@Override
	public void deleteCommerceWarehouseItems(long commerceWarehouseId) {
		commerceWarehouseItemPersistence.removeByCommerceWarehouseId(
			commerceWarehouseId);
	}

	@Override
	public void deleteCommerceWarehouseItemsByCPInstanceId(long cpInstanceId) {
		commerceWarehouseItemPersistence.removeByCPInstanceId(cpInstanceId);
	}

	@Override
	public CommerceWarehouseItem fetchCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId) {

		return commerceWarehouseItemPersistence.fetchByC_C(
			commerceWarehouseId, cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId) {

		return commerceWarehouseItemPersistence.findByCPInstanceId(
			cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {

		return commerceWarehouseItemFinder.findByCPInstanceId(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouseItem>
		getCommerceWarehouseItemsByCommerceWarehouseId(
			long commerceWarehouseId) {

		return commerceWarehouseItemPersistence.findByCommerceWarehouseId(
			commerceWarehouseId);
	}

	@Override
	public int getCommerceWarehouseItemsCount(long cpInstanceId) {
		return commerceWarehouseItemPersistence.countByCPInstanceId(
			cpInstanceId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) {
		return commerceWarehouseItemFinder.getCPInstanceQuantity(cpInstanceId);
	}

	@Override
	public CommerceWarehouseItem updateCommerceWarehouseItem(
			long commerceWarehouseItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemPersistence.findByPrimaryKey(
				commerceWarehouseItemId);

		commerceWarehouseItem.setQuantity(quantity);

		commerceWarehouseItemPersistence.update(commerceWarehouseItem);

		return commerceWarehouseItem;
	}

}