/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.provider;

import com.liferay.notification.term.provider.NotificationTermProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Balazs Breier
 */
public class CommerceOrderAddressNotificationTermProvider
	implements NotificationTermProvider {

	@Override
	public Map<String, String> getNotificationTerms() {
		return HashMapBuilder.put(
			"commerce-order-billing-address-city",
			"[%COMMERCEORDER_BILLING_ADDRESS_CITY%]"
		).put(
			"commerce-order-billing-address-country",
			"[%COMMERCEORDER_BILLING_ADDRESS_COUNTRY%]"
		).put(
			"commerce-order-billing-address-name",
			"[%COMMERCEORDER_BILLING_ADDRESS_NAME%]"
		).put(
			"commerce-order-billing-address-phone-number",
			"[%COMMERCEORDER_BILLING_ADDRESS_PHONE_NUMBER%]"
		).put(
			"commerce-order-billing-address-region",
			"[%COMMERCEORDER_BILLING_ADDRESS_REGION%]"
		).put(
			"commerce-order-billing-address-street1",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET1%]"
		).put(
			"commerce-order-billing-address-street2",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET2%]"
		).put(
			"commerce-order-billing-address-street3",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET3%]"
		).put(
			"commerce-order-billing-address-zip",
			"[%COMMERCEORDER_BILLING_ADDRESS_ZIP%]"
		).put(
			"commerce-order-shipping-address-city",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_CITY%]"
		).put(
			"commerce-order-shipping-address-country",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_COUNTRY%]"
		).put(
			"commerce-order-shipping-address-name",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_NAME%]"
		).put(
			"commerce-order-shipping-address-phone-number",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_PHONE_NUMBER%]"
		).put(
			"commerce-order-shipping-address-region",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_REGION%]"
		).put(
			"commerce-order-shipping-address-street1",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET1%]"
		).put(
			"commerce-order-shipping-address-street2",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET2%]"
		).put(
			"commerce-order-shipping-address-street3",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET3%]"
		).put(
			"commerce-order-shipping-address-zip",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_ZIP%]"
		).build();
	}

}