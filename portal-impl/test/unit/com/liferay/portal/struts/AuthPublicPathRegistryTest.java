/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.struts;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class AuthPublicPathRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContains() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<Object> serviceRegistration =
			bundleContext.registerService(
				Object.class, new Object(),
				MapUtil.singletonDictionary(
					"auth.public.path", _TEST_AUTH_PUBLIC_PATH));

		try {
			Assert.assertTrue(
				_TEST_AUTH_PUBLIC_PATH + " not found",
				AuthPublicPathRegistry.contains(_TEST_AUTH_PUBLIC_PATH));
			Assert.assertFalse(
				_UNKNOW_AUTH_PUBLIC_PATH + " should not be found",
				AuthPublicPathRegistry.contains(_UNKNOW_AUTH_PUBLIC_PATH));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _TEST_AUTH_PUBLIC_PATH =
		"TEST_AUTH_PUBLIC_PATH";

	private static final String _UNKNOW_AUTH_PUBLIC_PATH =
		"UNKNOW_AUTH_PUBLIC_PATH";

}