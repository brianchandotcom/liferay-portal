/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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