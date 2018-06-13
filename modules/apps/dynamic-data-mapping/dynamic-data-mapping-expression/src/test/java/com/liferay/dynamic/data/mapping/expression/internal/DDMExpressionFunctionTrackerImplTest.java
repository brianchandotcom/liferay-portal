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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ServiceTrackerMapFactory.class)
@RunWith(PowerMockRunner.class)
public class DDMExpressionFunctionTrackerImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpServiceTrackerMapFactory();
	}

	@Test
	public void testActivate() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.activate(_bundleContext);

		Assert.assertNotNull(
			ddmExpressionFunctionTracker.
				ddmExpressionFunctionTrackerServiceTrackerMap);
	}

	@Test
	public void testDeactivate() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.
			ddmExpressionFunctionTrackerServiceTrackerMap =
				_ddmExpressionFunctionTrackerServiceTrackerMap;

		ddmExpressionFunctionTracker.deactivate();

		Mockito.verify(
			_ddmExpressionFunctionTrackerServiceTrackerMap, Mockito.times(1)
		).close();
	}

	@Test
	public void testGetDDMExpressionFunction() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.
			ddmExpressionFunctionTrackerServiceTrackerMap =
				_ddmExpressionFunctionTrackerServiceTrackerMap;

		ddmExpressionFunctionTracker.getDDMExpressionFunction("function");

		Mockito.verify(
			_ddmExpressionFunctionTrackerServiceTrackerMap, Mockito.times(1)
		).getService(
			"function"
		);
	}

	@Test
	public void testGetDDMExpressionFunctions() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.
			ddmExpressionFunctionTrackerServiceTrackerMap =
				_ddmExpressionFunctionTrackerServiceTrackerMap;

		Set<String> keySet = new HashSet() {
			{
				add("function1");
				add("function2");
			}
		};

		when(
			_ddmExpressionFunctionTrackerServiceTrackerMap.keySet()
		).thenReturn(
			keySet
		);

		DDMExpressionFunction ddmExpressionFunction1 = mock(
			DDMExpressionFunction.class);

		when(
			_ddmExpressionFunctionTrackerServiceTrackerMap.getService(
				"function1")
		).thenReturn(
			ddmExpressionFunction1
		);

		DDMExpressionFunction ddmExpressionFunction2 = mock(
			DDMExpressionFunction.class);

		when(
			_ddmExpressionFunctionTrackerServiceTrackerMap.getService(
				"function2")
		).thenReturn(
			ddmExpressionFunction2
		);

		Map<String, DDMExpressionFunction> ddmExpressionFunctions =
			ddmExpressionFunctionTracker.getDDMExpressionFunctions();

		Assert.assertEquals(
			ddmExpressionFunction1, ddmExpressionFunctions.get("function1"));

		Assert.assertEquals(
			ddmExpressionFunction2, ddmExpressionFunctions.get("function2"));

		InOrder inOrder = Mockito.inOrder(
			_ddmExpressionFunctionTrackerServiceTrackerMap);

		inOrder.verify(
			_ddmExpressionFunctionTrackerServiceTrackerMap, Mockito.times(1)
		).keySet();

		inOrder.verify(
			_ddmExpressionFunctionTrackerServiceTrackerMap, Mockito.times(2)
		).getService(
			Matchers.anyString()
		);
	}

	protected void setUpServiceTrackerMapFactory() {
		mockStatic(ServiceTrackerMapFactory.class);

		when(
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, DDMExpressionFunction.class,
				"ddm.form.evaluator.function.name")
		).thenReturn(
			_ddmExpressionFunctionTrackerServiceTrackerMap
		);
	}

	@Mock
	private BundleContext _bundleContext;

	@Mock
	private ServiceTrackerMap<String, DDMExpressionFunction>
		_ddmExpressionFunctionTrackerServiceTrackerMap;

}