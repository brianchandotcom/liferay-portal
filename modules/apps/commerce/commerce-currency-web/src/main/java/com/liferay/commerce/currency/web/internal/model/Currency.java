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

package com.liferay.commerce.currency.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Currency {

	public Currency(
		long accountEntryId, boolean active, String channelName,
		long commerceChannelAccountEntryRelId, String name, double priority,
		int type) {

		_accountEntryId = accountEntryId;
		_active = active;
		_channelName = channelName;
		_commerceChannelAccountEntryRelId = commerceChannelAccountEntryRelId;
		_name = name;
		_priority = priority;
		_type = type;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public boolean getActive() {
		return _active;
	}

	public String getChannelName() {
		return _channelName;
	}

	public long getCommerceChannelAccountEntryRelId() {
		return _commerceChannelAccountEntryRelId;
	}

	public String getName() {
		return _name;
	}

	public double getPriority() {
		return _priority;
	}

	public int getType() {
		return _type;
	}

	private final long _accountEntryId;
	private final boolean _active;
	private final String _channelName;
	private final long _commerceChannelAccountEntryRelId;
	private final String _name;
	private final double _priority;
	private final int _type;

}