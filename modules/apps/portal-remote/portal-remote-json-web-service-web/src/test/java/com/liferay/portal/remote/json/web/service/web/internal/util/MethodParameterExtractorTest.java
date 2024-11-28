/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tamas Biro
 */
public class MethodParameterExtractorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_methodParameters = new ArrayList<>();
	}

	@Test
	public void testGetMethodParametersFromGenerics()
		throws NoSuchMethodException, PortalException {

		_inputClass = TestClass.class;

		_inputMethod = _inputClass.getDeclaredMethod(
			"_stringWithGenerics", String.class, List.class);

		_methodParameters = MethodParameterExtractor.getMethodParameters(
			_inputClass, _inputMethod);

		Assert.assertEquals(
			"a",
			_methodParameters.get(
				0
			).getName());
		Assert.assertEquals(
			"longList",
			_methodParameters.get(
				1
			).getName());
		Assert.assertEquals(
			_methodParameters.get(
				0
			).getType(),
			String.class);
		Assert.assertEquals(
			_methodParameters.get(
				1
			).getType(),
			List.class);

		Assert.assertTrue(
			_checkGenericTypes(null, new Class<?>[] {Long.class}));
	}

	@Test
	public void testGetMethodParametersFromPrimitives()
		throws NoSuchMethodException, PortalException {

		_inputClass = TestClass.class;

		_inputMethod = _inputClass.getDeclaredMethod(
			"_withPrimitives", double.class, long.class);

		_methodParameters = MethodParameterExtractor.getMethodParameters(
			_inputClass, _inputMethod);

		Assert.assertEquals(
			"a",
			_methodParameters.get(
				0
			).getName());
		Assert.assertEquals(
			"b",
			_methodParameters.get(
				1
			).getName());
		Assert.assertEquals(
			_methodParameters.get(
				0
			).getType(),
			double.class);
		Assert.assertEquals(
			_methodParameters.get(
				1
			).getType(),
			long.class);

		Assert.assertTrue(_checkGenericTypes(null, null));
	}

	@Test
	public void testGetMethodParametersFromStaticMethod()
		throws NoSuchMethodException, PortalException {

		_inputClass = TestStaticClass.class;

		_inputMethod = _inputClass.getDeclaredMethod("_mapGenerics", Map.class);

		_methodParameters = MethodParameterExtractor.getMethodParameters(
			_inputClass, _inputMethod);

		Assert.assertEquals(
			"map",
			_methodParameters.get(
				0
			).getName());
		Assert.assertEquals(
			_methodParameters.get(
				0
			).getType(),
			Map.class);

		Assert.assertTrue(
			_checkGenericTypes(new Class<?>[] {Object.class, Integer.class}));
	}

	private boolean _checkGenericTypes(Class[]... classes) {
		if (_methodParameters.size() != classes.length) {
			return false;
		}

		int matchCounter = 0;

		for (int i = 0; i < classes.length; i++) {
			Class<?>[] extractedClassArray = _methodParameters.get(
				i
			).getGenericTypes();

			if ((extractedClassArray == null) && (classes[i] == null)) {
				matchCounter++;
			}
			else if (Arrays.equals(extractedClassArray, classes[i])) {
				matchCounter++;
			}
		}

		if (matchCounter == classes.length) {
			return true;
		}

		return false;
	}

	private Class<?> _inputClass;
	private Method _inputMethod;
	private List<MethodParameter> _methodParameters;

	private static class TestStaticClass {

		private void _mapGenerics(Map<Object, Integer> map) {
		}

	}

	private class TestClass {

		private String _stringWithGenerics(String a, List<Long> longList) {
			return "return";
		}

		private void _withPrimitives(double a, long b) {
		}

	}

}