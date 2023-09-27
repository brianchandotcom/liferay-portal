/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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