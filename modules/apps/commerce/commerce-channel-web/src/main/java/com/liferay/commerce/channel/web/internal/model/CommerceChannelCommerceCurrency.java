/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

/**
 * @author Fabio Monaco
 */
public class CommerceChannelCommerceCurrency {

	public CommerceChannelCommerceCurrency(
		long commerceChannelId, long commerceChannelRelId,
		String commerceCurrencyCode, long commerceCurrencyId,
		String commerceCurrencyName, String commerceCurrencySymbol) {

		_commerceChannelId = commerceChannelId;
		_commerceChannelRelId = commerceChannelRelId;

		_code = commerceCurrencyCode;
		_id = commerceCurrencyId;
		_name = commerceCurrencyName;
		_symbol = commerceCurrencySymbol;
	}

	public String getCode() {
		return _code;
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public long getCommerceChannelRelId() {
		return _commerceChannelRelId;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getSymbol() {
		return _symbol;
	}

	private final String _code;
	private final long _commerceChannelId;
	private final long _commerceChannelRelId;
	private final long _id;
	private final String _name;
	private final String _symbol;

}