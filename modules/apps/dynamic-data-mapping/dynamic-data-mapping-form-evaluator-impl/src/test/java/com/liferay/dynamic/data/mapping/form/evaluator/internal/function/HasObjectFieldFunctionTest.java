/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class HasObjectFieldFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse() {
		HasObjectFieldFunction hasObjectFieldFunction =
			new HasObjectFieldFunction();

		Assert.assertFalse(hasObjectFieldFunction.apply(null));
		Assert.assertFalse(hasObjectFieldFunction.apply(StringPool.BLANK));
		Assert.assertFalse(hasObjectFieldFunction.apply(new String[0]));
		Assert.assertFalse(hasObjectFieldFunction.apply("[\"\"]"));
	}

	@Test
	public void testApplyTrue() {
		HasObjectFieldFunction hasObjectFieldFunction =
			new HasObjectFieldFunction();

		Assert.assertTrue(
			hasObjectFieldFunction.apply(StringUtil.randomString()));
		Assert.assertTrue(
			hasObjectFieldFunction.apply(
				new String[] {StringUtil.randomString()}));
		Assert.assertTrue(
			hasObjectFieldFunction.apply(
				"[\"" + StringUtil.randomString() + "\"]"));
	}

}