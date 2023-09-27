/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Paulo Albuquerque
 */
public class OldValueFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply1() {
		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetObjectFieldsOldValuesSupplier(
			() -> HashMapBuilder.<String, Object>put(
				"aaa", "AAA"
			).put(
				"bbb", "BBB"
			).build());

		_oldValueFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertEquals("AAA", _oldValueFunction.apply("aaa"));
		Assert.assertEquals("BBB", _oldValueFunction.apply("bbb"));
		Assert.assertNull(
			_oldValueFunction.apply(RandomTestUtil.randomString()));
	}

	@Test
	public void testApply2() {
		Assert.assertNull(
			_oldValueFunction.apply(RandomTestUtil.randomString()));
	}

	private final OldValueFunction _oldValueFunction = new OldValueFunction();

}