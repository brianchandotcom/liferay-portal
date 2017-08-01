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

		if (Validator.isNotNull(address.getStreet1())) {
			sb.append(address.getStreet1());
		}

		if (Validator.isNotNull(address.getStreet2())) {
			sb.append(StringPool.NEW_LINE);
			sb.append(address.getStreet2());
		}

		if (Validator.isNotNull(address.getStreet3())) {
			sb.append(StringPool.NEW_LINE);
			sb.append(address.getStreet3());
		}

		sb.append(StringPool.NEW_LINE);

		if (Validator.isNotNull(address.getCity())) {
			sb.append(address.getCity());
		}

		if (Validator.isNotNull(
				AddressTextLocalizerUtil.getRegionName(address))) {

			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(AddressTextLocalizerUtil.getRegionName(address));
		}

		if (Validator.isNotNull(address.getZip())) {
			sb.append(StringPool.SPACE);
			sb.append(address.getZip());
		}

		if (Validator.isNotNull(
				AddressTextLocalizerUtil.getCountryName(address))) {

			sb.append(StringPool.NEW_LINE);
			sb.append(AddressTextLocalizerUtil.getCountryName(address));
		}

		return sb.toString();
	}

}