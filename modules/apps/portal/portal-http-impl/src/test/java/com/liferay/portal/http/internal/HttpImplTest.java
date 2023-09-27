/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.http.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

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