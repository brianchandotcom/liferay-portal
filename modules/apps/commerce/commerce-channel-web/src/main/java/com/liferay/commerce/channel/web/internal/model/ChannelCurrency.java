/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

/**
 * @author Fabio Monaco
 */
public class ChannelCurrency {

	public ChannelCurrency(
		long channelId, long channelRelId, String currencyCode, long currencyId,
		String currencyName, String currencySymbol) {

		_channelId = channelId;
		_channelRelId = channelRelId;
		_currencyCode = currencyCode;
		_currencyId = currencyId;
		_currencyName = currencyName;
		_currencySymbol = currencySymbol;
	}

	public long getChannelId() {
		return _channelId;
	}

	public long getChannelRelId() {
		return _channelRelId;
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public long getCurrencyId() {
		return _currencyId;
	}

	public String getCurrencyName() {
		return _currencyName;
	}

	public String getCurrencySymbol() {
		return _currencySymbol;
	}

	private final long _channelId;
	private final long _channelRelId;
	private final String _currencyCode;
	private final long _currencyId;
	private final String _currencyName;
	private final String _currencySymbol;

}