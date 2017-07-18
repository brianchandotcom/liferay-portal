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

package com.liferay.address.formatter;

import com.liferay.address.formatter.util.AddressFormatterUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = {"country=US"},
	service = AddressFormatter.class
)
public class USAddressFormatter implements AddressFormatter {

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

		if (Validator.isNotNull(AddressFormatterUtil.getRegion(address))) {
			sb.append(StringPool.COMMA);
			sb.append(StringPool.SPACE);
			sb.append(AddressFormatterUtil.getRegion(address));
		}

		if (Validator.isNotNull(address.getZip())) {
			sb.append(StringPool.SPACE);
			sb.append(address.getZip());
		}

		if (Validator.isNotNull(AddressFormatterUtil.getCountry(address))) {
			sb.append(StringPool.NEW_LINE);
			sb.append(AddressFormatterUtil.getCountry(address));
		}

		return sb.toString();
	}

}