/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class InstancePriceEntry {

	public InstancePriceEntry(
		long priceEntryId, String createDateString, String name,
		boolean priceOnApplication, String quantity, String unitOfMeasure,
		String unitPrice) {

		_priceEntryId = priceEntryId;
		_createDateString = createDateString;
		_name = name;
		_priceOnApplication = priceOnApplication;
		_quantity = quantity;
		_unitOfMeasure = unitOfMeasure;
		_unitPrice = unitPrice;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public String getName() {
		return _name;
	}

	public long getPriceEntryId() {
		return _priceEntryId;
	}

	public String getQuantity() {
		return _quantity;
	}

	public String getUnitOfMeasure() {
		return _unitOfMeasure;
	}

	public String getUnitPrice() {
		return _unitPrice;
	}

	public boolean isPriceOnApplication() {
		return _priceOnApplication;
	}

	private final String _createDateString;
	private final String _name;
	private final long _priceEntryId;
	private final boolean _priceOnApplication;
	private final String _quantity;
	private final String _unitOfMeasure;
	private final String _unitPrice;

}