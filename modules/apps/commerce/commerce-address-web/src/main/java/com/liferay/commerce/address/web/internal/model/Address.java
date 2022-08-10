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

package com.liferay.commerce.address.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Address {

	public Address(
		long accountEntryId, String channelName,
		long commerceChannelAccountEntryRelId, String description, String name,
		int type) {

		_accountEntryId = accountEntryId;
		_channelName = channelName;
		_commerceChannelAccountEntryRelId = commerceChannelAccountEntryRelId;
		_description = description;
		_name = name;
		_type = type;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getChannelName() {
		return _channelName;
	}

	public long getCommerceChannelAccountEntryRelId() {
		return _commerceChannelAccountEntryRelId;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public int getType() {
		return _type;
	}

	private final long _accountEntryId;
	private final String _channelName;
	private final long _commerceChannelAccountEntryRelId;
	private final String _description;
	private final String _name;
	private final int _type;

}