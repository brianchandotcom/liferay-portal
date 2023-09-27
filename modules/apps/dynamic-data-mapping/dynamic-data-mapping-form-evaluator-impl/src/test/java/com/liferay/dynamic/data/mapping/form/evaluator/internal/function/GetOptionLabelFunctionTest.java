/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Marcos Martins
 */
public class GetOptionLabelFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_getOptionLabelFunction = new GetOptionLabelFunction();
	}

	@Test
	public void testApply() {
		DefaultDDMExpressionFieldAccessor ddmExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		DDMFormFieldOptions ddmFormFieldOptions = Mockito.mock(
			DDMFormFieldOptions.class);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, "Option 1");
		localizedValue.addString(LocaleUtil.BRAZIL, "Opcao 1");

		Mockito.when(
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

		DDMFormFieldOptions ddmFormFieldOptions = Mockito.mock(
			DDMFormFieldOptions.class);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, "Option 1");

		Mockito.when(
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