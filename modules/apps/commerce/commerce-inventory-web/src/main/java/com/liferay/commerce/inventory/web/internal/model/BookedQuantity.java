/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class BookedQuantity {

	public BookedQuantity(
		String account, long commerceOrderId, String expirationDate,
		BigDecimal quantity, String unitOfMeasureKey) {

		_account = account;
		_commerceOrderId = commerceOrderId;
		_expirationDate = expirationDate;
		_quantity = quantity;
		_unitOfMeasureKey = unitOfMeasureKey;
	}

	public String getAccount() {
		return _account;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public String getExpirationDate() {
		return _expirationDate;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	private final String _account;
	private final long _commerceOrderId;
	private final String _expirationDate;
	private final BigDecimal _quantity;
	private final String _unitOfMeasureKey;

}