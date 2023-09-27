/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

import com.liferay.commerce.constants.CommercePriceConstants;

import java.util.Objects;

/**
 * @author Marco Leo
 */
public class PriceModel {

	public PriceModel(String price) {
		_price = price;
	}

	public String getDiscount() {
		return _discount;
	}

	public String getDiscountPercentage() {
		return _discountPercentage;
	}

	public String[] getDiscountPercentages() {
		return _discountPercentages;
	}

	public String getFinalPrice() {
		return _finalPrice;
	}

	public String getPrice() {
		return _price;
	}

	public String getPromoPrice() {
		return _promoPrice;
	}

	public boolean isPriceOnApplication() {
		boolean priceOnApplication = false;

		if ((_price != null) &&
			Objects.equals(
				_price,
				CommercePriceConstants.PRICE_VALUE_PRICE_ON_APPLICATION)) {

			priceOnApplication = true;
		}

		if (_promoPrice != null) {
			priceOnApplication = false;
		}

		return priceOnApplication;
	}

	public void setDiscount(String discount) {
		_discount = discount;
	}

	public void setDiscountPercentage(String discountPercentage) {
		_discountPercentage = discountPercentage;
	}

	public void setDiscountPercentages(String[] discountPercentages) {
		_discountPercentages = discountPercentages;
	}

	public void setFinalPrice(String finalPrice) {
		_finalPrice = finalPrice;
	}

	public void setPrice(String price) {
		_price = price;
	}

	public void setPromoPrice(String promoPrice) {
		_promoPrice = promoPrice;
	}

	private String _discount;
	private String _discountPercentage;
	private String[] _discountPercentages;
	private String _finalPrice;
	private String _price;
	private String _promoPrice;

}