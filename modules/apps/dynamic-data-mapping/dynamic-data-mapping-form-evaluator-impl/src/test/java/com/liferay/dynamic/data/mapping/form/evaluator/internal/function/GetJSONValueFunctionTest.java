/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcos Martins
 */
public class GetJSONValueFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		String jsonObjectString = JSONUtil.put(
			"test", "test"
		).toString();

		JSONObject jsonObject = _getJSONValueFunction.apply(jsonObjectString);

		Assert.assertEquals(jsonObjectString, jsonObject.toString());
	}

	@Test
	public void testApplyEmptyValue() {
		JSONObject jsonObject = _getJSONValueFunction.apply(StringPool.BLANK);

		Assert.assertEquals(0, jsonObject.length());
	}

	@Test
	public void testApplyNullValue() {
		JSONObject jsonObject = _getJSONValueFunction.apply(null);

		Assert.assertEquals(0, jsonObject.length());
	}

	private final GetJSONValueFunction _getJSONValueFunction =
		new GetJSONValueFunction();

}