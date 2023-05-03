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
import com.liferay.ip.geocoder.internal.configuration.IPGeocoderConfiguration;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import java.io.File;
import java.io.IOException;

import java.net.InetAddress;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.ip.geocoder.internal.configuration.IPGeocoderConfiguration",
	name = "IPGeocoder", service = IPGeocoder.class
)
public class IPGeocoderImpl implements IPGeocoder {

	@Override
	public IPInfo getIPInfo(HttpServletRequest httpServletRequest) {
		String ipAddress = _getIPAddress(httpServletRequest);

		return new IPInfo(_getCountryCode(ipAddress), ipAddress);
	}

	@Activate
	protected void activate(Map<String, String> properties) {
		_properties = properties;
	}

	private DatabaseReader _createDatabaseReader() {
		try {
			return new DatabaseReader.Builder(
				_getFile()
			).withCache(
				new CHMCache()
			).build();
		}
		catch (IOException ioException) {
			_log.error("Unable to activate IP Geocoder", ioException);

			throw new RuntimeException(
				"Unable to activate IP Geocoder", ioException);
		}
	}

	private String _getCountryCode(String ipAddress) {
		try {
			InetAddress inetAddress = InetAddress.getByName(ipAddress);

			if (inetAddress.isAnyLocalAddress() ||
				inetAddress.isLoopbackAddress()) {

				return null;
			}

			DatabaseReader databaseReader =
				_databaseReaderDCLSingleton.getSingleton(
					this::_createDatabaseReader);

			CountryResponse countryResponse = databaseReader.country(
				inetAddress);

			if (countryResponse == null) {
				return null;
			}

			Country country = countryResponse.getCountry();

			return country.getIsoCode();
		}
		catch (AddressNotFoundException addressNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(addressNotFoundException);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return null;
	}

	private File _getFile() throws IOException {
		IPGeocoderConfiguration ipGeocoderConfiguration =
			ConfigurableUtil.createConfigurable(
				IPGeocoderConfiguration.class, _properties);

		if (Validator.isNotNull(ipGeocoderConfiguration.filePath())) {
			File file = new File(ipGeocoderConfiguration.filePath());

			if (file.exists()) {
				if (_log.isInfoEnabled()) {
					_log.info("Use file " + ipGeocoderConfiguration.filePath());
				}

				return file;
			}
		}

		Class<?> clazz = getClass();

		File file = FileUtil.createTempFile(
			clazz.getResourceAsStream("/com.maxmind.geolite2.country.mmdb"));

		if (_log.isInfoEnabled()) {
			_log.info("Use temp file " + file);
		}

		return file;
	}

	private String _getIPAddress(HttpServletRequest httpServletRequest) {

		// diplomatie.gouv.fr resolves to 77.158.88.130
		// gov.uk resolves to 151.101.192.144
		// state.gov resolves to 34.233.79.178

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String mockIPGeocoderRemoteAddr =
			originalHttpServletRequest.getParameter("mockIPGeocoderRemoteAddr");

		if (mockIPGeocoderRemoteAddr != null) {
			return mockIPGeocoderRemoteAddr;
		}

		return httpServletRequest.getRemoteAddr();
	}

	private static final Log _log = LogFactoryUtil.getLog(IPGeocoderImpl.class);

	private final DCLSingleton<DatabaseReader> _databaseReaderDCLSingleton =
		new DCLSingleton<>();

	@Reference
	private Portal _portal;

	private volatile Map<String, String> _properties;

}