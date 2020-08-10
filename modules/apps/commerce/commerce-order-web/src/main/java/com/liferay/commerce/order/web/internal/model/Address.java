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

package com.liferay.commerce.order.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Address {

	public Address(long addressId, String title, String description) {
		_addressId = addressId;
		_title = title;
		_description = description;
	}

	public long getAddressId() {
		return _addressId;
	}

	public String getDescription() {
		return _description;
	}

	public String getTitle() {
		return _title;
	}

	private final long _addressId;
	private final String _description;
	private final String _title;

}