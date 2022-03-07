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