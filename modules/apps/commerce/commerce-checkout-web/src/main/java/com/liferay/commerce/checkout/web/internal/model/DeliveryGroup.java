/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.web.internal.model;

import java.util.Date;

/**
 * @author Luca Pellizzon
 */
public class DeliveryGroup {

	public DeliveryGroup(
		long addressId, String addressSummary, Date deliveryDate, String name) {

		_addressId = addressId;
		_addressSummary = addressSummary;
		_deliveryDate = deliveryDate;
		_name = name;
	}

	public long getAddressId() {
		return _addressId;
	}

	public String getAddressSummary() {
		return _addressSummary;
	}

	public Date getDeliveryDate() {
		return _deliveryDate;
	}

	public String getName() {
		return _name;
	}

	private final long _addressId;
	private final String _addressSummary;
	private final Date _deliveryDate;
	private final String _name;

}