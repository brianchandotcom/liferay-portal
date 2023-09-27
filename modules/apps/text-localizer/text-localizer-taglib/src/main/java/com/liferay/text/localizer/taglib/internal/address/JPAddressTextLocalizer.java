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
 * @author Yasuyuki Takeo
 */
@Component(property = "country=JP", service = AddressTextLocalizer.class)
public class JPAddressTextLocalizer implements AddressTextLocalizer {

	@Override
	public String format(Address address) {
		StringBundler sb = new StringBundler(13);

		String countryName = AddressUtil.getCountryName(address);

		if (countryName != null) {
			sb.append(html.escape(countryName));
			sb.append(StringPool.NEW_LINE);
		}

		Address escapedAddress = address.toEscapedModel();

		String city = escapedAddress.getCity();

		boolean hasCity = Validator.isNotNull(city);

		String zip = escapedAddress.getZip();

		boolean hasZip = Validator.isNotNull(zip);

		if (hasZip) {
			sb.append(zip);
		}

		String regionName = AddressUtil.getRegionName(address);

		if (regionName != null) {
			if (hasZip) {
				sb.append(StringPool.SPACE);
			}

			sb.append(html.escape(regionName));
		}

		if (hasCity) {
			sb.append(StringPool.SPACE);
			sb.append(city);
		}

		String street1 = escapedAddress.getStreet1();

		if (Validator.isNotNull(street1)) {
			if (hasZip || hasCity) {
				sb.append(StringPool.SPACE);
			}

			sb.append(street1);
		}

		String street2 = escapedAddress.getStreet2();

		if (Validator.isNotNull(street2)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(street2);
		}

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