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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.JumpToPageDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDMFormRuleActionSerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSerialize() {
		Mockito.when(
			_jumpToPageDDMFormRuleAction.getSource()
		).thenReturn(
			"1"
		);

		Mockito.when(
			_jumpToPageDDMFormRuleAction.getTarget()
		).thenReturn(
			"3"
		);

		JumpToPageDDMFormRuleActionSerializer
			jumpToPageDDMFormRuleActionSerializer =
				new JumpToPageDDMFormRuleActionSerializer(
					_jumpToPageDDMFormRuleAction);

		String result = jumpToPageDDMFormRuleActionSerializer.serialize(
			_spiDDMFormRuleSerializerContext);

		Assert.assertEquals("jumpPage(1, 3)", result);
	}

	@Test
	public void testSerializeWithEmptyTarget() {
		Mockito.when(
			_jumpToPageDDMFormRuleAction.getTarget()
		).thenReturn(
			StringPool.BLANK
		);

		JumpToPageDDMFormRuleActionSerializer
			jumpToPageDDMFormRuleActionSerializer =
				new JumpToPageDDMFormRuleActionSerializer(
					_jumpToPageDDMFormRuleAction);

		String result = jumpToPageDDMFormRuleActionSerializer.serialize(
			_spiDDMFormRuleSerializerContext);

		Assert.assertNull(result);
	}

	private final JumpToPageDDMFormRuleAction _jumpToPageDDMFormRuleAction =
		Mockito.mock(JumpToPageDDMFormRuleAction.class);
	private final SPIDDMFormRuleSerializerContext
		_spiDDMFormRuleSerializerContext = Mockito.mock(
			SPIDDMFormRuleSerializerContext.class);

}