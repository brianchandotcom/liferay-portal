/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

/**
 * @author Luca Pellizzon
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