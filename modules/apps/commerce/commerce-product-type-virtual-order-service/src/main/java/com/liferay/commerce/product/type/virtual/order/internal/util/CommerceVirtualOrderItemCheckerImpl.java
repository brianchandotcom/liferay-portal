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

package com.liferay.commerce.product.type.virtual.order.internal.util;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.order.util.CommerceVirtualOrderItemChecker;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceVirtualOrderItemChecker.class)
public class CommerceVirtualOrderItemCheckerImpl
	implements CommerceVirtualOrderItemChecker {

	@Override
	public void checkCommerceVirtualOrderItems(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return;
		}

		_checkCommerceVirtualOrderItems(commerceOrder);
	}

	private void _checkCommerceVirtualOrderItems(CommerceOrder commerceOrder)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				_commerceSubscriptionEntryLocalService.
					fetchCommerceSubscriptionEntryByCommerceOrderItemId(
						commerceOrderItem.getCommerceOrderItemId());

			CommerceVirtualOrderItem commerceVirtualOrderItem =
				_getCommerceVirtualOrderItem(
					commerceOrderItem, commerceSubscriptionEntry);

			if ((commerceVirtualOrderItem == null) &&
				((commerceSubscriptionEntry == null) ||
				 (commerceSubscriptionEntry.getCurrentCycle() == 1))) {

				CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

				if (!VirtualCPTypeConstants.NAME.equals(
						cpDefinition.getProductTypeName())) {

					continue;
				}

				// Add commerce virtual order item

				commerceVirtualOrderItem =
					_commerceVirtualOrderItemLocalService.
						addCommerceVirtualOrderItem(
							commerceOrderItem.getCommerceOrderItemId(),
							_getServiceContext(commerceOrder));
			}

			if (commerceVirtualOrderItem == null) {
				continue;
			}

			if (commerceOrderItem.isSubscription()) {

				// Update commerce virtual order item dates

				commerceVirtualOrderItem =
					_commerceVirtualOrderItemLocalService.
						updateCommerceVirtualOrderItemDates(
							commerceVirtualOrderItem.
								getCommerceVirtualOrderItemId());
			}

			if (commerceOrder.getOrderStatus() ==
					commerceVirtualOrderItem.getActivationStatus()) {

				// Set commerce virtual order item active

				_commerceVirtualOrderItemLocalService.setActive(
					commerceVirtualOrderItem.getCommerceVirtualOrderItemId(),
					true);
			}
		}
	}

	private CommerceVirtualOrderItem _getCommerceVirtualOrderItem(
		CommerceOrderItem commerceOrderItem,
		CommerceSubscriptionEntry commerceSubscriptionCycleEntry) {

		if (!commerceOrderItem.isSubscription()) {
			return _commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					commerceOrderItem.getCommerceOrderItemId());
		}

		if (commerceSubscriptionCycleEntry == null) {
			return null;
		}

		return _commerceVirtualOrderItemLocalService.
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				commerceSubscriptionCycleEntry.getCommerceOrderItemId());
	}

	private ServiceContext _getServiceContext(CommerceOrder commerceOrder)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		serviceContext.setUserId(commerceAccount.getUserId());

		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		serviceContext.setUuid(uuid.toString());

		return serviceContext;
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

}