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

package com.liferay.commerce.internal.subscription;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceSubscriptionEntryHelper.class
)
public class CommerceSubscriptionEntryHelperImpl
	implements CommerceSubscriptionEntryHelper {

	@Override
	public void checkCommerceSubscriptions(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrderItemLocalService.getSubscriptionCommerceOrderItems(
				commerceOrder.getCommerceOrderId());

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (_isNewSubscription(commerceOrderItem)) {
				CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

				if (cpInstance == null) {
					continue;
				}

				CPSubscriptionInfo cpSubscriptionInfo =
					cpInstance.getCPSubscriptionInfo();

				if (cpSubscriptionInfo != null) {
					String subscriptionType = null;

					CPDefinition cpDefinition = cpInstance.getCPDefinition();

					if (cpInstance.isSubscriptionEnabled() ||
						cpDefinition.isSubscriptionEnabled()) {

						subscriptionType =
							cpSubscriptionInfo.getSubscriptionType();
					}

					String deliverySubscriptionType = null;

					if (cpInstance.isDeliverySubscriptionEnabled() ||
						cpDefinition.isDeliverySubscriptionEnabled()) {

						deliverySubscriptionType =
							cpSubscriptionInfo.getDeliverySubscriptionType();
					}

					_commerceSubscriptionEntryLocalService.
						addCommerceSubscriptionEntry(
							commerceAccount.getUserId(),
							commerceOrder.getGroupId(),
							commerceOrderItem.getCommerceOrderItemId(),
							cpSubscriptionInfo.getSubscriptionLength(),
							subscriptionType,
							cpSubscriptionInfo.getMaxSubscriptionCycles(),
							cpSubscriptionInfo.
								getSubscriptionTypeSettingsProperties(),
							cpSubscriptionInfo.getDeliverySubscriptionLength(),
							deliverySubscriptionType,
							cpSubscriptionInfo.
								getDeliveryMaxSubscriptionCycles(),
							cpSubscriptionInfo.
								getDeliverySubscriptionTypeSettingsProperties());
				}
			}
		}
	}

	@Override
	public void checkDeliverySubscriptionEntriesStatus(
			List<CommerceSubscriptionEntry> commerceSubscriptionEntries)
		throws Exception {

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			checkDeliverySubscriptionStatus(commerceSubscriptionEntry);
		}
	}

	@Override
	public void checkDeliverySubscriptionStatus(
			CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		Date now = new Date();

		Date nextIterationDate =
			commerceSubscriptionEntry.getDeliveryNextIterationDate();

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		if ((commerceOrderItem != null) && now.after(nextIterationDate)) {
			if (Objects.equals(
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_ACTIVE,
					commerceSubscriptionEntry.
						getDeliverySubscriptionStatus()) &&
				!Objects.equals(
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED,
					commerceSubscriptionEntry.getSubscriptionStatus()) &&
				!Objects.equals(
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_CANCELLED,
					commerceSubscriptionEntry.getSubscriptionStatus())) {

				_addShipment(commerceOrderItem);

				_commerceSubscriptionEntryLocalService.
					incrementCommerceDeliverySubscriptionEntryCycle(
						commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId());
			}
			else {
				_commerceSubscriptionEntryLocalService.
					updateDeliverySubscriptionStatus(
						commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId(),
						CommerceSubscriptionEntryConstants.
							SUBSCRIPTION_STATUS_SUSPENDED);
			}
		}
	}

	@Override
	public void checkSubscriptionEntriesStatus(
			List<CommerceSubscriptionEntry> commerceSubscriptionEntries)
		throws Exception {

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			checkSubscriptionStatus(commerceSubscriptionEntry);
		}
	}

	@Override
	public void checkSubscriptionStatus(
			CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		Date now = new Date();

		Date nextIterationDate =
			commerceSubscriptionEntry.getNextIterationDate();

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		if ((commerceOrderItem != null) && now.after(nextIterationDate)) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			boolean subscriptionValid =
				_commerceSubscriptionEngine.getSubscriptionValidity(
					commerceOrder.getCommerceOrderId());

			if (subscriptionValid &&
				Objects.equals(
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_ACTIVE,
					commerceSubscriptionEntry.getSubscriptionStatus())) {

				_commerceSubscriptionEntryLocalService.
					incrementCommerceSubscriptionEntryCycle(
						commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId());
			}
			else {
				_commerceSubscriptionEntryLocalService.updateSubscriptionStatus(
					commerceSubscriptionEntry.getCommerceSubscriptionEntryId(),
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED);
			}
		}
	}

	private void _addShipment(CommerceOrderItem commerceOrderItem)
		throws Exception {

		_commerceShipmentLocalService.addDeliverySubscriptionCommerceShipment(
			commerceOrderItem.getUserId(),
			commerceOrderItem.getCommerceOrderItemId());
	}

	private boolean _isNewSubscription(CommerceOrderItem commerceOrderItem) {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.
				fetchCommerceSubscriptionEntry(
					commerceOrderItem.getCommerceOrderItemId());

		if (commerceSubscriptionEntry != null) {
			return false;
		}

		return true;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@Reference
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

}