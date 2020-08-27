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
 */
public class InventoryItem {

	public InventoryItem(String sku, int stock, int booked, int incoming) {
		_sku = sku;
		_stock = stock;

		if ((stock > 0) && (booked >= 0)) {
			_available = stock - booked;
		}
		else {
			_available = 0;
		}

		_booked = booked;
		_incoming = incoming;
	}

	public int getAvailable() {
		return _available;
	}

	public int getBooked() {
		return _booked;
	}

	public int getIncoming() {
		return _incoming;
	}

	public String getSku() {
		return _sku;
	}

	public int getStock() {
		return _stock;
	}

	private final int _available;
	private final int _booked;
	private final int _incoming;
	private final String _sku;
	private final int _stock;

}