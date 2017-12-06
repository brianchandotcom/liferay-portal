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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.base.CommerceOrderItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemLocalServiceImpl
	extends CommerceOrderItemLocalServiceBaseImpl {

	@Override
	public CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpDefinitionId, long cpInstanceId,
			int quantity, int shippedQuantity, String json, double price,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(cpDefinitionId, cpInstanceId);

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		long commerceOrderItemId = counterLocalService.increment();

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.create(commerceOrderItemId);

		commerceOrderItem.setGroupId(groupId);
		commerceOrderItem.setCompanyId(user.getCompanyId());
		commerceOrderItem.setUserId(user.getUserId());
		commerceOrderItem.setUserName(user.getFullName());
		commerceOrderItem.setCommerceOrderId(commerceOrderId);
		commerceOrderItem.setCPDefinitionId(cpDefinitionId);
		commerceOrderItem.setCPInstanceId(cpInstanceId);
		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setShippedQuantity(shippedQuantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setPrice(price);

		CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

		commerceOrderItem.setTitleMap(cpDefinition.getTitleMap());

		commerceOrderItem.setSku(cpInstance.getSku());
		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		commerceOrderItemPersistence.update(commerceOrderItem);

		return commerceOrderItem;
	}

	@Override
	public CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpDefinitionId, long cpInstanceId,
			int quantity, String json, double price,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceOrderItem(
			commerceOrderId, cpDefinitionId, cpInstanceId, quantity, 0, json,
			price, serviceContext);
	}

	@Override
	public CommerceOrderItem deleteCommerceOrderItem(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		// Commerce order item

		commerceOrderItemPersistence.remove(commerceOrderItem);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceOrderItem.getCommerceOrderItemId());

		return commerceOrderItem;
	}

	@Override
	public CommerceOrderItem deleteCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		return commerceOrderItemLocalService.deleteCommerceOrderItem(
			commerceOrderItem);
	}

	@Override
	public void deleteCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemPersistence.findByCommerceOrderId(
				commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			deleteCommerceOrderItem(commerceOrderItem);
		}
	}

	@Override
	public void deleteCommerceOrderItemsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemPersistence.findByCPDefinitionId(cpDefinitionId);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			deleteCommerceOrderItem(commerceOrderItem);
		}
	}

	@Override
	public void deleteCommerceOrderItemsByCPInstanceId(long cpInstanceId)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemPersistence.findByCPInstanceId(cpInstanceId);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			deleteCommerceOrderItem(commerceOrderItem);
		}
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end) {

		return commerceOrderItemPersistence.findByCommerceOrderId(
			commerceOrderId, start, end);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return commerceOrderItemPersistence.findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long groupId, long commerceAddressId, int start, int end) {

		return commerceOrderItemFinder.findByG_C(
			groupId, commerceAddressId, start, end);
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId) {
		return commerceOrderItemPersistence.countByCommerceOrderId(
			commerceOrderId);
	}

	@Override
	public CommerceOrderItem incrementShippedQuantity(
			long commerceOrderItemId, int shippedQuantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		shippedQuantity =
			commerceOrderItem.getShippedQuantity() + shippedQuantity;

		commerceOrderItem.setShippedQuantity(shippedQuantity);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity, String json, double price)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setPrice(price);

		commerceOrderItemPersistence.update(commerceOrderItem);

		return commerceOrderItem;
	}

	@Override
	public void validate(long cpDefinitionId, long cpInstanceId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpInstanceId > 0) {
			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				cpInstanceId);

			if (cpInstance.getCPDefinitionId() !=
					cpDefinition.getCPDefinitionId()) {

				throw new NoSuchCPInstanceException(
					"CPInstance " + cpInstance.getCPInstanceId() +
						" belongs to a different CPDefinition than " +
							cpDefinition.getCPDefinitionId());
			}
		}
	}

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

}