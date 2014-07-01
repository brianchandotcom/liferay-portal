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
import com.liferay.portal.kernel.util.Validator;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import org.tukaani.xz.XZInputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@Component(
	name = IPGeocoderServiceImpl.SERVICE_NAME,
	service = IPGeocoderService.class,
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"ip.geocoderservice.file.location=",
		"ip.geocoderservice.file.url=http://cdn.files.liferay.com/mirrors/" +
			"geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.xz"
	})
public class IPGeocoderServiceImpl implements IPGeocoderService {

	public static final String SERVICE_NAME = "IPGeocoderService";

	@Activate
	public void activate(final Map<String, String> properties) {
		_init(properties);
	}

	@Deactivate
	public void deactivate(final Map<String, String> properties) {
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

	@Modified
	public void modified(final Map<String, String> properties) {
		if (properties.containsKey("ip.geocoderservice.file.location") ||
			properties.containsKey("ip.geocoderservice.file.url")) {

			_lookupService = null;

			_init(properties);
		}
	}

	protected String getIPGeocoderFile(
			String path, String url, boolean forceDownload)
		throws IOException {

		File file = new File(path);

		if (!file.exists() || forceDownload) {
			synchronized (this) {
				if (_log.isInfoEnabled()) {
					_log.info("Downloading Geolocation Data from " + url);
				}

				byte[] bytes = HttpUtil.URLtoByteArray(url);

				String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

				File tarFile = new File(tmpDir + GEO_DATA_TAR_FILE_NAME);

				FileUtil.write(tarFile, bytes);

				FileUtil.write(
					file, new XZInputStream(new FileInputStream(tarFile)));
			}
		}

		return FileUtil.getAbsolutePath(file);
	}

	private void _init(Map<String, String> properties) {
		if (_lookupService != null) {
			return;
		}

		String fileLocation = properties.get(
			"ip.geocoderservice.file.location");

		if (Validator.isNull(fileLocation)) {
			fileLocation =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/liferay/GeoIP/GeoIPCity.dat";
		}

		String fileURL = properties.get("ip.geocoderservice.file.url");

		try {
			String ipGeocoderFile = getIPGeocoderFile(
				fileLocation, fileURL, false);

			_lookupService = new LookupService(
				ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		IPGeocoderServiceImpl.class);

	private static String GEO_DATA_TAR_FILE_NAME =
		"/liferay/GeoIP/GeoIPCity.dat.xz";

	private static LookupService _lookupService;

}