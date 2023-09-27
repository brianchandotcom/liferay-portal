/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class SetPropertyFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		SetMultipleFunction setMultipleFunction = new SetMultipleFunction();

		DefaultDDMExpressionObserver spyDefaultDDMExpressionObserver =
			Mockito.spy(new DefaultDDMExpressionObserver());

		setMultipleFunction.setDDMExpressionObserver(
			spyDefaultDDMExpressionObserver);

		Boolean result = setMultipleFunction.apply("field", true);

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spyDefaultDDMExpressionObserver, Mockito.times(1)
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
		SetEnabledFunction setEnabledFunction = new SetEnabledFunction();

		Assert.assertFalse(setEnabledFunction.apply("field", true));
	}

}