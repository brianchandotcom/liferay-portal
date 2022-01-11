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