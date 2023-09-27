/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TaxRateSetting {

	public TaxRateSetting(
		String country, String rate, String region, String taxRate,
		long taxRateSettingId, String zip) {

		_country = country;
		_rate = rate;
		_region = region;
		_taxRate = taxRate;
		_taxRateSettingId = taxRateSettingId;
		_zip = zip;
	}

	public String getCountry() {
		return _country;
	}

	public String getRate() {
		return _rate;
	}

	public String getRegion() {
		return _region;
	}

	public String getTaxRate() {
		return _taxRate;
	}

	public long getTaxRateSettingId() {
		return _taxRateSettingId;
	}

	public String getZip() {
		return _zip;
	}

	private final String _country;
	private final String _rate;
	private final String _region;
	private final String _taxRate;
	private final long _taxRateSettingId;
	private final String _zip;

}