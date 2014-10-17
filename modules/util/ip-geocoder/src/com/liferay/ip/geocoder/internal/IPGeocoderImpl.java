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

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

import org.apache.log4j.Logger;

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
	configurationPolicy = ConfigurationPolicy.OPTIONAL, name = "IPGeocoder",
	property = {
		"ip.geocoder.file.path=",
		"ip.geocoder.file.url=http://cdn.mirrors.liferay.com" +
			"/geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.xz"
	},
	service = IPGeocoder.class)
public class IPGeocoderImpl implements IPGeocoder {

	@Activate
	public void activate(Map<String, String> properties) {
		if (_lookupService != null) {
			return;
		}

		String filePath = properties.get("ip.geocoder.file.path");

		if ((filePath == null) || filePath.equals("")) {
			filePath =
				System.getProperty("java.io.tmpdir") +
					"/liferay/geoip/GeoIPCity.dat";
		}

		String fileURL = properties.get("ip.geocoder.file.url");

		try {
			File ipGeocoderFile = getIPGeocoderFile(filePath, fileURL, false);

			_lookupService = new LookupService(
				ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);
		}
		catch (IOException ioe) {
			_logger.error("Unable to activate Liferay IP Geocoder", ioe);
		}
	}

	@Deactivate
	public void deactivate(Map<String, String> properties) {
		_lookupService = null;
	}

	@Override
	public IPInfo getIPInfo(String ipAddress) {
		if (_lookupService == null) {
			return null;
		}

		Location location = _lookupService.getLocation(ipAddress);

		return new IPInfo(ipAddress, location);
	}

	@Modified
	public void modified(Map<String, String> properties) {
		if (properties.containsKey("ip.geocoder.file.path") ||
			properties.containsKey("ip.geocoder.file.url")) {

			_lookupService = null;

			activate(properties);
		}
	}

	protected File getIPGeocoderFile(
			String filePath, String fileURL, boolean forceDownload)
		throws IOException {

		File file = new File(filePath);

		if (file.exists() && !forceDownload) {
			return file;
		}

		synchronized (this) {
			if (_logger.isInfoEnabled()) {
				_logger.info("Downloading " + fileURL);
			}

			URL url = new URL(fileURL);

			URLConnection urlConnection = url.openConnection();

			File xzFile = new File(
				System.getProperty("java.io.tmpdir") +
					"/liferay/geoip/GeoIPCity.dat.xz");

			write(xzFile, urlConnection.getInputStream());

			write(file, new XZInputStream(new FileInputStream(xzFile)));
		}

		return file;
	}

	protected void write(File file, InputStream inputStream)
		throws IOException {

		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		catch (SecurityException se) {

			// We may have the permission to write a specific file without
			// having the permission to check if the parent file exists

		}

		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream)) {

			BufferedOutputStream bufferedOutputStream =
				new BufferedOutputStream(new FileOutputStream(file));

			int i = 0;

			while ((i = bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(i);
			}

			bufferedOutputStream.flush();
		}
	}

	private static final Logger _logger = Logger.getLogger(
		IPGeocoderImpl.class);

	private static LookupService _lookupService;

}