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

import com.liferay.apio.architect.form.Form;

/**
 * Instances of this class represent the values extracted from a price entry
 * upserter form.
 *
 * @author Zoltán Takács
 * @review
 */
public class PriceEntryUpserterForm {

	/**
	 * Builds a {@code Form} that generates {@code PriceEntryUpserterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product upserter form
	 * @review
	 */
	public static Form<PriceEntryUpserterForm> buildForm(
		Form.Builder<PriceEntryUpserterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The price entry upserter form"
		).description(
			__ -> "This form can be used to upsert a price entry"
		).constructor(
			PriceEntryUpserterForm::new
		).addOptionalLong(
			"commercePriceEntryId",
			PriceEntryUpserterForm::_setCommercePriceEntryId
		).addOptionalLong(
			"skuId", PriceEntryUpserterForm::_setSkuId
		).addOptionalString(
			"externalReferenceCode",
			PriceEntryUpserterForm::_setExternalReferenceCode
		).addOptionalString(
			"skuExternalReferenceCode",
			PriceEntryUpserterForm::_setSkuExternalReferenceCode
		).addRequiredDouble(
			"price", PriceEntryUpserterForm::_setPrice
		).addRequiredDouble(
			"promoPrice", PriceEntryUpserterForm::_setPromoPrice
		).build();
	}

	public Long getCommercePriceEntryId() {
		return _commercePriceEntryId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public Double getPrice() {
		return _price;
	}

	public Double getPromoPrice() {
		return _promoPrice;
	}

	public String getSkuExternalReferenceCode() {
		return _skuExternalReferenceCode;
	}

	public Long getSkuId() {
		return _skuId;
	}

	private void _setCommercePriceEntryId(Long commercePriceEntryId) {
		_commercePriceEntryId = commercePriceEntryId;
	}

	private void _setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	private void _setPrice(Double price) {
		_price = price;
	}

	private void _setPromoPrice(Double promoPrice) {
		_promoPrice = promoPrice;
	}

	private void _setSkuExternalReferenceCode(String skuExternalReferenceCode) {
		_skuExternalReferenceCode = skuExternalReferenceCode;
	}

	private void _setSkuId(Long skuId) {
		_skuId = skuId;
	}

	private Long _commercePriceEntryId = 0L;
	private String _externalReferenceCode;
	private Double _price;
	private Double _promoPrice;
	private String _skuExternalReferenceCode;
	private Long _skuId = 0L;

}