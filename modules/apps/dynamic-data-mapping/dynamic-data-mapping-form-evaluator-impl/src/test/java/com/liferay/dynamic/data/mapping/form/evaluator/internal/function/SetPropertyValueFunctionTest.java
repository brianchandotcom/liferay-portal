/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Carolina Barbosa
 */
public class SetPropertyValueFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		SetPropertyValueFunction setPropertyValueFunction =
			new SetPropertyValueFunction();

		DefaultDDMExpressionObserver defaultDDMExpressionObserver = Mockito.spy(
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