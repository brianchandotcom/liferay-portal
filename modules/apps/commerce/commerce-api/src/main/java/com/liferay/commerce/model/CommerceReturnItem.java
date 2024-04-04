/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.object.model.ObjectEntry;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceReturnItem {

	public CommerceReturnItem(ObjectEntry objectEntry) {
		Map<String, Serializable> objectEntryValues = objectEntry.getValues();

		_objectEntry = objectEntry;

		_amount = new BigDecimal(
			String.valueOf(objectEntryValues.get("amount")));
		_commerceOrderItemId = (long)objectEntryValues.get(
			"r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId");
		_createDate = objectEntry.getCreateDate();
		_externalReferenceCode = objectEntry.getExternalReferenceCode();
		_id = objectEntry.getPrimaryKey();
		_quantity = new BigDecimal(
			String.valueOf(objectEntryValues.get("quantity")));
		_returnReason = (String)objectEntryValues.get("returnReason");
		_status = objectEntry.getStatus();
	}

	public BigDecimal getAmount() {
		return _amount;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getId() {
		return _id;
	}

	public ObjectEntry getObjectEntry() {
		return _objectEntry;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public String getReturnReason() {
		return _returnReason;
	}

	public int getStatus() {
		return _status;
	}

	private final BigDecimal _amount;
	private final long _commerceOrderItemId;
	private final Date _createDate;
	private final String _externalReferenceCode;
	private final long _id;
	private final ObjectEntry _objectEntry;
	private final BigDecimal _quantity;
	private final String _returnReason;
	private final int _status;

}