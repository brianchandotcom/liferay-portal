/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class InstanceTierPriceEntry {

	public InstanceTierPriceEntry(
		long priceTierEntryId, String createDateString, BigDecimal minQuantity,
		String price) {

		_priceTierEntryId = priceTierEntryId;
		_createDateString = createDateString;
		_minQuantity = minQuantity;
		_price = price;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public BigDecimal getMinQuantity() {
		return _minQuantity;
	}

	public String getPrice() {
		return _price;
	}

	public long getTierPriceEntryId() {
		return _priceTierEntryId;
	}

	private final String _createDateString;
	private final BigDecimal _minQuantity;
	private final String _price;
	private final long _priceTierEntryId;

}