/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.account.model;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderList {

	public OrderList(List<Order> orders, int count) {
		_orders = orders;
		_count = count;

		_success = true;
	}

	public OrderList(String[] errorMessages) {
		_errorMessages = errorMessages;

		_success = false;
	}

	public int getCount() {
		return _count;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public List<Order> getOrders() {
		return _orders;
	}

	public boolean getSuccess() {
		return _success;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setOrders(List<Order> orders) {
		_orders = orders;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private int _count;
	private String[] _errorMessages;
	private List<Order> _orders;
	private boolean _success;

}