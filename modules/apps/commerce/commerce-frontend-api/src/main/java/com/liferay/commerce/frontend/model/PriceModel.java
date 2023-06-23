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

package com.liferay.commerce.frontend.model;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class PriceModel {

	public PriceModel(CommerceMoney priceCommerceMoney, String price)
		throws PortalException {

		_priceCommerceMoney = priceCommerceMoney;
		_price = price;
	}

	public String getDiscount() {
		return _discount;
	}

	public CommerceMoney getDiscountCommerceMoney() {
		return _discountCommerceMoney;
	}

	public String getDiscountPercentage() {
		return _discountPercentage;
	}

	public String[] getDiscountPercentages() {
		return _discountPercentages;
	}

	public CommerceMoney getFinalCommerceMoney() {
		return _finalCommerceMoney;
	}

	public String getFinalPrice() {
		return _finalPrice;
	}

	public String getPrice() {
		return _price;
	}

	public CommerceMoney getPriceCommerceMoney() {
		return _priceCommerceMoney;
	}

	public CommerceMoney getPromoCommerceMoney() {
		return _promoCommerceMoney;
	}

	public String getPromoPrice() {
		return _promoPrice;
	}

	public boolean isPriceOnApplication() {
		boolean priceOnApplication = false;

		if ((_priceCommerceMoney != null) &&
			_priceCommerceMoney.isPriceOnApplication()) {

			priceOnApplication = true;
		}

		if (_promoCommerceMoney != null) {
			priceOnApplication = false;
		}

		return priceOnApplication;
	}

	public void setDiscount(
		CommerceMoney discountCommerceMoney, String discount) {

		_discountCommerceMoney = discountCommerceMoney;
		_discount = discount;
	}

	public void setDiscountPercentage(String discountPercentage) {
		_discountPercentage = discountPercentage;
	}

	public void setDiscountPercentages(String[] discountPercentages) {
		_discountPercentages = discountPercentages;
	}

	public void setFinalPrice(
			CommerceMoney finalCommerceMoney, String finalPrice)
		throws PortalException {

		_finalCommerceMoney = finalCommerceMoney;
		_finalPrice = finalPrice;
	}

	public void setPrice(CommerceMoney priceCommerceMoney, String price)
		throws PortalException {

		_priceCommerceMoney = priceCommerceMoney;
		_price = price;
	}

	public void setPromoPrice(
			CommerceMoney promoCommerceMoney, String promoPrice)
		throws PortalException {

		_promoCommerceMoney = promoCommerceMoney;
		_promoPrice = promoPrice;
	}

	private String _discount;
	private CommerceMoney _discountCommerceMoney;
	private String _discountPercentage;
	private String[] _discountPercentages;
	private CommerceMoney _finalCommerceMoney;
	private String _finalPrice;
	private String _price;
	private CommerceMoney _priceCommerceMoney;
	private CommerceMoney _promoCommerceMoney;
	private String _promoPrice;

}