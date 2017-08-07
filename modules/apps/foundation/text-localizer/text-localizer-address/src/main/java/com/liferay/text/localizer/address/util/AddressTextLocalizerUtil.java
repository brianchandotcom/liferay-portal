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

package com.liferay.text.localizer.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
public class AddressTextLocalizerUtil {

	public static Optional<String> getCountryNameOptional(Address address) {
		Optional<Address> addressOptional = Optional.ofNullable(address);

		return addressOptional.map(
			Address::getCountry
		).filter(
			country -> country.getCountryId() > 0
		).map(
			country -> {
				Optional<ServiceContext> serviceContextOptional =
					Optional.ofNullable(
						ServiceContextThreadLocal.getServiceContext());

				return serviceContextOptional.map(
					serviceContext ->
						country.getName(serviceContext.getLocale())
				).orElseGet(
					country::getName
				);
			}
		).filter(
			Validator::isNotNull
		);
	}

	public static Optional<String> getRegionNameOptional(Address address) {
		Optional<Address> addressOptional = Optional.ofNullable(address);

		return addressOptional.map(
			Address::getRegion
		).filter(
			region -> region.getRegionId() > 0
		).map(
			Region::getName
		).filter(
			Validator::isNotNull
		);
	}

	/**
	* Check if address is written in Roman characters.
	*
	* @param address the address
	* @return <code>true</code> if the address in written in Roman characters;
	* <code>false</code> otherwise.
	*/
	public static boolean isRomanizedAddress(Address address) {
		String addressString = _getAddressString(address);

		if (Validator.isNull(addressString)) {
			return false;
		}

		Matcher matcher = _romanizedAddressPattern.matcher(addressString);

		return matcher.matches();
	}

	private static String _getAddressString(Address address) {
		StringBundler sb = new StringBundler(7);

		if (Validator.isNotNull(address.getStreet1())) {
			sb.append(address.getStreet1());
		}

		if (Validator.isNotNull(address.getStreet2())) {
			sb.append(StringPool.SPACE);
			sb.append(address.getStreet2());
		}

		if (Validator.isNotNull(address.getStreet3())) {
			sb.append(StringPool.SPACE);
			sb.append(address.getStreet3());
		}

		if (Validator.isNotNull(address.getCity())) {
			sb.append(StringPool.SPACE);
			sb.append(address.getCity());
		}

		return sb.toString();
	}

	private static final Pattern _romanizedAddressPattern = Pattern.compile(
		"[\\w\\s\\-\\,\\.]+");

}