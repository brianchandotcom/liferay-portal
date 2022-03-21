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