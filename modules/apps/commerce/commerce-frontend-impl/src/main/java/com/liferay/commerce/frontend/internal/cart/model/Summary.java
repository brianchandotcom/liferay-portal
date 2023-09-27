/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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