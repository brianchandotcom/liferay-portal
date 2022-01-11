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

package com.liferay.commerce.order.content.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class PreviewOrderItem {

	public PreviewOrderItem(
		String externalReferenceCode, String importStatus, String options,
		String productName, int quantity, String replacingSKU, int rowNumber,
		String sku, String totalPrice, String unitPrice) {

		_externalReferenceCode = externalReferenceCode;
		_importStatus = importStatus;
		_options = options;
		_productName = productName;
		_quantity = quantity;
		_replacingSKU = replacingSKU;
		_rowNumber = rowNumber;
		_sku = sku;
		_totalPrice = totalPrice;
		_unitPrice = unitPrice;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getImportStatus() {
		return _importStatus;
	}

	public String getOptions() {
		return _options;
	}

	public String getProductName() {
		return _productName;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getReplacingSKU() {
		return _replacingSKU;
	}

	public int getRowNumber() {
		return _rowNumber;
	}

	public String getSKU() {
		return _sku;
	}

	public String getTotalPrice() {
		return _totalPrice;
	}

	public String getUnitPrice() {
		return _unitPrice;
	}

	private final String _externalReferenceCode;
	private final String _importStatus;
	private final String _options;
	private final String _productName;
	private final int _quantity;
	private final String _replacingSKU;
	private final int _rowNumber;
	private final String _sku;
	private final String _totalPrice;
	private final String _unitPrice;

}