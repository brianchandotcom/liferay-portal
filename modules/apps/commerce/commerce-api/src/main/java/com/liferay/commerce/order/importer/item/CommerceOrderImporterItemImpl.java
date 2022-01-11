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

package com.liferay.commerce.order.importer.item;

import com.liferay.commerce.price.CommerceOrderItemPrice;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderImporterItemImpl
	implements CommerceOrderImporterItem {

	@Override
	public CommerceOrderItemPrice getCommerceOrderItemPrice() {
		return _commerceOrderItemPrice;
	}

	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	@Override
	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	@Override
	public String[] getErrorMessages() {
		return _errorMessages;
	}

	@Override
	public String getJSON() {
		return _json;
	}

	@Override
	public String getName(Locale locale) {
		if (_nameMap == null) {
			return null;
		}

		return _nameMap.get(locale);
	}

	@Override
	public long getParentCommerceOrderItemCPDefinitionId() {
		return _parentCommerceOrderItemCPDefinitionId;
	}

	@Override
	public int getQuantity() {
		return _quantity;
	}

	@Override
	public String getReplacingSKU() {
		return _replacingSKU;
	}

	@Override
	public String getSKU() {
		return _sku;
	}

	@Override
	public boolean hasParentCommerceOrderItem() {
		if (getParentCommerceOrderItemCPDefinitionId() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isValid() {
		String[] errorMessages = getErrorMessages();

		if ((errorMessages == null) || (errorMessages.length == 0)) {
			return true;
		}

		return false;
	}

	public void setCommerceOrderItemPrice(
		CommerceOrderItemPrice commerceOrderItemPrice) {

		_commerceOrderItemPrice = commerceOrderItemPrice;
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setJSON(String json) {
		_json = json;
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		_nameMap = nameMap;
	}

	public void setParentCommerceOrderItemCPDefinitionId(
		long parentCommerceOrderItemCPDefinitionId) {

		_parentCommerceOrderItemCPDefinitionId =
			parentCommerceOrderItemCPDefinitionId;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setReplacingSKU(String replacingSKU) {
		_replacingSKU = replacingSKU;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	private CommerceOrderItemPrice _commerceOrderItemPrice;
	private long _cpDefinitionId;
	private long _cpInstanceId;
	private String[] _errorMessages;
	private String _json;
	private Map<Locale, String> _nameMap;
	private long _parentCommerceOrderItemCPDefinitionId;
	private int _quantity;
	private String _replacingSKU;
	private String _sku;

}