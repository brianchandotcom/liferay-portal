/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Objects;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ProxyConfigTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_systemProperties = new Properties(System.getProperties());
	}

	@After
	public void tearDown() {
		System.setProperties(_systemProperties);
	}

	@Test
	public void testShouldApplyConfigWithHostAndPort() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).port(
			32000
		).build();

		Assert.assertTrue(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldApplyConfigWithHostAndPortInSystemProperties() {
		System.setProperty("http.proxyHost", "http://proxy");
		System.setProperty("http.proxyPort", "32000");

		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.build();

		Assert.assertTrue(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldApplyConfigWithHostAndPortOfProxyHost() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		String domain = "domain";
		String networkAddress = "http://domain:9200";

		Mockito.when(
			_http.isNonProxyHost(domain)
		).thenReturn(
			Objects.equals(domain, "nonProxyHostDomain")
		);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).networkAddresses(
			new String[] {networkAddress}
		).port(
			32000
		).build();

		Assert.assertTrue(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldNotApplyConfigWithHostAndPortOfNonProxyHost() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		String domain = "domain";
		String networkAddress = "http://domain:9200";

		Mockito.when(
			_http.isNonProxyHost(domain)
		).thenReturn(
			true
		);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).networkAddresses(
			new String[] {networkAddress}
		).port(
			32000
		).build();

		Assert.assertFalse(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldNotApplyConfigWithoutHost() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.port(
			32000
		).build();

		Assert.assertFalse(proxyConfig.shouldApplyConfig());
	}

	@Test
	public void testShouldNotApplyConfigWithoutPort() {
		ProxyConfig.Builder builder = ProxyConfig.builder(_http);

		ProxyConfig proxyConfig = builder.host(
			"http://proxy"
		).build();

		Assert.assertFalse(proxyConfig.shouldApplyConfig());
	}

	private final Http _http = Mockito.mock(Http.class);
	private Properties _systemProperties;

}