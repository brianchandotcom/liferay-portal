/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.text.localizer.taglib.internal.address.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.text.localizer.address.AddressTextLocalizer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pei-Jung Lan
 */
public class AddressTextLocalizerUtil {

	public static String format(Address address) {
		AddressTextLocalizer addressTextLocalizer = getAddressTextLocalizer(
			address);

		return addressTextLocalizer.format(address);
	}

	public static AddressTextLocalizer getAddressTextLocalizer(
		Address address) {

		AddressTextLocalizer addressTextLocalizer = null;

		Country country = address.getCountry();

		if (country != null) {
			addressTextLocalizer = getAddressTextLocalizer(country.getA2());
		}

		if (addressTextLocalizer == null) {
			addressTextLocalizer = getAddressTextLocalizer("US");
		}

		return addressTextLocalizer;
	}

	public static AddressTextLocalizer getAddressTextLocalizer(
		String countryA2) {

		return _serviceTrackerMap.getService(countryA2);
	}

	private static final ServiceTrackerMap<String, AddressTextLocalizer>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AddressTextLocalizerUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AddressTextLocalizer.class, "country");
	}

}