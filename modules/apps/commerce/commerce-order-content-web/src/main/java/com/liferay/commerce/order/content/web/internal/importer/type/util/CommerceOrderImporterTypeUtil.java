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

package com.liferay.commerce.order.content.web.internal.importer.type.util;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderImporterTypeUtil {

	public static CommerceOrder getCommerceOrder(
			CommerceContextFactory commerceContextFactory,
			CommerceOrder commerceOrder,
			CommerceOrderImporterItem[] commerceOrderImporterItems,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderService commerceOrderService,
			UserLocalService userLocalService)
		throws Exception {

		CommerceOrder tempCommerceOrder = commerceOrderService.addCommerceOrder(
			commerceOrder.getGroupId(), commerceOrder.getCommerceAccountId(),
			commerceOrder.getCommerceCurrencyId(),
			commerceOrder.getCommerceOrderTypeId());

		CommerceContext commerceContext = commerceContextFactory.create(
			tempCommerceOrder.getCompanyId(), tempCommerceOrder.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			tempCommerceOrder.getCommerceOrderId(),
			tempCommerceOrder.getCommerceAccountId());

		for (CommerceOrderImporterItem commerceOrderImporterItem :
				commerceOrderImporterItems) {

			commerceOrderItemService.addCommerceOrderItem(
				tempCommerceOrder.getCommerceOrderId(),
				commerceOrderImporterItem.getCPInstanceId(),
				commerceOrderImporterItem.getJSON(),
				commerceOrderImporterItem.getQuantity(), 0, commerceContext,
				_getServiceContext(userLocalService));
		}

		return tempCommerceOrder;
	}

	public static CommerceOrder getCommerceOrder(
			CommerceContextFactory commerceContextFactory,
			CommerceOrder commerceOrder,
			List<CommerceOrderItem> commerceOrderItems,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderService commerceOrderService,
			UserLocalService userLocalService)
		throws Exception {

		CommerceOrder tempCommerceOrder = commerceOrderService.addCommerceOrder(
			commerceOrder.getGroupId(), commerceOrder.getCommerceAccountId(),
			commerceOrder.getCommerceCurrencyId(),
			commerceOrder.getCommerceOrderTypeId());

		CommerceContext commerceContext = commerceContextFactory.create(
			tempCommerceOrder.getCompanyId(), tempCommerceOrder.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			tempCommerceOrder.getCommerceOrderId(),
			tempCommerceOrder.getCommerceAccountId());

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			commerceOrderItemService.addCommerceOrderItem(
				tempCommerceOrder.getCommerceOrderId(),
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getJson(), commerceOrderItem.getQuantity(), 0,
				commerceContext, _getServiceContext(userLocalService));
		}

		return tempCommerceOrder;
	}

	private static ServiceContext _getServiceContext(
			UserLocalService userLocalService)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		User user = userLocalService.getUser(PrincipalThreadLocal.getUserId());

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setLanguageId(user.getLanguageId());
		serviceContext.setScopeGroupId(user.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

}