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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class HttpImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String[] stripURLAnchor(String url, String separator) {
					return new String[] {url, StringPool.BLANK};
				}

			});
	}

	@Test
	public void testIsNonProxyHost() throws Exception {
		String domain = "foo.com";
		String ipAddress = "192.168.0.250";
		String ipAddressWithStarWildcard = "182.*.0.250";

		Field field = ReflectionTestUtil.getField(
			HttpImpl.class, "_NON_PROXY_HOSTS");

		Object value = field.get(null);

		try {
			field.set(
				null,
				new String[] {domain, ipAddress, ipAddressWithStarWildcard});

			Assert.assertTrue(_httpImpl.isNonProxyHost(domain));
			Assert.assertTrue(_httpImpl.isNonProxyHost(ipAddress));
			Assert.assertTrue(_httpImpl.isNonProxyHost("182.123.0.250"));
			Assert.assertFalse(_httpImpl.isNonProxyHost("182.100.1.250"));
			Assert.assertFalse(_httpImpl.isNonProxyHost("google.com"));
		}
		finally {
			field.set(null, value);
		}
	}

	private final HttpImpl _httpImpl = new HttpImpl();

}