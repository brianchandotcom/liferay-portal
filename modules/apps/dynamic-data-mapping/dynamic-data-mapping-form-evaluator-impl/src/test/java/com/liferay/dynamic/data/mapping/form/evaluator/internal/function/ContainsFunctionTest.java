/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class ContainsFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferay", "forms");

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyJSONArray() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		ContainsFunction containsFunction = new ContainsFunction();

		Assert.assertTrue(containsFunction.apply(JSONUtil.putAll(1L, 2L), "2"));
		Assert.assertFalse(
			containsFunction.apply(JSONUtil.putAll("apple", "banana"), "["));
		Assert.assertFalse(
			containsFunction.apply(JSONUtil.putAll("apple", "banana"), "app"));
		Assert.assertTrue(
			containsFunction.apply(
				JSONUtil.putAll("apple", "banana"), "banana"));
		Assert.assertFalse(
			containsFunction.apply(
				JSONUtil.putAll("apple", "banana"), "cherry"));
	}

	@Test
	public void testApplyTrue() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferayFORMS", "forms");

		Assert.assertTrue(result);
	}

	@Test
	public void testApplyWithNull1() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply((Object)null, "forms");

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyWithNull2() {
		ContainsFunction containsFunction = new ContainsFunction();

		Boolean result = containsFunction.apply("liferay", (Object)null);

		Assert.assertFalse(result);
	}

}