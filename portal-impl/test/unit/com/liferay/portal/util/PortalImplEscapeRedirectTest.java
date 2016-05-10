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

import com.liferay.portal.kernel.util.HttpUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Tomas Polesovsky
 */
public class PortalImplEscapeRedirectTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());
	}

	@Test
	public void testEscapeRedirectWithDomains() throws Exception {
		String securityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;
		String[] allowedDomains = PropsValues.REDIRECT_URL_DOMAINS_ALLOWED;

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "domain");
		setPropsValuesValue(
			"REDIRECT_URL_DOMAINS_ALLOWED", new String[]{
				"google.com", "localhost"
			});

		try {
			Assert.assertEquals(
				"/web/guest",
				_portalImpl.escapeRedirect("/web/guest"));

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
				"google.com",
				_portalImpl.escapeRedirect("google.com"));

			Assert.assertEquals(
				"http://google.com",
				_portalImpl.escapeRedirect("http://google.com"));

			Assert.assertEquals(
				"https://google.com:8080/a/b;c=d?e=f&g=h#x=y",
				_portalImpl.escapeRedirect(
					"https://google.com:8080/a/b;c=d?e=f&g=h#x=y"));

			Assert.assertNull(_portalImpl.escapeRedirect("liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("http://liferay.com"));
			Assert.assertNull(_portalImpl.escapeRedirect(
				"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));

			Assert.assertNull(_portalImpl.escapeRedirect("google.comsuffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("google.com.suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefixgoogle.com"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix.google.com"));
		}
		finally {
			setPropsValuesValue("REDIRECT_URL_DOMAINS_ALLOWED", allowedDomains);
			setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", securityMode);
		}
	}

	@Test
	public void testEscapeRedirectWithIPs() throws Exception {
		String securityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;
		String[] allowedIPs = PropsValues.REDIRECT_URL_IPS_ALLOWED;

		setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", "ip");
		setPropsValuesValue(
			"REDIRECT_URL_IPS_ALLOWED", new String[]{"127.0.0.1", "SERVER_IP"});

		try {
			Assert.assertEquals(
				"/web/guest",
				_portalImpl.escapeRedirect("/web/guest"));

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
			Assert.assertNull(_portalImpl.escapeRedirect(
				"https://liferay.com:8080/a/b;c=d?e=f&g=h#x=y"));

			Assert.assertNull(_portalImpl.escapeRedirect("127.0.0.1suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("127.0.0.1.suffix"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix127.0.0.1"));
			Assert.assertNull(_portalImpl.escapeRedirect("prefix.127.0.0.1"));
		}
		finally {
			setPropsValuesValue("REDIRECT_URL_IPS_ALLOWED", allowedIPs);
			setPropsValuesValue("REDIRECT_URL_SECURITY_MODE", securityMode);
		}
	}

	protected void setPropsValuesValue(String fieldName, Object value)
		throws Exception {

		Field field = field(PropsValues.class, fieldName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(PropsValues.class, value);
	}

	private final PortalImpl _portalImpl = new PortalImpl();

}