/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class ShippingFixedOptionSetting {

	public ShippingFixedOptionSetting(
		String country, String region, long shippingFixedOptionSettingId,
		String shippingMethod, String shippingOption, String warehouse,
		String zip) {

		_country = country;
		_region = region;
		_shippingFixedOptionSettingId = shippingFixedOptionSettingId;
		_shippingMethod = shippingMethod;
		_shippingOption = shippingOption;
		_warehouse = warehouse;
		_zip = zip;
	}

	public String getCountry() {
		return _country;
	}

	public String getRegion() {
		return _region;
	}

	public long getShippingFixedOptionSettingId() {
		return _shippingFixedOptionSettingId;
	}

	public String getShippingMethod() {
		return _shippingMethod;
	}

	public String getShippingOption() {
		return _shippingOption;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	public String getZip() {
		return _zip;
	}

	private final String _country;
	private final String _region;
	private final long _shippingFixedOptionSettingId;
	private final String _shippingMethod;
	private final String _shippingOption;
	private final String _warehouse;
	private final String _zip;

}