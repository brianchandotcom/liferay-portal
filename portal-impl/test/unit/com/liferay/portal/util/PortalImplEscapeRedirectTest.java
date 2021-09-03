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

import com.liferay.portal.kernel.redirect.RedirectURLSettings;
import com.liferay.portal.kernel.redirect.RedirectURLSettingsUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() {
		_originalRedirectURLSettings = ReflectionTestUtil.getAndSetFieldValue(
			RedirectURLSettingsUtil.class, "_redirectURLSettings",
			_redirectURLSettingsImpl);
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			RedirectURLSettingsUtil.class, "_redirectURLSettings",
			_originalRedirectURLSettings);
	}

	@Test
	public void testEscapeRedirectWithDomains() throws Exception {
		_redirectURLSettingsImpl.allowedDomains = new String[] {
			"google.com", "localhost"
		};
		_redirectURLSettingsImpl.securityMode = "domain";

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
			"test@google.com", _portalImpl.escapeRedirect("test@google.com"));
		Assert.assertNull(_portalImpl.escapeRedirect("///liferay.com"));

		// Relative path with protocol

		Assert.assertNull(_portalImpl.escapeRedirect("https:/path"));
		Assert.assertNull(_portalImpl.escapeRedirect("test:/google.com"));

		// Allowed domains

		Assert.assertEquals(
			"http://localhost", _portalImpl.escapeRedirect("http://localhost"));
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

	@Test
	public void testEscapeRedirectWithIPs() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS", 2);
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "DNS_SECURITY_THREAD_LIMIT", 10);

		_redirectURLSettingsImpl.allowedIPs = new String[] {
			"127.0.0.1", "SERVER_IP"
		};
		_redirectURLSettingsImpl.securityMode = "ip";

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
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DNS_SECURITY_THREAD_LIMIT", 10);
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS", 2);
		}
	}

	@Test
	public void testEscapeRedirectWithRelativeURL() throws Exception {
		Assert.assertEquals(
			"user/test/~/control_panel/manage/-/select/image%2Clurl/",
			_portalImpl.escapeRedirect(
				"user/test/~/control_panel/manage/-/select/image%2Clurl/"));
		Assert.assertEquals(
			"user/test/~/control_panel/manage/-/select/image,url/",
			_portalImpl.escapeRedirect(
				"user/test/~/control_panel/manage/-/select/image,url/"));
	}

	@Test
	public void testEscapeRedirectWithSubdomains() throws Exception {
		_redirectURLSettingsImpl.allowedDomains = new String[] {
			"*.test.liferay.com", "google.com"
		};
		_redirectURLSettingsImpl.securityMode = "domain";

		// Relative path

		Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
		Assert.assertEquals(
			"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
		Assert.assertEquals(
			"/a/b;c=d?e=f&g=h#x=y",
			_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
		Assert.assertEquals(
			"test.liferay.com", _portalImpl.escapeRedirect("test.liferay.com"));

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

	private RedirectURLSettings _originalRedirectURLSettings;
	private final PortalImpl _portalImpl = new PortalImpl();
	private final RedirectURLSettingsImpl _redirectURLSettingsImpl =
		new RedirectURLSettingsImpl();

	private static class RedirectURLSettingsImpl
		implements RedirectURLSettings {

		@Override
		public String[] getAllowedDomains(long companyId) {
			return GetterUtil.getStringValues(allowedDomains);
		}

		@Override
		public String[] getAllowedIPs(long companyId) {
			return GetterUtil.getStringValues(allowedIPs);
		}

		@Override
		public String getSecurityMode(long companyId) {
			return GetterUtil.getString(securityMode);
		}

		protected String[] allowedDomains;
		protected String[] allowedIPs = {"127.0.0.1", "SERVER_IP"};
		protected String securityMode = "ip";

	}

}