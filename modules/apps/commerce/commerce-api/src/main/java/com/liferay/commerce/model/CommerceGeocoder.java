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