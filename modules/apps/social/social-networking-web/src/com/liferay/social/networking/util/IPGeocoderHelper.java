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

package com.liferay.social.networking.util;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

/**
 * @author Adolfo Pérez
 */
public class IPGeocoderHelper {

	public IPGeocoderHelper(IPGeocoder ipGeocoder) {
		_ipGeocoder = ipGeocoder;
	}

	public IPInfo getIPInfo(String ipAddress) {
		if (!isIPGeocoderInstalled()) {
			throw new IllegalStateException("IPGeocoder not installed");
		}

		return _ipGeocoder.getIPInfo(ipAddress);
	}

	public boolean isIPGeocoderConfigured(String ipAddress) {
		return getIPInfo(ipAddress) != null;
	}

	public boolean isIPGeocoderInstalled() {
		if ((_ipGeocoder != null) &&
			MessageBusUtil.hasMessageListener(DestinationNames.IP_GEOCODER)) {

			return true;
		}

		return false;
	}

	private final IPGeocoder _ipGeocoder;

}