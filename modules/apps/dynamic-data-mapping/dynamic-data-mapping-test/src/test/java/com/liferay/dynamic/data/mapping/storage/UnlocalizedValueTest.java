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

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class UnlocalizedValueTest {

	@Test
	public void testEqualsWithDifferentValueString() {
		Value value1 = new UnlocalizedValue(StringUtil.randomString());
		Value value2 = new UnlocalizedValue(StringUtil.randomString());

		Assert.assertNotEquals(value1, value2);
	}

	@Test
	public void testEqualsWithSameValueString() {
		String valueString = StringUtil.randomString();

		Value value1 = new UnlocalizedValue(valueString);
		Value value2 = new UnlocalizedValue(valueString);

		Assert.assertEquals(value1, value2);
	}

}