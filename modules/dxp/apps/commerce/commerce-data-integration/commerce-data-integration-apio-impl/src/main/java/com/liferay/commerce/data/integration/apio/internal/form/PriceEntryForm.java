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

package com.liferay.commerce.data.integration.apio.internal.form;

import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.PRICE;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.PROMO_PRICE;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.SKU_ID;

import com.liferay.apio.architect.form.Form;

/**
 * Instances of this class represent the values extracted from a price entry
 * form.
 *
 * @author Zoltán Takács
 * @review
 */
public class PriceEntryForm {

	/**
	 * Builds a {@code Form} that generates {@code PriceEntryForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product form
	 * @review
	 */
	public static Form<PriceEntryForm> buildForm(
		Form.Builder<PriceEntryForm> formBuilder) {

		return formBuilder.title(
			__ -> "The price entry form"
		).description(
			__ -> "This form can be used to create or update a price entry"
		).constructor(
			PriceEntryForm::new
		).addRequiredDouble(
			PRICE, PriceEntryForm::_setPrice
		).addRequiredDouble(
			PROMO_PRICE, PriceEntryForm::_setPromoPrice
		).addRequiredLong(
			SKU_ID, PriceEntryForm::_setSkuID
		).build();
	}

	public Double getPrice() {
		return _price;
	}

	public Double getPromoPrice() {
		return _promoPrice;
	}

	public Long getSkuID() {
		return _skuID;
	}

	private void _setPrice(Double price) {
		_price = price;
	}

	private void _setPromoPrice(Double promoPrice) {
		_promoPrice = promoPrice;
	}

	private void _setSkuID(Long skuID) {
		_skuID = skuID;
	}

	private Double _price;
	private Double _promoPrice;
	private Long _skuID;

}