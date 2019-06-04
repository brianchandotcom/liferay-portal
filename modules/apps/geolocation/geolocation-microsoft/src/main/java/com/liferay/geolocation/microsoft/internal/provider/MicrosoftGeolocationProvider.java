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

package com.liferay.geolocation.microsoft.internal.provider;

import com.liferay.geolocation.microsoft.internal.model.MicrosoftGeolocationAddress;
import com.liferay.geolocation.microsoft.internal.model.MicrosoftGeolocationPosition;
import com.liferay.geolocation.model.GeolocationAddress;
import com.liferay.geolocation.model.GeolocationPosition;
import com.liferay.geolocation.provider.GeolocationProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = GeolocationProvider.class)
public class MicrosoftGeolocationProvider implements GeolocationProvider {

	@Override
	public GeolocationAddress getGeolocationAddress(
		GeolocationPosition geolocationPosition) {

		return new MicrosoftGeolocationAddress();
	}

	@Override
	public GeolocationAddress getGeolocationAddress(String ipAddress) {
		return new MicrosoftGeolocationAddress();
	}

	@Override
	public GeolocationPosition getGeolocationPosition(
		GeolocationAddress geolocationAddress) {

		return new MicrosoftGeolocationPosition();
	}

	@Override
	public GeolocationPosition getGeolocationPosition(String ipAddress) {
		return new MicrosoftGeolocationPosition();
	}

}