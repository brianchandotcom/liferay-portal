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

package com.liferay.commerce.account.internal.upgrade.v10_0_0;

import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alessio Antonio Rendina
 */
public class AccountEntryUpgradeProcess extends UpgradeProcess {

	public AccountEntryUpgradeProcess(
		AddressLocalService addressLocalService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		CommerceTermEntryLocalService commerceTermEntryLocalService) {

		_addressLocalService = addressLocalService;
		_commerceChannelAccountEntryRelLocalService =
			commerceChannelAccountEntryRelLocalService;
		_commerceTermEntryLocalService = commerceTermEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("AccountEntry", "defaultBillingAddressId") &&
			hasColumn("AccountEntry", "defaultShippingAddressId")) {

			_updateDefaultAddresses();
		}

		if (hasColumn("AccountEntry", "defaultDeliveryCTermEntryId") &&
			hasColumn("AccountEntry", "defaultPaymentCTermEntryId")) {

			_updateDefaultCommerceTermEntries();
		}
	}

	private void _updateDefaultAddresses() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select accountEntryId, defaultBillingAddressId, " +
					"defaultShippingAddressId, userId from AccountEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long accountEntryId = resultSet.getLong("accountEntryId");
				long defaultBillingAddressId = resultSet.getLong(
					"defaultBillingAddressId");
				long defaultShippingAddressId = resultSet.getLong(
					"defaultShippingAddressId");
				long userId = resultSet.getLong("userId");

				Address billingAddress = _addressLocalService.fetchAddress(
					defaultBillingAddressId);

				if (billingAddress != null) {
					_commerceChannelAccountEntryRelLocalService.
						addCommerceChannelAccountEntryRel(
							userId, accountEntryId, Address.class.getName(),
							billingAddress.getAddressId(), 0, false, 0,
							CommerceChannelAccountEntryRelConstants.
								TYPE_BILLING_ADDRESS);
				}

				Address shippingAddress = _addressLocalService.fetchAddress(
					defaultShippingAddressId);

				if (shippingAddress != null) {
					_commerceChannelAccountEntryRelLocalService.
						addCommerceChannelAccountEntryRel(
							userId, accountEntryId, Address.class.getName(),
							shippingAddress.getAddressId(), 0, false, 0,
							CommerceChannelAccountEntryRelConstants.
								TYPE_SHIPPING_ADDRESS);
				}
			}
		}
	}

	private void _updateDefaultCommerceTermEntries() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select accountEntryId, defaultDeliveryCTermEntryId, " +
					"defaultPaymentCTermEntryId, userId from AccountEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long accountEntryId = resultSet.getLong("accountEntryId");
				long defaultDeliveryCTermEntryId = resultSet.getLong(
					"defaultDeliveryCTermEntryId");
				long defaultPaymentCTermEntryId = resultSet.getLong(
					"defaultPaymentCTermEntryId");
				long userId = resultSet.getLong("userId");

				CommerceTermEntry deliveryCommerceTermEntry =
					_commerceTermEntryLocalService.fetchCommerceTermEntry(
						defaultDeliveryCTermEntryId);

				if (deliveryCommerceTermEntry != null) {
					_commerceChannelAccountEntryRelLocalService.
						addCommerceChannelAccountEntryRel(
							userId, accountEntryId,
							CommerceTermEntry.class.getName(),
							defaultDeliveryCTermEntryId, 0, false, 0,
							CommerceChannelAccountEntryRelConstants.
								TYPE_DELIVERY_TERM);
				}

				CommerceTermEntry paymentCommerceTermEntry =
					_commerceTermEntryLocalService.fetchCommerceTermEntry(
						defaultPaymentCTermEntryId);

				if (paymentCommerceTermEntry != null) {
					_commerceChannelAccountEntryRelLocalService.
						addCommerceChannelAccountEntryRel(
							userId, accountEntryId,
							CommerceTermEntry.class.getName(),
							defaultPaymentCTermEntryId, 0, false, 0,
							CommerceChannelAccountEntryRelConstants.
								TYPE_PAYMENT_TERM);
				}
			}
		}

		alterTableDropColumn("AccountEntry", "defaultDeliveryCTermEntryId");
		alterTableDropColumn("AccountEntry", "defaultPaymentCTermEntryId");
	}

	private final AddressLocalService _addressLocalService;
	private final CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;
	private final CommerceTermEntryLocalService _commerceTermEntryLocalService;

}