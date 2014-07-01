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

package com.liferay.ip.geocoder.internal;

import com.liferay.ip.geocoder.model.IPInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 */
public class IPGeocoderServiceImpl {

	public static IPInfo getIPInfo(String ipAddress) throws PortalException {
		if (_lookupService == null) {
			_init();
		}

		Location location = _lookupService.getLocation(ipAddress);

		return new IPInfo(ipAddress, location);
	}

	private static void _init() {
		try {
			if (_lookupService == null) {
				_lookupService = new LookupService(
					GEO_DATA_LOCATION, LookupService.GEOIP_MEMORY_CACHE);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
	}

	private static String GEO_DATA_LOCATION =
		"/usr/local/share/GeoIP/GeoIPCity.dat";

	private static Log _log = LogFactoryUtil.getLog(IPGeocoderServiceImpl.class);

	private static LookupService _lookupService;

}