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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
public class SetPropertyFunctionTest extends PowerMockito {

	@Test
	public void testApply() {
		SetPropertyFunction<Boolean> setPropertyFunction =
			new SetMultipleFunction();

		DefaultDDMExpressionObserver defaultDDMExpressionObserver =
			new DefaultDDMExpressionObserver();

		DefaultDDMExpressionObserver spy = spy(defaultDDMExpressionObserver);

		setPropertyFunction.setDDMExpressionObserver(spy);

		Boolean result = setPropertyFunction.apply("field", true);

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).updateFieldProperty(
			argumentCaptor.capture()
		);

		Assert.assertTrue(result);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			argumentCaptor.getValue();

		Map<String, Object> properties =
			updateFieldPropertyRequest.getProperties();

		Assert.assertEquals("field", updateFieldPropertyRequest.getField());

		Assert.assertTrue(properties.containsKey("multiple"));
		Assert.assertTrue((boolean)properties.get("multiple"));
	}

	@Test
	public void testNullObserver() {
		SetPropertyFunction<Boolean> setPropertyFunction =
			new SetEnabledFunction();

		Assert.assertFalse(setPropertyFunction.apply("field", true));
	}

}