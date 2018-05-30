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

import java.util.Date;

/**
 * Instances of this class represent the values extracted from a price list
 * form.
 *
 * @author Zoltán Takács
 * @review
 */
public class PriceListUpserterForm {

	/**
	 * Builds a {@code Form} that generates {@code PriceListUpserterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product upserter form
	 * @review
	 */
	public static Form<PriceListUpserterForm> buildForm(
		Form.Builder<PriceListUpserterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The price list upserter form"
		).description(
			__ -> "This form can be used to upsert a price list"
		).constructor(
			PriceListUpserterForm::new
		).addOptionalDate(
			"displayDate", PriceListUpserterForm::_setDisplayDate
		).addOptionalDate(
			"expirationDate", PriceListUpserterForm::_setExpirationDate
		).addOptionalLong(
			"commercePriceListId",
			PriceListUpserterForm::_setCommercePriceListId
		).addOptionalString(
			"externalReferenceCode",
			PriceListUpserterForm::_setExternalReferenceCode
		).addRequiredBoolean(
			"neverExpire", PriceListUpserterForm::_setNeverExpire
		).addRequiredDouble(
			"priority", PriceListUpserterForm::_setPriority
		).addRequiredString(
			"currency", PriceListUpserterForm::_setCurrency
		).addRequiredString(
			"name", PriceListUpserterForm::_setName
		).build();
	}

	public Long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public String getCurrency() {
		return _currency;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getName() {
		return _name;
	}

	public Double getPriority() {
		return _priority;
	}

	public Boolean isNeverExpire() {
		return _neverExpire;
	}

	private void _setCommercePriceListId(Long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
	}

	private void _setCurrency(String currency) {
		_currency = currency;
	}

	private void _setDisplayDate(Date display) {
		_displayDate = display;
	}

	private void _setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	private void _setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setNeverExpire(Boolean neverExpire) {
		_neverExpire = neverExpire;
	}

	private void _setPriority(Double priority) {
		_priority = priority;
	}

	private Long _commercePriceListId = 0L;
	private String _currency;
	private Date _displayDate;
	private Date _expirationDate;
	private String _externalReferenceCode;
	private String _name;
	private Boolean _neverExpire;
	private Double _priority;

}