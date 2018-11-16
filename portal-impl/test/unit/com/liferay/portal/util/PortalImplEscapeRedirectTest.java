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
import com.liferay.portal.kernel.util.HttpUtil;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class PortalImplEscapeRedirectTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());
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
			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"http://localhost",
				_portalImpl.escapeRedirect("http://localhost"));
			Assert.assertEquals(
				"https://localhost:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://localhost:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"google.com", _portalImpl.escapeRedirect("google.com"));
			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));
			Assert.assertEquals(
				"https://google.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://google.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"test:test@google.com",
				_portalImpl.escapeRedirect("test:test@google.com"));
			Assert.assertEquals(
				"test@google.com",
				_portalImpl.escapeRedirect("test@google.com"));
			Assert.assertEquals(
				":@google.com", _portalImpl.escapeRedirect(":@google.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(_portalImpl.escapeRedirect("google.comsuffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("google.com.suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefixgoogle.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix.google.com"));
			Assert.assertNull(_portalImpl.escapeRedirect(":google.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("test:google.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("test:test@liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("test@liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("@liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect(":@liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("//www.google.com"));
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

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "ip");
		setPropsValuesValue(
			"REDIRECT_URL_IPS_ALLOWED",
			new String[] {"127.0.0.1", "SERVER_IP"});

		try {
			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
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

			Assert.assertNull(_portalImpl.escapeRedirect("liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(_portalImpl.escapeRedirect("127.0.0.1suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("127.0.0.1.suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix127.0.0.1"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix.127.0.0.1"));
		}
		finally {
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
			Assert.assertEquals("/", _portalImpl.escapeRedirect("/"));
			Assert.assertEquals(
				"/web/guest", _portalImpl.escapeRedirect("/web/guest"));
			Assert.assertEquals(
				"/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect("/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"test.liferay.com",
				_portalImpl.escapeRedirect("test.liferay.com"));
			Assert.assertEquals(
				"http://test.liferay.com",
				_portalImpl.escapeRedirect("http://test.liferay.com"));
			Assert.assertEquals(
				"https://test.liferay.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://test.liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertEquals(
				"second.test.liferay.com",
				_portalImpl.escapeRedirect("second.test.liferay.com"));
			Assert.assertEquals(
				"http://second.test.liferay.com",
				_portalImpl.escapeRedirect("http://second.test.liferay.com"));
			Assert.assertEquals(
				"https://second.test.liferay.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://second.test.liferay.com:8080/a/b;c=d?e=f&g=h#x=" +
						"y"));
			Assert.assertEquals(
				"google.com", _portalImpl.escapeRedirect("google.com"));
			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));
			Assert.assertEquals(
				"https://google.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://google.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(_portalImpl.escapeRedirect("liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(
				_portalImpl.escapeRedirect(
					"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("test.liferay.comsuffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("test.liferay.com.suffix"));
			Assert.assertNull(
				_portalImpl.escapeRedirect("prefixtest.liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("google.comsuffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("google.com.suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefixgoogle.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix.google.com"));
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