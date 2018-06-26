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
import com.liferay.apio.architect.form.Form.Builder;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CommerceAccountUpserterForm {

	public static Form<CommerceAccountUpserterForm> buildForm(
		Builder<CommerceAccountUpserterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The account creator form"
		).description(
			__ -> "This form can be used to create an account"
		).constructor(
			CommerceAccountUpserterForm::new
		).addRequiredString(
			"externalReferenceCode",
			CommerceAccountUpserterForm::_setExternalReferenceCode
		).addOptionalLong(
			"countryId", CommerceAccountUpserterForm::_setCountryId
		).addOptionalLong(
			"regionId", CommerceAccountUpserterForm::_setRegionId
		).addRequiredString(
			"name", CommerceAccountUpserterForm::_setName
		).addOptionalLongList(
			"commerceUserIds", CommerceAccountUpserterForm::_setCommerceUserIds
		).build();
	}

	public List<Long> getCommerceUserIds() {
		return _commerceUserIds;
	}

	public long getCountryId() {
		return _countryId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getName() {
		return _name;
	}

	public long getRegionId() {
		return _regionId;
	}

	private void _setCommerceUserIds(List<Long> commerceUserIds) {
		_commerceUserIds = commerceUserIds;
	}

	private void _setCountryId(long countryId) {
		_countryId = countryId;
	}

	private void _setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setRegionId(long regionId) {
		_regionId = regionId;
	}

	private List<Long> _commerceUserIds;
	private long _countryId;
	private String _externalReferenceCode;
	private String _name;
	private long _regionId;

}