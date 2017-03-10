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

package com.liferay.dynamic.data.lists.form.web.internal.converter.serializer;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.JumpToPageDDLFormRuleAction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class JumpToPageDDLFormRuleActionSerializerTest extends PowerMockito {

	@Test
	public void testSerialize() {
		when(
			_jumpToPageDDLFormRuleAction.getSource()
		).thenReturn(
			"1"
		);

		when(
			_jumpToPageDDLFormRuleAction.getTarget()
		).thenReturn(
			"3"
		);

		JumpToPageDDLFormRuleActionSerializer
			jumpToPageDDLFormRuleActionSerializer =
				new JumpToPageDDLFormRuleActionSerializer(
					_jumpToPageDDLFormRuleAction);

		String result = jumpToPageDDLFormRuleActionSerializer.serialize();

		Assert.assertEquals("jumpPage(1, 3)", result);
	}

	@Mock
	private JumpToPageDDLFormRuleAction _jumpToPageDDLFormRuleAction;

}