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

package com.liferay.geolocation.provider;

import com.liferay.geolocation.exception.GeolocationException;
import com.liferay.geolocation.model.GeolocationAddress;
import com.liferay.geolocation.model.GeolocationPosition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo García
 */
@ProviderType
public interface GeolocationProvider {

	/**
	 * Performs a reverse geocoding process, converting the geographic
	 * coordinates in the {@code GeolocationPosition} into a human-readable
	 * {@code GeolocationAddress}.
	 *
	 * @param  geolocationPosition the geographic coordinates
	 * @return the human-readable address
	 * @throws GeolocationException if a geolocation error occurs
	 * @review
	 */
	public GeolocationAddress getGeolocationAddress(
			GeolocationPosition geolocationPosition)
		throws GeolocationException;

	/**
	 * Performs a reverse IP geocoding process, converting the IP address into a
	 * human-readable {@code GeolocationAddress}.
	 *
	 * @param  ipAddress the IP address
	 * @return the human-readable address
	 * @throws GeolocationException if a geolocation error occurs
	 * @review
	 */
	public GeolocationAddress getGeolocationAddress(String ipAddress)
		throws GeolocationException;

	/**
	 * Performs a geocoding process, converting the human-readable {@code
	 * GeolocationAddress} into a {@code GeolocationPosition} with geographic
	 * coordinates.
	 *
	 * @param  geolocationAddress the human-readable address
	 * @return the geographic coordinates
	 * @throws GeolocationException if a geolocation error occurs
	 * @review
	 */
	public GeolocationPosition getGeolocationPosition(
			GeolocationAddress geolocationAddress)
		throws GeolocationException;

	/**
	 * Performs a IP geocoding process, converting the the IP address into a
	 * {@code GeolocationPosition} with geographic coordinates.
	 *
	 * @param  ipAddress the IP address
	 * @return the geographic coordinates
	 * @throws GeolocationException if a geolocation error occurs
	 * @review
	 */
	public GeolocationPosition getGeolocationPosition(String ipAddress)
		throws GeolocationException;

}