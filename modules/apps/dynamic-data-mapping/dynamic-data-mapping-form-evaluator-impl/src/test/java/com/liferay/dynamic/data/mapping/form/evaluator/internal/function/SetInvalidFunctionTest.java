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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class SetInvalidFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		DefaultDDMExpressionObserver spyDefaultDDMExpressionObserver =
			Mockito.spy(new DefaultDDMExpressionObserver());

		SetInvalidFunction setInvalidFunction = new SetInvalidFunction();

		setInvalidFunction.setDDMExpressionObserver(
			spyDefaultDDMExpressionObserver);

		Boolean result = setInvalidFunction.apply(
			"contact", "Custom error message");

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spyDefaultDDMExpressionObserver, Mockito.times(1)
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

		Assert.assertFalse((boolean)properties.get("valid"));

		Assert.assertTrue(result);
	}

	@Test
	public void testNullObserver() {
		SetInvalidFunction setInvalidFunction = new SetInvalidFunction();

		Boolean result = setInvalidFunction.apply("field", "errorMessage");

		Assert.assertFalse(result);
	}

}