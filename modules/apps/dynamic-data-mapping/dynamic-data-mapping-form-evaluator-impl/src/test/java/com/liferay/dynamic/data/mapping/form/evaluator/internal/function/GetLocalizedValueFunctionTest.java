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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class GetLocalizedValueFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		GetLocalizedValueFunction getLocalizedValueFunction =
			new GetLocalizedValueFunction();

		LocalizedValue localizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(
				"(99) 9999-9999", LocaleUtil.US);

		getLocalizedValueFunction.setDDMExpressionFieldAccessor(
			_getDDMExpressionFieldAccessor(localizedValue));

		Assert.assertEquals(
			localizedValue, getLocalizedValueFunction.apply("inputMaskFormat"));
	}

	@Test
	public void testDDMExpressionFieldAccessorNull() {
		GetLocalizedValueFunction getLocalizedValueFunction =
			new GetLocalizedValueFunction();

		Assert.assertEquals(
			StringPool.BLANK,
			getLocalizedValueFunction.apply("inputMaskFormat"));
	}

	@Test
	public void testLocalizedValueNull() {
		GetLocalizedValueFunction getLocalizedValueFunction =
			new GetLocalizedValueFunction();

		getLocalizedValueFunction.setDDMExpressionFieldAccessor(
			_getDDMExpressionFieldAccessor(null));

		Assert.assertEquals(
			StringPool.BLANK,
			getLocalizedValueFunction.apply("inputMaskFormat"));
	}

	private DDMExpressionFieldAccessor _getDDMExpressionFieldAccessor(
		Object value) {

		DefaultDDMExpressionFieldAccessor defaultDDMExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(value);

		defaultDDMExpressionFieldAccessor.setGetFieldPropertyResponseFunction(
			getFieldPropertyRequest -> builder.build());

		return defaultDDMExpressionFieldAccessor;
	}

}