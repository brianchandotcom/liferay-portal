/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder.internal;

import com.liferay.ip.geocoder.IPInfo;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class IPGeocoderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(_ipGeocoderImpl, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_ipGeocoderImpl, "_portalCache", _portalCache);

		DCLSingleton<DatabaseReader> databaseReaderDCLSingleton =
			ReflectionTestUtil.getFieldValue(
				_ipGeocoderImpl, "_databaseReaderDCLSingleton");

		databaseReaderDCLSingleton.getSingleton(() -> _databaseReader);
	}

	@Test
	public void testGetIPInfoWhenCountryIsoCodeIsNull() throws Exception {
		_setUpDatabaseReader(null);

		IPInfo ipInfo = _ipGeocoderImpl.getIPInfo(_getHttpServletRequest());

		Assert.assertEquals(StringPool.BLANK, ipInfo.getCountryCode());
		Assert.assertEquals("8.8.8.8", ipInfo.getIPAddress());
	}

	@Test
	public void testGetIPInfoWhenCountryIsoCodeIsValid() throws Exception {
		_setUpDatabaseReader("US");

		IPInfo ipInfo = _ipGeocoderImpl.getIPInfo(_getHttpServletRequest());

		Assert.assertEquals("US", ipInfo.getCountryCode());
		Assert.assertEquals("8.8.8.8", ipInfo.getIPAddress());
	}

	private HttpServletRequest _getHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRemoteAddr("8.8.8.8");

		Mockito.when(
			_portal.getOriginalServletRequest(Mockito.any())
		).thenAnswer(
			invocation -> invocation.getArgument(0)
		);

		return mockHttpServletRequest;
	}

	private void _setUpDatabaseReader(String isoCode) throws Exception {
		Country country = Mockito.mock(Country.class);

		Mockito.when(
			country.getIsoCode()
		).thenReturn(
			isoCode
		);

		CountryResponse countryResponse = Mockito.mock(CountryResponse.class);

		Mockito.when(
			countryResponse.getCountry()
		).thenReturn(
			country
		);

		Mockito.when(
			_databaseReader.country(Mockito.any(InetAddress.class))
		).thenReturn(
			countryResponse
		);
	}

	private final DatabaseReader _databaseReader = Mockito.mock(
		DatabaseReader.class);
	private final IPGeocoderImpl _ipGeocoderImpl = new IPGeocoderImpl();
	private final Portal _portal = Mockito.mock(Portal.class);
	private final PortalCache<String, String> _portalCache = Mockito.mock(
		PortalCache.class);

}