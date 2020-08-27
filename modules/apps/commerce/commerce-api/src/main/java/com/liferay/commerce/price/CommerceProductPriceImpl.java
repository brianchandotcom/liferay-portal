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

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceProductPriceImpl implements CommerceProductPrice {

	@Override
	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	@Override
	public CommerceDiscountValue getDiscountValue() {
		return _commerceDiscountValue;
	}

	@Override
	public CommerceDiscountValue getDiscountValueWithTaxAmount() {
		return _commerceDiscountValueWithTaxAmount;
	}

	@Override
	public CommerceMoney getFinalPrice() {
		return _finalPrice;
	}

	@Override
	public CommerceMoney getFinalPriceWithTaxAmount() {
		return _finalPriceWithTaxAmount;
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
	public CommerceMoney getUnitPriceWithTaxAmount() {
		return _unitPriceWithTaxAmount;
	}

	@Override
	public CommerceMoney getUnitPromoPrice() {
		return _unitPromoPrice;
	}

	@Override
	public CommerceMoney getUnitPromoPriceWithTaxAmount() {
		return _unitPromoPriceWithTaxAmount;
	}

	public void setCommerceDiscountValue(
		CommerceDiscountValue commerceDiscountValue) {

		_commerceDiscountValue = commerceDiscountValue;
	}

	public void setCommerceDiscountValueWithTaxAmount(
		CommerceDiscountValue commerceDiscountValueWithTaxAmount) {

		_commerceDiscountValueWithTaxAmount =
			commerceDiscountValueWithTaxAmount;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
	}

	public void setFinalPrice(CommerceMoney finalPrice) {
		_finalPrice = finalPrice;
	}

	public void setFinalPriceWithTaxAmount(
		CommerceMoney finalPriceWithTaxAmount) {

		_finalPriceWithTaxAmount = finalPriceWithTaxAmount;
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

	public void setUnitPriceWithTaxAmount(
		CommerceMoney unitPriceWithTaxAmount) {

		_unitPriceWithTaxAmount = unitPriceWithTaxAmount;
	}

	public void setUnitPromoPrice(CommerceMoney unitPromoPrice) {
		_unitPromoPrice = unitPromoPrice;
	}

	public void setUnitPromoPriceWithTaxAmount(
		CommerceMoney unitPromoPriceWithTaxAmount) {

		_unitPromoPriceWithTaxAmount = unitPromoPriceWithTaxAmount;
	}

	private CommerceDiscountValue _commerceDiscountValue;
	private CommerceDiscountValue _commerceDiscountValueWithTaxAmount;
	private long _commercePriceListId;
	private CommerceMoney _finalPrice;
	private CommerceMoney _finalPriceWithTaxAmount;
	private int _quantity;
	private BigDecimal _taxValue;
	private CommerceMoney _unitPrice;
	private CommerceMoney _unitPriceWithTaxAmount;
	private CommerceMoney _unitPromoPrice;
	private CommerceMoney _unitPromoPriceWithTaxAmount;

}