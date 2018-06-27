/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.internal.price;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.price.CommerceProductPrice;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceProductPriceImpl implements CommerceProductPrice {

	@Override
	public CommerceDiscountValue getDiscountValue() {
		return _commerceDiscountValue;
	}

	@Override
	public CommerceMoney getFinalPrice() {
		return _finalPrice;
	}

	@Override
	public int getQuantity() {
		return _quantity;
	}

	@Override
	public BigDecimal getTaxValue() {
		return _taxValue;
	}

	@Override
	public CommerceMoney getUnitPrice() {
		return _unitPrice;
	}

	@Override
	public CommerceMoney getUnitPromoPrice() {
		return _unitPromoPrice;
	}

	public void setCommerceDiscountValue(
		CommerceDiscountValue commerceDiscountValue) {

		_commerceDiscountValue = commerceDiscountValue;
	}

	public void setFinalPrice(CommerceMoney finalPrice) {
		_finalPrice = finalPrice;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setTaxValue(BigDecimal taxValue) {
		_taxValue = taxValue;
	}

	public void setUnitPrice(CommerceMoney unitPrice) {
		_unitPrice = unitPrice;
	}

	public void setUnitPromoPrice(CommerceMoney unitPromoPrice) {
		_unitPromoPrice = unitPromoPrice;
	}

	private CommerceDiscountValue _commerceDiscountValue;
	private CommerceMoney _finalPrice;
	private int _quantity;
	private BigDecimal _taxValue;
	private CommerceMoney _unitPrice;
	private CommerceMoney _unitPromoPrice;

}