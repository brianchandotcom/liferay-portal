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

import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
public class GetOptionLabelFunctionTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_getOptionLabelFunction = new GetOptionLabelFunction();
	}

	@Test
	public void testApply() {
		DefaultDDMExpressionFieldAccessor ddmExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		DDMFormFieldOptions ddmFormFieldOptions = mock(
			DDMFormFieldOptions.class);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, "Option 1");
		localizedValue.addString(LocaleUtil.BRAZIL, "Opcao 1");

		when(
			ddmFormFieldOptions.getOptionLabels(Mockito.eq("optionName"))
		).thenReturn(
			localizedValue
		);

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(ddmFormFieldOptions);

		ddmExpressionFieldAccessor.setGetFieldPropertyResponseFunction(
			getFieldPropertyRequest -> builder.build());

		_getOptionLabelFunction.setDDMExpressionFieldAccessor(
			ddmExpressionFieldAccessor);

		_getOptionLabelFunction.setDDMExpressionParameterAccessor(
			new DefaultDDMExpressionParameterAccessor());

		Assert.assertEquals(
			"Opcao 1",
			_getOptionLabelFunction.apply("fieldName", "optionName"));
	}

	@Test
	public void testApplyWithNullDDMExpressionFieldAccessor() {
		Object result = _getOptionLabelFunction.apply(
			"fieldName", "optionName");

		Assert.assertEquals(StringPool.BLANK, result);
	}

	@Test
	public void testApplyWithNullLocale() {
		DefaultDDMExpressionFieldAccessor ddmExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		DDMFormFieldOptions ddmFormFieldOptions = mock(
			DDMFormFieldOptions.class);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, "Option 1");

		when(
			ddmFormFieldOptions.getOptionLabels(Mockito.eq("optionName"))
		).thenReturn(
			localizedValue
		);

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(ddmFormFieldOptions);

		ddmExpressionFieldAccessor.setGetFieldPropertyResponseFunction(
			getFieldPropertyRequest -> builder.build());

		_getOptionLabelFunction.setDDMExpressionFieldAccessor(
			ddmExpressionFieldAccessor);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetLocaleSupplier(() -> null);

		_getOptionLabelFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertEquals(
			"Option 1",
			_getOptionLabelFunction.apply("fieldName", "optionName"));
	}

	private GetOptionLabelFunction _getOptionLabelFunction;

}