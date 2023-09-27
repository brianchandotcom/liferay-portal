/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.account.web.internal.model;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
public class ShippingOption {

	public ShippingOption(
		long accountEntryId, String active, String channelName,
		long commerceChannelId, String shippingMethodName,
		String shippingOptionName) {

		_accountEntryId = accountEntryId;
		_active = active;
		_channelName = channelName;
		_commerceChannelId = commerceChannelId;
		_shippingMethodName = shippingMethodName;
		_shippingOptionName = shippingOptionName;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getActive() {
		return _active;
	}

	public String getChannelName() {
		return _channelName;
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public String getShippingMethodName() {
		return _shippingMethodName;
	}

	public String getShippingOptionName() {
		return _shippingOptionName;
	}

	private final long _accountEntryId;
	private final String _active;
	private final String _channelName;
	private final long _commerceChannelId;
	private final String _shippingMethodName;
	private final String _shippingOptionName;

}