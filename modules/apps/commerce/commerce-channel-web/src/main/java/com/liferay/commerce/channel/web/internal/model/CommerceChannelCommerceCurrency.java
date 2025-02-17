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
		_commerceCurrencyCode = commerceCurrencyCode;
		_commerceCurrencyId = commerceCurrencyId;
		_commerceCurrencyName = commerceCurrencyName;
		_commerceCurrencySymbol = commerceCurrencySymbol;
	}

	public String getCode() {
		return _commerceCurrencyCode;
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public long getCommerceChannelRelId() {
		return _commerceChannelRelId;
	}

	public long getId() {
		return _commerceCurrencyId;
	}

	public String getName() {
		return _commerceCurrencyName;
	}

	public String getSymbol() {
		return _commerceCurrencySymbol;
	}

	private final long _commerceChannelId;
	private final long _commerceChannelRelId;
	private final String _commerceCurrencyCode;
	private final long _commerceCurrencyId;
	private final String _commerceCurrencyName;
	private final String _commerceCurrencySymbol;

}