/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public BigDecimal getQuantity() {
		return _quantity;
	}

	@Override
	public BigDecimal getTaxValue() {
		return _taxValue;
	}

	@Override
	public BigDecimal getUnitOfMeasureIncrementalOrderQuantity() {
		return _unitOfMeasureIncrementalOrderQuantity;
	}

	@Override
	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
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

	public boolean isPriceOnApplication() {
		return _priceOnApplication;
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

	public void setPriceOnApplication(boolean priceOnApplication) {
		_priceOnApplication = priceOnApplication;
	}

	public void setQuantity(BigDecimal quantity) {
		_quantity = quantity;
	}

	public void setTaxValue(BigDecimal taxValue) {
		_taxValue = taxValue;
	}

	public void setUnitOfMeasureIncrementalOrderQuantity(
		BigDecimal unitOfMeasureIncrementalOrderQuantity) {

		_unitOfMeasureIncrementalOrderQuantity =
			unitOfMeasureIncrementalOrderQuantity;
	}

	public void setUnitOfMeasureKey(String unitOfMeasureKey) {
		_unitOfMeasureKey = unitOfMeasureKey;
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
	private boolean _priceOnApplication;
	private BigDecimal _quantity;
	private BigDecimal _taxValue;
	private BigDecimal _unitOfMeasureIncrementalOrderQuantity;
	private String _unitOfMeasureKey;
	private CommerceMoney _unitPrice;
	private CommerceMoney _unitPriceWithTaxAmount;
	private CommerceMoney _unitPromoPrice;
	private CommerceMoney _unitPromoPriceWithTaxAmount;

}