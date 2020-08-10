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

package com.liferay.commerce.frontend.internal.cart.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Summary {

	public Summary(String subtotal, String total, int itemsQuantity) {
		_subtotal = subtotal;
		_total = total;
		_itemsQuantity = itemsQuantity;
	}

	public String getDiscount() {
		return _discount;
	}

	public int getItemsQuantity() {
		return _itemsQuantity;
	}

	public String getSubtotal() {
		return _subtotal;
	}

	public String getTotal() {
		return _total;
	}

	public void setDiscount(String discount) {
		_discount = discount;
	}

	public void setItemsQuantity(int itemsQuantity) {
		_itemsQuantity = itemsQuantity;
	}

	public void setSubtotal(String subtotal) {
		_subtotal = subtotal;
	}

	public void setTotal(String total) {
		_total = total;
	}

	private String _discount;
	private int _itemsQuantity;
	private String _subtotal;
	private String _total;

}