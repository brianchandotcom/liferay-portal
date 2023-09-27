/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSubscriptionInfo {

	public CPSubscriptionInfo(
		int subscriptionLength, String subscriptionType,
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
		long maxSubscriptionCycles, int deliverySubscriptionLength,
		String deliverySubscriptionType,
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
		long deliveryMaxSubscriptionCycles) {

		_subscriptionLength = subscriptionLength;
		_subscriptionType = subscriptionType;
		_subscriptionTypeSettingsUnicodeProperties =
			subscriptionTypeSettingsUnicodeProperties;
		_maxSubscriptionCycles = maxSubscriptionCycles;
		_deliverySubscriptionLength = deliverySubscriptionLength;
		_deliverySubscriptionType = deliverySubscriptionType;
		_deliverySubscriptionTypeSettingsUnicodeProperties =
			deliverySubscriptionTypeSettingsUnicodeProperties;
		_deliveryMaxSubscriptionCycles = deliveryMaxSubscriptionCycles;
	}

	public long getDeliveryMaxSubscriptionCycles() {
		return _deliveryMaxSubscriptionCycles;
	}

	public int getDeliverySubscriptionLength() {
		return _deliverySubscriptionLength;
	}

	public String getDeliverySubscriptionType() {
		return _deliverySubscriptionType;
	}

	public UnicodeProperties
		getDeliverySubscriptionTypeSettingsUnicodeProperties() {

		return _deliverySubscriptionTypeSettingsUnicodeProperties;
	}

	public long getMaxSubscriptionCycles() {
		return _maxSubscriptionCycles;
	}

	public int getSubscriptionLength() {
		return _subscriptionLength;
	}

	public String getSubscriptionType() {
		return _subscriptionType;
	}

	public UnicodeProperties getSubscriptionTypeSettingsUnicodeProperties() {
		return _subscriptionTypeSettingsUnicodeProperties;
	}

	private final long _deliveryMaxSubscriptionCycles;
	private final int _deliverySubscriptionLength;
	private final String _deliverySubscriptionType;
	private final UnicodeProperties
		_deliverySubscriptionTypeSettingsUnicodeProperties;
	private final long _maxSubscriptionCycles;
	private final int _subscriptionLength;
	private final String _subscriptionType;
	private final UnicodeProperties _subscriptionTypeSettingsUnicodeProperties;

}