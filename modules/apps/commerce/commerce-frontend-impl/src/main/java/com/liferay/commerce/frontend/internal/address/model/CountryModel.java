/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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