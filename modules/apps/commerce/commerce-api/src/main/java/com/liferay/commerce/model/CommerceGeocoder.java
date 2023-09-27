/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.commerce.exception.CommerceGeocoderException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 */
public interface CommerceGeocoder {

	public default double[] getCoordinates(
			long groupId, String street, String city, String zip,
			String regionCode, String countryA2)
		throws CommerceGeocoderException {

		return new double[] {0, 0};
	}

	public double[] getCoordinates(
			String street, String city, String zip, Region region,
			Country country)
		throws CommerceGeocoderException;

}