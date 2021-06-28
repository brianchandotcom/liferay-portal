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
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Carolina Barbosa
 */
public class SetPropertyValueFunctionTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		SetPropertyValueFunction setPropertyValueFunction =
			new SetPropertyValueFunction();

		DefaultDDMExpressionObserver defaultDDMExpressionObserver = spy(
			new DefaultDDMExpressionObserver());

		setPropertyValueFunction.setDDMExpressionObserver(
			defaultDDMExpressionObserver);

		setPropertyValueFunction.apply(
			"predefinedValue", "inputMaskFormat",
			DDMFormValuesTestUtil.createLocalizedValue(
				"(99) 9999-9999", LocaleUtil.US));

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			defaultDDMExpressionObserver, Mockito.times(1)
		).updateFieldProperty(
			argumentCaptor.capture()
		);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			argumentCaptor.getValue();

		Assert.assertEquals(
			"predefinedValue", updateFieldPropertyRequest.getField());

		Map<String, Object> updateFieldPropertyRequestProperties =
			updateFieldPropertyRequest.getProperties();

		Assert.assertEquals(
			DDMFormValuesTestUtil.createLocalizedValue(
				"(99) 9999-9999", LocaleUtil.US),
			updateFieldPropertyRequestProperties.get("inputMaskFormat"));
	}

	@Test
	public void testDDMExpressionObserverNull() {
		SetPropertyValueFunction setPropertyValueFunction =
			new SetPropertyValueFunction();

		Assert.assertFalse(
			setPropertyValueFunction.apply(
				"predefinedValue", "inputMask", true));
	}

}