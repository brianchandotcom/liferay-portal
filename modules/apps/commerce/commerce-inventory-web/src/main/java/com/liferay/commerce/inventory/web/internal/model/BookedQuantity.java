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

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class BookedQuantity {

	public BookedQuantity(
		String account, long commerceOrderId, int quantity,
		String expirationDate) {

		_account = account;
		_commerceOrderId = commerceOrderId;
		_quantity = quantity;
		_expirationDate = expirationDate;
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

	public int getQuantity() {
		return _quantity;
	}

	private final String _account;
	private final long _commerceOrderId;
	private final String _expirationDate;
	private final int _quantity;

}