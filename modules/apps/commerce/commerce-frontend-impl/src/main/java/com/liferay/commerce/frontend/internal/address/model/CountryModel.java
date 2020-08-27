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

package com.liferay.commerce.frontend.internal.address.model;

/**
 * @author Marco Leo
 */
public class CountryModel {

	public CountryModel(
		long id, String name, boolean billingAllowed, boolean shippingAllowed) {

		_id = id;
		_name = name;
		_billingAllowed = billingAllowed;
		_shippingAllowed = shippingAllowed;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public boolean isBillingAllowed() {
		return _billingAllowed;
	}

	public boolean isShippingAllowed() {
		return _shippingAllowed;
	}

	private final boolean _billingAllowed;
	private final long _id;
	private final String _name;
	private final boolean _shippingAllowed;

}