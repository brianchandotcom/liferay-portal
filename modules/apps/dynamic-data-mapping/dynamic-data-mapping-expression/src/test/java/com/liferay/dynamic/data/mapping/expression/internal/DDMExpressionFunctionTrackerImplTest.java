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
import com.liferay.dynamic.data.mapping.expression.internal.helper.DDMExpressionFunctionTrackerHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DDMExpressionFunctionTrackerImplTest extends PowerMockito {

	@Test
	public void testDeactivate() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.ddmExpressionFunctionTrackerHelper = spy(
			new DDMExpressionFunctionTrackerHelper());

		ddmExpressionFunctionTracker.deactivate();

		Mockito.verify(
			ddmExpressionFunctionTracker.ddmExpressionFunctionTrackerHelper,
			Mockito.times(1)
		).clear();
	}

	@Test
	public void testGetDDMExpressionFunction() {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		ddmExpressionFunctionTracker.ddmExpressionFunctionTrackerHelper = spy(
			new DDMExpressionFunctionTrackerHelper());

		Set<String> functionNames = new HashSet<>();

		functionNames.add("function");

		ddmExpressionFunctionTracker.getDDMExpressionFunctions(functionNames);

		Mockito.verify(
			ddmExpressionFunctionTracker.ddmExpressionFunctionTrackerHelper,
			Mockito.times(1)
		).getDDMExpressionFunction(
			"function"
		);
	}

	@Test
	public void testGetDDMExpressionFunctions() throws Exception {
		DDMExpressionFunctionTrackerImpl ddmExpressionFunctionTracker =
			new DDMExpressionFunctionTrackerImpl();

		DDMExpressionFunctionTrackerHelper spy = spy(
			new DDMExpressionFunctionTrackerHelper());

		field(
			DDMExpressionFunctionTrackerHelper.class,
			"ddmExpressionFunctionComponentFactoryMap"
		).set(
			spy, Collections.emptyMap()
		);

		ddmExpressionFunctionTracker.ddmExpressionFunctionTrackerHelper = spy;

		DDMExpressionFunction ddmExpressionFunction1 = mock(
			DDMExpressionFunction.class);

		when(
			ddmExpressionFunction1.getName()
		).thenReturn(
			"function1"
		);

		when(
			spy.getDDMExpressionFunction(Matchers.eq("function1"))
		).thenReturn(
			ddmExpressionFunction1
		);

		DDMExpressionFunction ddmExpressionFunction2 = mock(
			DDMExpressionFunction.class);

		when(
			ddmExpressionFunction2.getName()
		).thenReturn(
			"function2"
		);

		when(
			spy.getDDMExpressionFunction(Matchers.eq("function2"))
		).thenReturn(
			ddmExpressionFunction2
		);

		Set<String> functionNames = new HashSet<>();

		functionNames.add("function1");
		functionNames.add("function2");

		Map<String, DDMExpressionFunction> ddmExpressionFunctions =
			ddmExpressionFunctionTracker.getDDMExpressionFunctions(
				functionNames);

		Assert.assertEquals(
			ddmExpressionFunction1, ddmExpressionFunctions.get("function1"));

		Assert.assertEquals(
			ddmExpressionFunction2, ddmExpressionFunctions.get("function2"));

		InOrder inOrder = Mockito.inOrder(spy);

		inOrder.verify(
			spy, Mockito.times(2)
		).getDDMExpressionFunction(
			Matchers.anyString()
		);
	}

}