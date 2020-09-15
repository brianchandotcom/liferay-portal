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

package com.liferay.commerce.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.option.CommerceOptionValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Riccardo Alberti
 */
public class CommerceProductPriceRequest {

	public CommerceContext getCommerceContext() {
		return _commerceContext;
	}

	public List<CommerceOptionValue> getCommerceOptionValues() {
		return _commerceOptionValues;
	}

	public long getCpInstanceId() {
		return _cpInstanceId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public boolean isCalculateTax() {
		return _calculateTax;
	}

	public boolean isSecure() {
		return _secure;
	}

	public void setCalculateTax(boolean calculateTax) {
		_calculateTax = calculateTax;
	}

	public void setCommerceContext(CommerceContext commerceContext) {
		_commerceContext = commerceContext;
	}

	public void setCommerceOptionValues(
		List<CommerceOptionValue> commerceOptionValues) {

		Objects.requireNonNull(commerceOptionValues);

		_commerceOptionValues = new ArrayList<>(commerceOptionValues);
	}

	public void setCpInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setSecure(boolean secure) {
		_secure = secure;
	}

	private boolean _calculateTax;
	private CommerceContext _commerceContext;
	private List<CommerceOptionValue> _commerceOptionValues =
		Collections.emptyList();
	private long _cpInstanceId;
	private int _quantity;
	private boolean _secure;

}