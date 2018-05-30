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
 * Instances of this class represent the values extracted from a tier price
 * entry form.
 *
 * @author Zoltán Takács
 * @review
 */
public class TierPriceEntryUpserterForm {

	/**
	 * Builds a {@code Form} that generates {@code TierPriceEntryUpserterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a tier price entry upserter form
	 * @review
	 */
	public static Form<TierPriceEntryUpserterForm> buildForm(
		Form.Builder<TierPriceEntryUpserterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The tier price entry upserter form"
		).description(
			__ -> "This form can be used to upsert a tier price entry"
		).constructor(
			TierPriceEntryUpserterForm::new
		).addOptionalLong(
			"commerceTierPriceEntryId",
			TierPriceEntryUpserterForm::_setCommerceTierPriceEntryId
		).addOptionalString(
			"externalReferenceCode",
			TierPriceEntryUpserterForm::_setExternalReferenceCode
		).addOptionalString(
			"priceEntryExternalReferenceCode",
			TierPriceEntryUpserterForm::_setPriceEntryExternalReferenceCode
		).addRequiredLong(
			"minQuantity", TierPriceEntryUpserterForm::_setMinQuantity
		).addRequiredDouble(
			"price", TierPriceEntryUpserterForm::_setPrice
		).addRequiredDouble(
			"promoPrice", TierPriceEntryUpserterForm::_setPromoPrice
		).build();
	}

	public Long getCommerceTierPriceEntryId() {
		return _commerceTierPriceEntryId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public Long getMinQuantity() {
		return _minQuantity;
	}

	public Double getPrice() {
		return _price;
	}

	public String getPriceEntryExternalReferenceCode() {
		return _priceEntryExternalReferenceCode;
	}

	public Double getPromoPrice() {
		return _promoPrice;
	}

	private void _setCommerceTierPriceEntryId(Long commerceTierPriceEntryId) {
		_commerceTierPriceEntryId = commerceTierPriceEntryId;
	}

	private void _setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	private void _setMinQuantity(Long minQuantity) {
		_minQuantity = minQuantity;
	}

	private void _setPrice(Double price) {
		_price = price;
	}

	private void _setPriceEntryExternalReferenceCode(
		String priceEntryExternalReferenceCode) {

		_priceEntryExternalReferenceCode = priceEntryExternalReferenceCode;
	}

	private void _setPromoPrice(Double promoPrice) {
		_promoPrice = promoPrice;
	}

	private Long _commerceTierPriceEntryId = 0L;
	private String _externalReferenceCode;
	private Long _minQuantity;
	private Double _price;
	private String _priceEntryExternalReferenceCode;
	private Double _promoPrice;

}