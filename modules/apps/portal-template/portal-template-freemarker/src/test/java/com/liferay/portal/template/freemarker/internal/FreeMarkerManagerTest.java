/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.freemarker.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.osgi.framework.Bundle;

/**
 * @author Tina Tian
 */
public class FreeMarkerManagerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetMacroLibrary() {
		_testGetMacroLibrary(StringPool.BLANK);
		_testGetMacroLibrary("test.ftl");
		_testGetMacroLibrary("FTL_liferay.ftl as liferay");
		_testGetMacroLibrary("test.ftl, FTL_liferay.ftl as liferay");
	}

	private void _testGetMacroLibrary(String configuredMacroLibrary) {
		FreeMarkerManager freeMarkerManager = new FreeMarkerManager();

		ReflectionTestUtil.setFieldValue(
			freeMarkerManager, "_bundle",
			ProxyUtil.newProxyInstance(
				FreeMarkerManagerTest.class.getClassLoader(),
				new Class<?>[] {Bundle.class},
				(proxy, method, args) -> {
					String methodName = method.getName();

					if (methodName.equals("getResource")) {
						return new URL("http://localhost");
					}

					return null;
				}));
		ReflectionTestUtil.setFieldValue(
			freeMarkerManager, "_freeMarkerEngineConfiguration",
			ProxyUtil.newProxyInstance(
				FreeMarkerManagerTest.class.getClassLoader(),
				new Class<?>[] {FreeMarkerEngineConfiguration.class},
				(proxy, method, args) -> {
					String methodName = method.getName();

					if (methodName.equals("macroLibrary")) {
						return StringUtil.split(configuredMacroLibrary);
					}

					return null;
				}));

		String macroLibrary = ReflectionTestUtil.invoke(
			freeMarkerManager, "_getMacroLibrary", new Class<?>[0]);

		Assert.assertTrue(macroLibrary.contains("FTL_liferay.ftl as liferay"));
	}

}