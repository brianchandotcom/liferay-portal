/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.ExecuteActionRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Inácio Nery
 * @author Leonardo Barros
 */
public class JumpPageFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExecuteAction() {
		DefaultDDMExpressionActionHandler spyDefaultDDMExpressionActionHandler =
			Mockito.spy(new DefaultDDMExpressionActionHandler());

		JumpPageFunction jumpPageFunction = new JumpPageFunction();

		jumpPageFunction.setDDMExpressionActionHandler(
			spyDefaultDDMExpressionActionHandler);

		Boolean result = jumpPageFunction.apply(1, 3);

		ArgumentCaptor<ExecuteActionRequest> argumentCaptor =
			ArgumentCaptor.forClass(ExecuteActionRequest.class);

		Mockito.verify(
			spyDefaultDDMExpressionActionHandler, Mockito.times(1)
		).executeAction(
			argumentCaptor.capture()
		);

		ExecuteActionRequest executeActionRequest = argumentCaptor.getValue();

		Assert.assertEquals("jumpPage", executeActionRequest.getAction());
		Assert.assertEquals(
			1, (Object)executeActionRequest.getParameter("from"));
		Assert.assertEquals(3, (Object)executeActionRequest.getParameter("to"));

		Assert.assertTrue(result);
	}

	@Test
	public void testNullActionHandler() {
		JumpPageFunction jumpPageFunction = new JumpPageFunction();

		Boolean result = jumpPageFunction.apply(1, 3);

		Assert.assertFalse(result);
	}

}