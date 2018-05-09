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

import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.CURRENCY;
import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.DISPLAY_DATE;
import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.EXPIRATION_DATE;
import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.NAME;
import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.NEVER_EXPIRE;
import static com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants.PRIORITY;

import com.liferay.apio.architect.form.Form;

import java.util.Date;

/**
 * Instances of this class represent the values extracted from a price list
 * form.
 *
 * @author Zoltán Takács
 * @review
 */
public class PriceListForm {

	/**
	 * Builds a {@code Form} that generates {@code PriceListForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product form
	 * @review
	 */
	public static Form<PriceListForm> buildForm(
		Form.Builder<PriceListForm> formBuilder) {

		return formBuilder.title(
			__ -> "The price list form"
		).description(
			__ -> "This form can be used to create or update a price list"
		).constructor(
			PriceListForm::new
		).addOptionalDate(
			DISPLAY_DATE, PriceListForm::_setDisplayDate
		).addOptionalDate(
			EXPIRATION_DATE, PriceListForm::_setExpirationDate
		).addRequiredBoolean(
			NEVER_EXPIRE, PriceListForm::_setNeverExpire
		).addRequiredDouble(
			PRIORITY, PriceListForm::_setPriority
		).addRequiredString(
			CURRENCY, PriceListForm::setCurrency
		).addRequiredString(
			NAME, PriceListForm::_setName
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