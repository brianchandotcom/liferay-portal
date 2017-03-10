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

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.DefaultDDLFormRuleAction;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DefaultDDLFormRuleActionSerializerTest {

	@Test
	public void testSerializeSetEnabled() {
		DefaultDDLFormRuleAction defaultDDLFormRuleAction =
			new DefaultDDLFormRuleAction("enable", "field0");

		DefaultDDLFormRuleActionSerializer defaultDDLFormRuleActionSerializer =
			new DefaultDDLFormRuleActionSerializer(defaultDDLFormRuleAction);

		String result = defaultDDLFormRuleActionSerializer.serialize();

		Assert.assertEquals("setEnabled('field0', true)", result);
	}

	@Test
	public void testSerializeSetInvalid() {
		DefaultDDLFormRuleAction defaultDDLFormRuleAction =
			new DefaultDDLFormRuleAction("invalidate", "field0");

		DefaultDDLFormRuleActionSerializer defaultDDLFormRuleActionSerializer =
			new DefaultDDLFormRuleActionSerializer(defaultDDLFormRuleAction);

		String result = defaultDDLFormRuleActionSerializer.serialize();

		Assert.assertEquals("setInvalid('field0', true)", result);
	}

	@Test
	public void testSerializeSetRequired() {
		DefaultDDLFormRuleAction defaultDDLFormRuleAction =
			new DefaultDDLFormRuleAction("require", "field0");

		DefaultDDLFormRuleActionSerializer defaultDDLFormRuleActionSerializer =
			new DefaultDDLFormRuleActionSerializer(defaultDDLFormRuleAction);

		String result = defaultDDLFormRuleActionSerializer.serialize();

		Assert.assertEquals("setRequired('field0', true)", result);
	}

	@Test
	public void testSerializeSetVisible() {
		DefaultDDLFormRuleAction defaultDDLFormRuleAction =
			new DefaultDDLFormRuleAction("show", "field0");

		DefaultDDLFormRuleActionSerializer defaultDDLFormRuleActionSerializer =
			new DefaultDDLFormRuleActionSerializer(defaultDDLFormRuleAction);

		String result = defaultDDLFormRuleActionSerializer.serialize();

		Assert.assertEquals("setVisible('field0', true)", result);
	}

}