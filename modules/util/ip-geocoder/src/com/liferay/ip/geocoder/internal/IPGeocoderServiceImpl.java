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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import org.tukaani.xz.XZInputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
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

	protected String getIPGeocoderFile(
			String path, String url, boolean forceDownload)
		throws IOException {

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		File file = new File(tmpDir + path);

		if (!file.exists() || forceDownload) {
			synchronized (this) {
				if (_log.isInfoEnabled()) {
					_log.info("Downloading Geolocation Data from " + url);
				}

				byte[] bytes = HttpUtil.URLtoByteArray(url);

				File tarFile = new File(tmpDir + GEO_DATA_TAR_FILE_NAME);

				FileUtil.write(tarFile, bytes);

				FileUtil.write(
					file, new XZInputStream(new FileInputStream(tarFile)));
			}
		}

		return FileUtil.getAbsolutePath(file);
	}

	private void _init() {
		try {
			if (_lookupService == null) {
				String ipGeocoderFile = getIPGeocoderFile(
					GEO_DATA_FILE_NAME, GEO_DATA_URL, false);

				_lookupService = new LookupService(
					ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		IPGeocoderServiceImpl.class);

	private static String GEO_DATA_FILE_NAME ="/liferay/GeoIP/GeoIPCity.dat";

	private static String GEO_DATA_TAR_FILE_NAME =
		"/liferay/GeoIP/GeoIPCity.dat.xz";

	private static String GEO_DATA_URL =
		"http://cdn.files.liferay.com/mirrors/" +
			"geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.xz";

	private static LookupService _lookupService;

}