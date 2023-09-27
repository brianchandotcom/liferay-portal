/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TaxRate {

	public TaxRate(String name, String rate, long taxRateId) {
		_name = name;
		_rate = rate;
		_taxRateId = taxRateId;
	}

	public String getName() {
		return _name;
	}

	public String getRate() {
		return _rate;
	}

	public long getTaxRateId() {
		return _taxRateId;
	}

	private final String _name;
	private final String _rate;
	private final long _taxRateId;

}