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

package com.liferay.text.localizer.taglib.internal.address;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.text.localizer.address.AddressTextLocalizer;
import com.liferay.text.localizer.taglib.internal.address.util.AddressUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.framework.BundleContext;

/**
 * @author Yasuyuki Takeo
*/
@Component(
	immediate = true, property = "country=JP",
	service = AddressTextLocalizer.class
)
public class JPAddressTextLocalizer implements AddressTextLocalizer {

	@Override
	public String format(Address address) {
		StringBundler sb = new StringBundler(13);

		Optional<String> countryNameOptional =
			AddressUtil.getCountryNameOptional(address);

		// Country
		countryNameOptional.ifPresent(
			countryName -> {
				sb.append(html.escape(countryName));
				sb.append(StringPool.NEW_LINE);
			});

		Address escapedAddress = address.toEscapedModel();

		String city = escapedAddress.getCity();

		boolean hasCity = Validator.isNotNull(city);

		Optional<String> regionNameOptional = AddressUtil.getRegionNameOptional(
			address);

		boolean hasRegionName = regionNameOptional.isPresent();

		String zip = escapedAddress.getZip();

		boolean hasZip = Validator.isNotNull(zip);

		// Zip code
		if (hasZip) {
			sb.append(zip);
		}

		// Prefecture
		regionNameOptional.ifPresent(
			regionName -> {
				if (hasZip) {
					sb.append(StringPool.SPACE);
				}
				sb.append(html.escape(regionName));
			}
		);

		// City
		if (hasCity) {
			sb.append(StringPool.SPACE);
			sb.append(city);
		}

		// Residence
		String street1 = escapedAddress.getStreet1();

		if (Validator.isNotNull(street1)) {
			if (hasZip || hasCity) {
				sb.append(StringPool.SPACE);
			}
			sb.append(street1);
		}

		// Apartment name, etc
		String street2 = escapedAddress.getStreet2();

		if (Validator.isNotNull(street2)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street2);
		}

		// Apartment room number, etc
		String street3 = escapedAddress.getStreet3();

		if (Validator.isNotNull(street3)) {
			sb.append(StringPool.SPACE);
			sb.append(street3);
		}

		String s = sb.toString();

		return s.trim();
	}

	@Reference
	protected Html html;

}