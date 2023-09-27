/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class PreviewOrderItem {

	public PreviewOrderItem(
		String externalReferenceCode, String importStatus, String options,
		String productName, BigDecimal quantity, String replacingSKU,
		String requestedDeliveryDateString, int rowNumber, String sku,
		String totalPrice, String unitOfMeasureKey, String unitPrice) {

		_externalReferenceCode = externalReferenceCode;
		_importStatus = importStatus;
		_options = options;
		_productName = productName;
		_quantity = quantity;
		_replacingSKU = replacingSKU;
		_requestedDeliveryDateString = requestedDeliveryDateString;
		_rowNumber = rowNumber;
		_sku = sku;
		_totalPrice = totalPrice;
		_unitOfMeasureKey = unitOfMeasureKey;
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

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public String getReplacingSKU() {
		return _replacingSKU;
	}

	public String getRequestedDeliveryDateString() {
		return _requestedDeliveryDateString;
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

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	public String getUnitPrice() {
		return _unitPrice;
	}

	private final String _externalReferenceCode;
	private final String _importStatus;
	private final String _options;
	private final String _productName;
	private final BigDecimal _quantity;
	private final String _replacingSKU;
	private final String _requestedDeliveryDateString;
	private final int _rowNumber;
	private final String _sku;
	private final String _totalPrice;
	private final String _unitOfMeasureKey;
	private final String _unitPrice;

}