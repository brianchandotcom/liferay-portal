/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
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

	@Test
	public void testGetProxySettingsMap() {
		MockedStatic<SystemProperties> systemPropertiesMockedStatic =
			Mockito.mockStatic(SystemProperties.class);

		Mockito.when(
			SystemProperties.get("http.proxyHost")
		).thenReturn(
			"192.168.111.152"
		);

		Mockito.when(
			SystemProperties.get("http.proxyPort")
		).thenReturn(
			"3128"
		);

		DDMRESTDataProvider ddmrestDataProvider = new DDMRESTDataProvider();

		Http http = Mockito.mock(Http.class);

		ReflectionTestUtil.setFieldValue(ddmrestDataProvider, "_http", http);

		Mockito.when(
			http.isNonProxyHost(Mockito.anyString())
		).thenReturn(
			true
		);

		Map<String, Object> proxySettingsMap =
			ddmrestDataProvider.getProxySettingsMap(
				RandomTestUtil.randomString());

		Assert.assertTrue(MapUtil.isEmpty(proxySettingsMap));

		Mockito.when(
			http.isNonProxyHost(Mockito.anyString())
		).thenReturn(
			false
		);

		proxySettingsMap = ddmrestDataProvider.getProxySettingsMap(
			RandomTestUtil.randomString());

		Assert.assertEquals(
			"192.168.111.152", proxySettingsMap.get("proxyHostName"));
		Assert.assertEquals(3128, proxySettingsMap.get("proxyHostPort"));

		systemPropertiesMockedStatic.close();
	}

}