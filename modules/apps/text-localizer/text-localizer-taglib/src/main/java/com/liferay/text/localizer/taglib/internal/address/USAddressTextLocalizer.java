/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.text.localizer.taglib.internal.address;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.text.localizer.address.AddressTextLocalizer;
import com.liferay.text.localizer.taglib.internal.address.util.AddressUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(property = "country=US", service = AddressTextLocalizer.class)
public class USAddressTextLocalizer implements AddressTextLocalizer {

	@Override
	public String format(Address address) {
		StringBundler sb = new StringBundler(13);

		Address escapedAddress = address.toEscapedModel();

		String street1 = escapedAddress.getStreet1();

		if (Validator.isNotNull(street1)) {
			sb.append(street1);
		}

		String street2 = escapedAddress.getStreet2();

		if (Validator.isNotNull(street2)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street2);
		}

		String street3 = escapedAddress.getStreet3();

		if (Validator.isNotNull(street3)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street3);
		}

		String city = escapedAddress.getCity();

		boolean hasCity = Validator.isNotNull(city);

		String regionName = AddressUtil.getRegionName(address);

		boolean hasRegionName = false;

		if (regionName != null) {
			hasRegionName = true;
		}

		String zip = escapedAddress.getZip();

		boolean hasZip = Validator.isNotNull(zip);

		if (hasCity || hasRegionName || hasZip) {
			sb.append(StringPool.NEW_LINE);
		}

		if (hasCity) {
			sb.append(city);

			if (hasRegionName || hasZip) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		if (hasRegionName) {
			sb.append(html.escape(regionName));
		}

		if (hasZip) {
			if (hasRegionName) {
				sb.append(StringPool.SPACE);
			}

			sb.append(zip);
		}

		String countryName = AddressUtil.getCountryName(address);

		if (countryName != null) {
			sb.append(StringPool.NEW_LINE);
			sb.append(html.escape(countryName));
		}

		String s = sb.toString();

		return s.trim();
	}

	@Reference
	protected Html html;

}