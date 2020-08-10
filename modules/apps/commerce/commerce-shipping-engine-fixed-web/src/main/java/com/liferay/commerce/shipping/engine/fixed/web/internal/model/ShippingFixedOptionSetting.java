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