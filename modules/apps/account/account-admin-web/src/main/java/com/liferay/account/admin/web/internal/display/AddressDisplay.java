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

package com.liferay.account.admin.web.internal.display;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;

/**
 * @author Pei-Jung Lan
 */
public class AddressDisplay {

	public static AddressDisplay of(Address address) {
		if (address != null) {
			return new AddressDisplay(address);
		}

		return _EMPTY_INSTANCE;
	}

	public static AddressDisplay of(long addressId) {
		return of(AddressLocalServiceUtil.fetchAddress(addressId));
	}

	public long getAddressId() {
		return _addressId;
	}

	public String getCity() {
		return _city;
	}

	public String getName() {
		return _name;
	}

	public String getRegionName() {
		return _region.getName();
	}

	public String getStreet() {
		return _street;
	}

	public String getType() {
		return _type;
	}

	public String getZip() {
		return _zip;
	}

	private AddressDisplay() {
		_addressId = 0;
		_city = StringPool.BLANK;
		_name = StringPool.BLANK;
		_region = null;
		_street = StringPool.BLANK;
		_type = StringPool.BLANK;
		_zip = StringPool.BLANK;
	}

	private AddressDisplay(Address address) {
		_addressId = address.getAddressId();
		_city = address.getCity();
		_name = address.getName();
		_region = address.getRegion();
		_street = address.getStreet1();
		_type = _getType(address);
		_zip = address.getZip();
	}

	private String _getType(Address address) {
		ListType listType = address.getType();

		return listType.getName();
	}

	private static final AddressDisplay _EMPTY_INSTANCE = new AddressDisplay();

	private final long _addressId;
	private final String _city;
	private final String _name;
	private final Region _region;
	private final String _street;
	private final String _type;
	private final String _zip;

}