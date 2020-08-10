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