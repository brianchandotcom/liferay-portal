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

import com.liferay.dynamic.data.mapping.expression.ExecuteActionRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Inácio Nery
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class JumpPageFunctionTest extends PowerMockito {

	@Test
	public void testExecuteAction() {
		DefaultDDMExpressionActionHandler defaultDDMExpressionActionHandler =
			new DefaultDDMExpressionActionHandler();

		DefaultDDMExpressionActionHandler spy = spy(
			defaultDDMExpressionActionHandler);

		JumpPageFunction jumpPageFunction = new JumpPageFunction();

		jumpPageFunction.setDDMExpressionActionHandler(spy);

		Boolean result = jumpPageFunction.apply(1, 3);

		ArgumentCaptor<ExecuteActionRequest> argumentCaptor =
			ArgumentCaptor.forClass(ExecuteActionRequest.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).executeAction(
			argumentCaptor.capture()
		);

		ExecuteActionRequest executeActionRequest = argumentCaptor.getValue();

		Assert.assertEquals("jumpPage", executeActionRequest.getAction());
		Assert.assertEquals(
			1,
			executeActionRequest.getParameterOptional(
				"from"
			).get());
		Assert.assertEquals(
			3,
			executeActionRequest.getParameterOptional(
				"to"
			).get());

		Assert.assertTrue(result);
	}

	@Test
	public void testNullActionHandler() {
		JumpPageFunction jumpPageFunction = new JumpPageFunction();

		Boolean result = jumpPageFunction.apply(1, 3);

		Assert.assertFalse(result);
	}

}