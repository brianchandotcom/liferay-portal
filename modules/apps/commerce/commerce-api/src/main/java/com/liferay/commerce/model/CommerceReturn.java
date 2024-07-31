/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Stefano Motta
 */
public class CommerceReturn {

	public CommerceReturn(ObjectEntry objectEntry) {
		Map<String, Serializable> objectEntryValues = objectEntry.getValues();

		_objectEntry = objectEntry;

		_accountId = (long)objectEntryValues.get(
			"r_accountToCommerceReturns_accountEntryId");
		_channelGroupId = (long)objectEntryValues.get("channelGroupId");
		_channelId = (long)objectEntryValues.get("channelId");
		_channelName = (String)objectEntryValues.get("channelName");
		_createDate = objectEntry.getCreateDate();
		_currencyCode = (String)objectEntryValues.get("currencyCode");
		_currencySymbol = (String)objectEntryValues.get("currencySymbol");
		_externalReferenceCode = objectEntry.getExternalReferenceCode();
		_id = objectEntry.getPrimaryKey();
		_note = (String)objectEntryValues.get("note");
		_orderId = (long)objectEntryValues.get(
			"r_commerceOrderToCommerceReturns_commerceOrderId");
		_requestedItems = GetterUtil.getInteger(
			String.valueOf(objectEntryValues.get("requestedItems")));
		_returnStatus = (String)objectEntryValues.get("returnStatus");
		_status = objectEntry.getStatus();
		_totalAmount = new BigDecimal(
			GetterUtil.getDouble(objectEntryValues.get("totalAmount")));
	}

	public long getAccountId() {
		return _accountId;
	}

	public long getChannelGroupId() {
		return _channelGroupId;
	}

	public long getChannelId() {
		return _channelId;
	}

	public String getChannelName() {
		return _channelName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public String getCurrencySymbol() {
		return _currencySymbol;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getId() {
		return _id;
	}

	public String getNote() {
		return _note;
	}

	public ObjectEntry getObjectEntry() {
		return _objectEntry;
	}

	public long getOrderId() {
		return _orderId;
	}

	public int getRequestedItems() {
		return _requestedItems;
	}

	public String getReturnStatus() {
		return _returnStatus;
	}

	public int getStatus() {
		return _status;
	}

	public BigDecimal getTotalAmount() {
		return _totalAmount;
	}

	private final long _accountId;
	private final long _channelGroupId;
	private final long _channelId;
	private final String _channelName;
	private final Date _createDate;
	private final String _currencyCode;
	private final String _currencySymbol;
	private final String _externalReferenceCode;
	private final long _id;
	private final String _note;
	private final ObjectEntry _objectEntry;
	private final long _orderId;
	private final int _requestedItems;
	private final String _returnStatus;
	private final int _status;
	private final BigDecimal _totalAmount;

}