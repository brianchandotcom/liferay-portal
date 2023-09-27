/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.account.web.internal.model;

/**
 * @author Crescenzo Rega
 */
public class User {

	public User(
		long accountEntryId, String channelName,
		long commerceChannelAccountEntryRelId, String name, int type) {

		_accountEntryId = accountEntryId;
		_channelName = channelName;
		_commerceChannelAccountEntryRelId = commerceChannelAccountEntryRelId;
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

	public String getName() {
		return _name;
	}

	public int getType() {
		return _type;
	}

	private final long _accountEntryId;
	private final String _channelName;
	private final long _commerceChannelAccountEntryRelId;
	private final String _name;
	private final int _type;

}