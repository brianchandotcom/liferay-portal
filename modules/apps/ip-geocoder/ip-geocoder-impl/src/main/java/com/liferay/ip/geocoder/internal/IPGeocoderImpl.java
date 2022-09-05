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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
	configurationPid = "com.liferay.ip.geocoder.internal.IPGeocoderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, name = "IPGeocoder",
	service = IPGeocoder.class
)
public class IPGeocoderImpl implements IPGeocoder {

	@Override
	public IPInfo getIPInfo(String ipAddress) {
		LookupService lookupService = _getLookupService();

		Location location = lookupService.getLocation(ipAddress);

		if (location == null) {
			return new IPInfo(
				null, null, null, ipAddress, 0, 0, null, null, null);
		}

		return new IPInfo(
			location.city, location.countryCode, location.countryName,
			ipAddress, location.latitude, location.longitude,
			location.postalCode, location.region,
			com.maxmind.geoip.regionName.regionNameByCode(
				location.countryCode, location.region));
	}

	@Modified
	public void modified(Map<String, String> properties) {
		_lookupService = null;
		_properties = properties;
	}

	@Activate
	protected void activate(Map<String, String> properties) {
		_properties = properties;
	}

	@Deactivate
	protected void deactivate(Map<String, String> properties) {
		_lookupService = null;
		_properties = null;
	}

	private File _getIPGeocoderFile(
			String filePath, String fileURL, boolean forceDownload)
		throws IOException {

		File file = new File(filePath);

		if (file.exists() && !forceDownload) {
			return file;
		}

		synchronized (this) {
			if (_log.isInfoEnabled()) {
				_log.info("Downloading " + fileURL);
			}

			URL url = new URL(fileURL);

			URLConnection urlConnection = url.openConnection();

			File xzFile = new File(
				System.getProperty("java.io.tmpdir") +
					"/liferay/geoip/GeoIPCity.dat.xz");

			_write(xzFile, urlConnection.getInputStream());

			_write(file, new XZInputStream(new FileInputStream(xzFile)));
		}

		return file;
	}

	private LookupService _getLookupService() {
		LookupService lookupService = _lookupService;

		if (lookupService != null) {
			return lookupService;
		}

		IPGeocoderConfiguration igGeocoderConfiguration =
			ConfigurableUtil.createConfigurable(
				IPGeocoderConfiguration.class, _properties);

		String filePath = igGeocoderConfiguration.filePath();

		if ((filePath == null) || filePath.equals("")) {
			filePath =
				System.getProperty("java.io.tmpdir") +
					"/liferay/geoip/GeoIPCity.dat";
		}

		if (_log.isInfoEnabled()) {
			_log.info("File " + filePath);
		}

		try {
			File ipGeocoderFile = _getIPGeocoderFile(
				filePath, igGeocoderConfiguration.fileURL(), false);

			lookupService = new LookupService(
				ipGeocoderFile, LookupService.GEOIP_MEMORY_CACHE);

			_lookupService = lookupService;

			return lookupService;
		}
		catch (IOException ioException) {
			_log.error("Unable to activate IP Geocoder", ioException);

			throw new RuntimeException(
				"Unable to activate IP Geocoder", ioException);
		}
	}

	private void _write(File file, InputStream inputStream) throws IOException {
		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		catch (SecurityException securityException) {

			// We may have the permission to write a specific file without
			// having the permission to check if the parent file exists

		}

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
			inputStream);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
			new FileOutputStream(file));

		int i = 0;

		while ((i = bufferedInputStream.read()) != -1) {
			bufferedOutputStream.write(i);
		}

		bufferedOutputStream.flush();

		bufferedInputStream.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(IPGeocoderImpl.class);

	private volatile LookupService _lookupService;
	private volatile Map<String, String> _properties;

}