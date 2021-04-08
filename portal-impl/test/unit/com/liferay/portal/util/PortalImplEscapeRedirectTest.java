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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class PortalImplEscapeRedirectTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				PropsKeys.DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS,
				String.valueOf(2)
			).put(
				PropsKeys.DNS_SECURITY_THREAD_LIMIT, String.valueOf(10)
			).build());
	}

	@Test
	public void testEscapeRedirectWithDomains() throws Exception {
		String[] redirectURLDomainsAllowed =
			PropsValues.REDIRECT_URL_DOMAINS_ALLOWED;
		String redirectURLSecurityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "domain");
		setPropsValuesValue(
			"REDIRECT_URL_DOMAINS_ALLOWED",
			new String[] {"google.com", "localhost"});

		try {

			// Relative path

			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"/web/http:", _portalImpl.escapeRedirect("/web/http:"));
			Assert.assertEquals(
				"web/http:", _portalImpl.escapeRedirect("web/http:"));
			Assert.assertEquals(
				"test@google.com",
				_portalImpl.escapeRedirect("test@google.com"));

			// Relative path with protocol

			Assert.assertNull(_portalImpl.escapeRedirect("https:/path"));
			Assert.assertNull(_portalImpl.escapeRedirect("test:/google.com"));

			// Allowed domains

			Assert.assertEquals(
				"http://localhost",
				_portalImpl.escapeRedirect("http://localhost"));
			Assert.assertEquals(
				"https://localhost:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://localhost:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));
			Assert.assertEquals(
				"https://google.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://google.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));

			// Disabled domains

			Assert.assertNull(
				_portalImpl.escapeRedirect("https://google.comsuffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("https://google.com.suffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("https://prefixgoogle.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("https://prefix.google.com"));

			// Invalid URLs

			Assert.assertNull(_portalImpl.escapeRedirect("//www.google.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("https:google.com"));
			Assert.assertNull(_portalImpl.escapeRedirect(":@liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("http:/web"));
			Assert.assertNull(_portalImpl.escapeRedirect("http:web"));
		}
		finally {
			setPropsValuesValue(
				"REDIRECT_URL_DOMAINS_ALLOWED", redirectURLDomainsAllowed);
			setPropsValuesValue(
				"REDIRECT_URL_SECURITY_MODE", redirectURLSecurityMode);
		}
	}

	@Test
	public void testEscapeRedirectWithIPs() throws Exception {
		String[] redirectURLIPsAllowed = PropsValues.REDIRECT_URL_IPS_ALLOWED;
		String redirectURLSecurityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;

		setPropsValuesValue("DNS_SECURITY_THREAD_LIMIT", 10);
		setPropsValuesValue("DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS", 2);

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "ip");
		setPropsValuesValue(
			"REDIRECT_URL_IPS_ALLOWED",
			new String[] {"127.0.0.1", "SERVER_IP"});

		try {

			// Relative path

			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"liferay.com", _portalImpl.escapeRedirect("liferay.com"));

			// Absolute URL

			Assert.assertEquals(
				"http://localhost",
				_portalImpl.escapeRedirect("http://localhost"));
			Assert.assertEquals(
				"https://localhost:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://localhost:8080/a/b;c=d?e=f&g=h#x=y"));

			Set<String> computerAddresses = _portalImpl.getComputerAddresses();

			for (String computerAddress : computerAddresses) {
				Assert.assertEquals(
					"http://" + computerAddress,
					_portalImpl.escapeRedirect("http://" + computerAddress));
				Assert.assertEquals(
					"https://" + computerAddress + "/a/b;c=d?e=f&g=h#x=y",
					_portalImpl.escapeRedirect(
						"https://" + computerAddress + "/a/b;c=d?e=f&g=h#x=y"));
			}

			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://127.0.0.1suffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://127.0.0.1.suffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://prefix127.0.0.1"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://prefix.127.0.0.1"));
		}
		finally {
			setPropsValuesValue("DNS_SECURITY_THREAD_LIMIT", 10);
			setPropsValuesValue("DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS", 2);
			setPropsValuesValue(
				"REDIRECT_URL_IPS_ALLOWED", redirectURLIPsAllowed);
			setPropsValuesValue(
				"REDIRECT_URL_SECURITY_MODE", redirectURLSecurityMode);
		}
	}

	@Test
	public void testEscapeRedirectWithSubdomains() throws Exception {
		String[] redirectURLDomainsAllowed =
			PropsValues.REDIRECT_URL_DOMAINS_ALLOWED;
		String redirectURLSecurityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "domain");
		setPropsValuesValue(
			"REDIRECT_URL_DOMAINS_ALLOWED",
			new String[] {"*.test.liferay.com", "google.com"});

		try {

			// Relative path

			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"test.liferay.com",
				_portalImpl.escapeRedirect("test.liferay.com"));

			// Absolute URL

			Assert.assertEquals(
				"http://test.liferay.com",
				_portalImpl.escapeRedirect("http://test.liferay.com"));
			Assert.assertEquals(
				"https://test.liferay.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://test.liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"http://second.test.liferay.com",
				_portalImpl.escapeRedirect("http://second.test.liferay.com"));
			Assert.assertEquals(
				"https://second.test.liferay.com:8080/a;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://second.test.liferay.com:8080/a;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));
			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));
			Assert.assertEquals(
				"https://google.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://google.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://test.liferay.comsuffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://test.liferay.com.suffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("http://prefixtest.liferay.com"));
		}
		finally {
			setPropsValuesValue(
				"REDIRECT_URL_DOMAINS_ALLOWED", redirectURLDomainsAllowed);
			setPropsValuesValue(
				"REDIRECT_URL_SECURITY_MODE", redirectURLSecurityMode);
		}
	}

	protected void setPropsValuesValue(String name, Object value)
		throws Exception {

		ReflectionTestUtil.setFieldValue(PropsValues.class, name, value);
	}

	private final PortalImpl _portalImpl = new PortalImpl();

}