/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TermEntry {

	public TermEntry(
		long accountEntryId, boolean active, String channelName,
		long commerceChannelAccountEntryRelId, String name,
		boolean overrideEligibility, double priority, int type) {

		_accountEntryId = accountEntryId;
		_active = active;
		_channelName = channelName;
		_commerceChannelAccountEntryRelId = commerceChannelAccountEntryRelId;
		_name = name;
		_overrideEligibility = overrideEligibility;
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

	public boolean getOverrideEligibility() {
		return _overrideEligibility;
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
	private final boolean _overrideEligibility;
	private final double _priority;
	private final int _type;

}