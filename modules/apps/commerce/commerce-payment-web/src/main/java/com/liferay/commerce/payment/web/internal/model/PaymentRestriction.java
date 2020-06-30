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

package com.liferay.commerce.payment.web.internal.model;

import com.liferay.commerce.frontend.model.RestrictionField;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class PaymentRestriction {

	public PaymentRestriction(
		long commerceCountryId, String country, List<RestrictionField> fields) {

		_commerceCountryId = commerceCountryId;
		_country = country;
		_fields = fields;
	}

	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	public String getCountry() {
		return _country;
	}

	public List<RestrictionField> getFields() {
		return _fields;
	}

	private final long _commerceCountryId;
	private final String _country;
	private final List<RestrictionField> _fields;

}