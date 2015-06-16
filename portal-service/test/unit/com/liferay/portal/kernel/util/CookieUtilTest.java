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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CookieUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new CookieUtil();
	}

	@Test
	public void testEquals() {

		// Comment

		Cookie cookie1 = new Cookie("name", null);

		cookie1.setComment("comment");

		Cookie cookie2 = new Cookie("name2", null);

		cookie2.setComment("comment2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setComment("comment");

		// Domain

		cookie1.setDomain("domain");
		cookie2.setDomain("domain2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setDomain("domain");

		// Max age

		cookie1.setMaxAge(1);
		cookie2.setMaxAge(2);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setMaxAge(1);

		// Name

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2 = new Cookie("name", null);

		cookie2.setComment("comment");
		cookie2.setDomain("domain");
		cookie2.setMaxAge(1);

		// Path

		cookie1.setPath("path");
		cookie2.setPath("path2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setPath("path");

		// Secure

		cookie1.setSecure(true);
		cookie2.setSecure(false);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setSecure(true);

		// Value

		cookie1.setValue("value");
		cookie2.setValue("value2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setValue("value");

		// Version

		cookie1.setVersion(1);
		cookie2.setVersion(2);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setVersion(1);

		// HTTP only

		cookie1.setHttpOnly(true);
		cookie2.setHttpOnly(false);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setHttpOnly(true);

		// Equals

		Assert.assertTrue(CookieUtil.equals(cookie1, cookie2));
	}

	@Test
	public void testSerializationAndDeserialization() {
		Cookie cookie1 = new Cookie("name1", null);

		byte[] bytes = CookieUtil.serialize(cookie1);

		Assert.assertTrue(
			CookieUtil.equals(cookie1, CookieUtil.deserialize(bytes)));

		Cookie cookie2 = new Cookie("name2", "value");

		cookie2.setComment("comment");
		cookie2.setDomain("domain");
		cookie2.setHttpOnly(true);
		cookie2.setMaxAge(1);
		cookie2.setPath("path");
		cookie2.setSecure(true);
		cookie2.setVersion(1);

		bytes = CookieUtil.serialize(cookie2);

		Assert.assertTrue(
			CookieUtil.equals(cookie2, CookieUtil.deserialize(bytes)));
	}

	@Test
	public void testToString() {
		Cookie cookie = new Cookie("name", "value");

		cookie.setComment("comment");
		cookie.setDomain("domain");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(1);
		cookie.setPath("path");
		cookie.setSecure(true);
		cookie.setVersion(1);

		Assert.assertEquals(
			"{comment=comment, domain=domain, httpOnly=true, maxAge=1, " +
				"name=name, path=path, secure=true, value=value, version=1}",
			CookieUtil.toString(cookie));
	}

}