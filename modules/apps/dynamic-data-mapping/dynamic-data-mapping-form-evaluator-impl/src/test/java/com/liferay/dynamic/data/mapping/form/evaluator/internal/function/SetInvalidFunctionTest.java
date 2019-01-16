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
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class SetInvalidFunctionTest extends PowerMockito {

	@Test
	public void testApply() {
		DefaultDDMExpressionObserver defaultDDMExpressionObserver =
			new DefaultDDMExpressionObserver();

		DefaultDDMExpressionObserver spy = spy(defaultDDMExpressionObserver);

		SetInvalidFunction setInvalidFunction = new SetInvalidFunction();

		setInvalidFunction.setDDMExpressionObserver(spy);

		Boolean result = setInvalidFunction.apply(
			"contact", "Custom error message");

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).updateFieldProperty(
			argumentCaptor.capture()
		);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			argumentCaptor.getValue();

		Map<String, Object> properties =
			updateFieldPropertyRequest.getProperties();

		Assert.assertEquals("contact", updateFieldPropertyRequest.getField());

		Assert.assertTrue("valid", properties.containsKey("valid"));
		Assert.assertEquals(
			"Custom error message",
			updateFieldPropertyRequest.getPropertyOptional(
				"errorMessage"
			).get());
		Assert.assertEquals(false, properties.get("valid"));

		Assert.assertTrue(result);
	}

	@Test
	public void testNullObserver() {
		SetInvalidFunction setInvalidFunction = new SetInvalidFunction();

		Boolean result = setInvalidFunction.apply("field", "errorMessage");

		Assert.assertFalse(result);
	}

}