/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.model;

import com.liferay.commerce.frontend.model.RestrictionField;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class PaymentRestriction {

	public PaymentRestriction(
		long countryId, String countryName,
		List<RestrictionField> restrictionFields) {

		_countryId = countryId;
		_countryName = countryName;
		_restrictionFields = restrictionFields;
	}

	public long getCountryId() {
		return _countryId;
	}

	public String getCountryName() {
		return _countryName;
	}

	public List<RestrictionField> getRestrictionFields() {
		return _restrictionFields;
	}

	private final long _countryId;
	private final String _countryName;
	private final List<RestrictionField> _restrictionFields;

}