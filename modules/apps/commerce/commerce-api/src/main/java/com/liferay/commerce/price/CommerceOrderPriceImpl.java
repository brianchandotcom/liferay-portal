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

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderPriceImpl implements CommerceOrderPrice {

	@Override
	public CommerceDiscountValue getShippingDiscountValue() {
		return _shippingDiscountValue;
	}

	@Override
	public CommerceDiscountValue getShippingDiscountValueWithTaxAmount() {
		return _shippingDiscountValueWithTaxAmount;
	}

	@Override
	public CommerceMoney getShippingValue() {
		return _shippingValue;
	}

	@Override
	public CommerceMoney getShippingValueWithTaxAmount() {
		return _shippingValueWithTaxAmount;
	}

	@Override
	public CommerceMoney getSubtotal() {
		return _subtotal;
	}

	@Override
	public CommerceDiscountValue getSubtotalDiscountValue() {
		return _subtotalDiscountValue;
	}

	@Override
	public CommerceDiscountValue getSubtotalDiscountValueWithTaxAmount() {
		return _subtotalDiscountValueWithTaxAmount;
	}

	@Override
	public CommerceMoney getSubtotalWithTaxAmount() {
		return _subtotalWithTaxAmount;
	}

	@Override
	public CommerceMoney getTaxValue() {
		return _taxValue;
	}

	@Override
	public CommerceMoney getTotal() {
		return _total;
	}

	@Override
	public CommerceDiscountValue getTotalDiscountValue() {
		return _totalDiscountValue;
	}

	@Override
	public CommerceDiscountValue getTotalDiscountValueWithTaxAmount() {
		return _totalDiscountValueWithTaxAmount;
	}

	@Override
	public CommerceMoney getTotalWithTaxAmount() {
		return _totalWithTaxAmount;
	}

	public void setShippingDiscountValue(
		CommerceDiscountValue shippingDiscountValue) {

		_shippingDiscountValue = shippingDiscountValue;
	}

	public void setShippingDiscountValueWithTaxAmount(
		CommerceDiscountValue shippingDiscountValueWithTaxAmount) {

		_shippingDiscountValueWithTaxAmount =
			shippingDiscountValueWithTaxAmount;
	}

	public void setShippingValue(CommerceMoney shippingValue) {
		_shippingValue = shippingValue;
	}

	public void setShippingValueWithTaxAmount(
		CommerceMoney shippingValueWithTaxAmount) {

		_shippingValueWithTaxAmount = shippingValueWithTaxAmount;
	}

	public void setSubtotal(CommerceMoney subtotal) {
		_subtotal = subtotal;
	}

	public void setSubtotalDiscountValue(
		CommerceDiscountValue subtotalDiscountValue) {

		_subtotalDiscountValue = subtotalDiscountValue;
	}

	public void setSubtotalDiscountValueWithTaxAmount(
		CommerceDiscountValue subtotalDiscountValueWithTaxAmount) {

		_subtotalDiscountValueWithTaxAmount =
			subtotalDiscountValueWithTaxAmount;
	}

	public void setSubtotalWithTaxAmount(CommerceMoney subtotalWithTaxAmount) {
		_subtotalWithTaxAmount = subtotalWithTaxAmount;
	}

	public void setTaxValue(CommerceMoney taxValue) {
		_taxValue = taxValue;
	}

	public void setTotal(CommerceMoney total) {
		_total = total;
	}

	public void setTotalDiscountValue(
		CommerceDiscountValue totalDiscountValue) {

		_totalDiscountValue = totalDiscountValue;
	}

	public void setTotalDiscountValueWithTaxAmount(
		CommerceDiscountValue totalDiscountValueWithTaxAmount) {

		_totalDiscountValueWithTaxAmount = totalDiscountValueWithTaxAmount;
	}

	public void setTotalWithTaxAmount(CommerceMoney totalWithTaxAmount) {
		_totalWithTaxAmount = totalWithTaxAmount;
	}

	private CommerceDiscountValue _shippingDiscountValue;
	private CommerceDiscountValue _shippingDiscountValueWithTaxAmount;
	private CommerceMoney _shippingValue;
	private CommerceMoney _shippingValueWithTaxAmount;
	private CommerceMoney _subtotal;
	private CommerceDiscountValue _subtotalDiscountValue;
	private CommerceDiscountValue _subtotalDiscountValueWithTaxAmount;
	private CommerceMoney _subtotalWithTaxAmount;
	private CommerceMoney _taxValue;
	private CommerceMoney _total;
	private CommerceDiscountValue _totalDiscountValue;
	private CommerceDiscountValue _totalDiscountValueWithTaxAmount;
	private CommerceMoney _totalWithTaxAmount;

}