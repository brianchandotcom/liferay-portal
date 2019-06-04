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

package com.liferay.geolocation.model;

/**
 * @author Eduardo García
 */
public interface GeolocationAddress {

	public String getCity();

	/**
	 * Returns the country ISO-3166 code.
	 *
	 * @return the country ISO-3166 code
	 */
	public String getCountryCode();

	/**
	 * Returns the region (subdivision) ISO-3166 code.
	 *
	 * @return the region (subdivision) ISO-3166 code
	 */
	public String getRegionCode();

	public String getStreet();

	public String getZip();

}