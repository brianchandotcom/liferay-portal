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
	name = IPGeocoderImpl.SERVICE_NAME,
	service = IPGeocoder.class,
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"ip.geocoderservice.file.location=",
		"ip.geocoderservice.file.url=http://cdn.files.liferay.com/mirrors/" +
			"geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.xz"
	})
public class IPGeocoderImpl implements IPGeocoder {

	public static final String SERVICE_NAME = "IPGeocoder";

	@Activate
	public void activate(final Map<String, String> properties) {
		_init(properties);
	}

	@Deactivate
	public void deactivate(final Map<String, String> properties) {
		_lookupService = null;
	}

	@Override
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
			String path, String fileUrl, boolean forceDownload)
		throws IOException {

		File file = new File(path);

		if (!file.exists() || forceDownload) {
			synchronized (this) {
				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Downloading Geolocation Data from " + fileUrl);
				}

				URL url = new URL(fileUrl);

				URLConnection con = url.openConnection();

				String tmpDir = System.getProperty(TMP_DIR);

				File tarFile = new File(tmpDir + GEO_DATA_TAR_FILE_NAME);

				write(tarFile, con.getInputStream());

				write(file, new XZInputStream(new FileInputStream(tarFile)));
			}
		}

		String absolutePath = file.getAbsolutePath();

		return absolutePath.replace('\\', '/');
	}

	private void _init(Map<String, String> properties) {
		if (_lookupService != null) {
			return;
		}

		String fileLocation = properties.get(
			"ip.geocoderservice.file.location");

		if ((fileLocation == null) || (fileLocation.equals(""))) {
			fileLocation =
				System.getProperty(TMP_DIR) + "/liferay/GeoIP/GeoIPCity.dat";
		}

		String fileURL = properties.get("ip.geocoderservice.file.url");

		try {
			String ipGeocoderFile = getIPGeocoderFile(
				fileLocation, fileURL, false);

			_lookupService = new LookupService(
				ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);
		}
		catch (IOException ioe) {
			_logger.error(ioe.getMessage());
		}
	}

	private void mkdirsParentFile(File file) {
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
	}

	private void write(File file, InputStream is) throws IOException {
		mkdirsParentFile(file);

		BufferedInputStream bis = new BufferedInputStream(is);

		BufferedOutputStream bos = new BufferedOutputStream(
			new FileOutputStream(file));

		int i;

		while ((i = bis.read()) != -1) {
			bos.write(i);
		}

		bos.flush();
		bis.close();
	}

	private static final String TMP_DIR = "java.io.tmpdir";

	private static Logger _logger = Logger.getLogger(IPGeocoderImpl.class);

	private static String GEO_DATA_TAR_FILE_NAME =
		"/liferay/GeoIP/GeoIPCity.dat.xz";

	private static LookupService _lookupService;

}