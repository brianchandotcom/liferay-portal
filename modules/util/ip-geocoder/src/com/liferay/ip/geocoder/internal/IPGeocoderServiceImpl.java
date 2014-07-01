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
import com.liferay.ip.geocoder.service.IPGeocoderService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = IPGeocoderService.class)
public class IPGeocoderServiceImpl implements IPGeocoderService {

	@Activate
	public void activate() {
		_init();
	}

	@Deactivate
	public void deactivate() {
		_lookupService = null;
	}


	public IPInfo getIPInfo(String ipAddress) {
		if (_lookupService != null) {
			Location location = _lookupService.getLocation(ipAddress);

			return new IPInfo(ipAddress, location);
		}
		else {
			return null;
		}
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