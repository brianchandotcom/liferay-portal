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