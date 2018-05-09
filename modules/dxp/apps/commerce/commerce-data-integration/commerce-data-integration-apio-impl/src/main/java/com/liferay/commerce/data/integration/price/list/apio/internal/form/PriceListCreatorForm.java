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

package com.liferay.commerce.data.integration.price.list.apio.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Date;

/**
 * Instances of this class represent the values extracted from a price list
 * form.
 *
 * @author Zoltán Takács
 * @review
 */
public class PriceListCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code PriceListCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product creator form
	 * @review
	 */
	public static Form<PriceListCreatorForm> buildForm(
		Form.Builder<PriceListCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The price list creator form"
		).description(
			__ -> "This form can be used to create a price list"
		).constructor(
			PriceListCreatorForm::new
		).addOptionalDate(
			"displayDate", PriceListCreatorForm::_setDisplayDate
		).addOptionalDate(
			"expirationDate", PriceListCreatorForm::_setExpirationDate
		).addRequiredBoolean(
			"neverExpire", PriceListCreatorForm::_setNeverExpire
		).addRequiredDouble(
			"priority", PriceListCreatorForm::_setPriority
		).addRequiredString(
			"currency", PriceListCreatorForm::setCurrency
		).addRequiredString(
			"name", PriceListCreatorForm::_setName
		).build();
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

	public String getName() {
		return _name;
	}

	public Double getPriority() {
		return _priority;
	}

	public Boolean isNeverExpire() {
		return _neverExpire;
	}

	public void setCurrency(String currency) {
		_currency = currency;
	}

	private void _setDisplayDate(Date display) {
		_displayDate = display;
	}

	private void _setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
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

	private String _currency;
	private Date _displayDate;
	private Date _expirationDate;
	private String _name;
	private Boolean _neverExpire;
	private Double _priority;
	private String _title;

}