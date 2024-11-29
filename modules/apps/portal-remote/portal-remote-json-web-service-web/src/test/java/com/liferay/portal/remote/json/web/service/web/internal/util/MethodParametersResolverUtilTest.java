/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tamas Biro
 */
public class MethodParametersResolverUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testResolveMethodParameters() throws Exception {
		MethodParameter[] methodParameters =
			MethodParametersResolverUtil.resolveMethodParameters(
				TestClass.class.getDeclaredMethod(
					"stringAndLongList", String.class, List.class));

		_assertMethodParameter(methodParameters[0], null, "a", String.class);
		_assertMethodParameter(
			methodParameters[1], new Class<?>[] {Long.class}, "longList",
			List.class);

		methodParameters = MethodParametersResolverUtil.resolveMethodParameters(
			TestClass.class.getDeclaredMethod(
				"doubleAndLong", double.class, long.class));

		_assertMethodParameter(methodParameters[0], null, "a", double.class);
		_assertMethodParameter(methodParameters[1], null, "b", long.class);

		methodParameters = MethodParametersResolverUtil.resolveMethodParameters(
			TestStaticClass.class.getDeclaredMethod(
				"objectIntegerMap", Map.class));

		_assertMethodParameter(
			methodParameters[0], new Class<?>[] {Object.class, Integer.class},
			"map", Map.class);
	}

	private void _assertMethodParameter(
		MethodParameter methodParameter, Class<?>[] genericTypes, String name,
		Class<?> type) {

		Assert.assertEquals(genericTypes, methodParameter.getGenericTypes());
		Assert.assertEquals(name, methodParameter.getName());
		Assert.assertEquals(type, methodParameter.getType());
	}

	private static class TestStaticClass {

		public static void objectIntegerMap(Map<Object, Integer> map) {
			map.clear();
		}

	}

	private class TestClass {

		public void doubleAndLong(double a, long b) {
			a = a + b;
		}

		public String stringAndLongList(String a, List<Long> longList) {
			return a + longList.toString();
		}

	}

}