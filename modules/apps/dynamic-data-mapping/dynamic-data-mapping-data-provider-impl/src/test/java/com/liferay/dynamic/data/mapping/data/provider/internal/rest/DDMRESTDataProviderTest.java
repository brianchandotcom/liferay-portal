/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Nathaly Gomes
 */
public class DDMRESTDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			SystemProperties.get("http.proxyHost")
		).thenReturn(
			_PROXY_HOST
		);

		Mockito.when(
			SystemProperties.get("http.proxyPort")
		).thenReturn(
			String.valueOf(_PROXY_PORT)
		);

		ReflectionTestUtil.setFieldValue(_ddmrestDataProvider, "_http", _http);
	}

	@After
	public void tearDown() {
		_systemPropertiesMockedStatic.close();
	}

	@Test
	public void testGetProxySettingsMap() {
		Mockito.when(
			_http.isNonProxyHost(Mockito.anyString())
		).thenReturn(
			true
		);

		Assert.assertTrue(
			MapUtil.isEmpty(
				_ddmrestDataProvider.getProxySettingsMap(
					RandomTestUtil.randomString())));

		Mockito.when(
			_http.isNonProxyHost(Mockito.anyString())
		).thenReturn(
			false
		);

		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"proxyHostName", _PROXY_HOST
			).put(
				"proxyHostPort", _PROXY_PORT
			).build(),
			_ddmrestDataProvider.getProxySettingsMap(
				RandomTestUtil.randomString()));
	}

	private static final String _PROXY_HOST = RandomTestUtil.randomString();

	private static final int _PROXY_PORT = RandomTestUtil.randomInt();

	private final DDMRESTDataProvider _ddmrestDataProvider =
		new DDMRESTDataProvider();
	private final Http _http = Mockito.mock(Http.class);
	private final MockedStatic<SystemProperties> _systemPropertiesMockedStatic =
		Mockito.mockStatic(SystemProperties.class);

}