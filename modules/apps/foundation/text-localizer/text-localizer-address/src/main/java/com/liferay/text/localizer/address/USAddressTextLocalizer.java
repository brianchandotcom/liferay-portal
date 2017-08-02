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

package com.liferay.text.localizer.address;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.text.localizer.address.util.AddressTextLocalizerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = {"country=US"},
	service = AddressTextLocalizer.class
)
public class USAddressTextLocalizer implements AddressTextLocalizer {

	public String format(Address address) {
		StringBundler sb = new StringBundler(14);

		String street1 = address.getStreet1();
		String street2 = address.getStreet2();
		String street3 = address.getStreet3();
		String city = address.getCity();
		String regionName = AddressTextLocalizerUtil.getRegionName(address);
		String zip = address.getZip();
		String countryName = AddressTextLocalizerUtil.getCountryName(address);

		if (Validator.isNotNull(street1)) {
			sb.append(street1);
		}

		if (Validator.isNotNull(street2)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street2);
		}

		if (Validator.isNotNull(street3)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street3);
		}

		boolean hasCity = Validator.isNotNull(city);
		boolean hasRegionName = Validator.isNotNull(regionName);
		boolean hasZip = Validator.isNotNull(zip);

		if (hasCity || hasRegionName || hasZip) {
			sb.append(StringPool.NEW_LINE);
		}

		if (hasCity) {
			sb.append(city);
		}

		if (hasRegionName) {
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(regionName);
		}

		if (hasZip) {
			sb.append(StringPool.SPACE);
			sb.append(zip);
		}

		if (Validator.isNotNull(countryName)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(countryName);
		}

		return sb.toString();
	}

}