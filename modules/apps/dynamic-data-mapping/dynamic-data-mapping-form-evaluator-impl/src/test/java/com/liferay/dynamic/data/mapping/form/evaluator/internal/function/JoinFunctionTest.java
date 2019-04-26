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
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class JoinFunctionTest {

	@Test
	public void testArray() {
		JSONArray jsonArray = new JSONArrayImpl();

		jsonArray.put(
			"value1"
		).put(
			"value2"
		);

		JoinFunction joinFunction = new JoinFunction();

		String actual = joinFunction.apply(jsonArray);

		Assert.assertEquals("value1,value2", actual);
	}

	@Test
	public void testEmptyArray() {
		JSONArray jsonArray = new JSONArrayImpl();

		JoinFunction joinFunction = new JoinFunction();

		String actual = joinFunction.apply(jsonArray);

		Assert.assertEquals(StringPool.BLANK, actual);
	}

	@Test
	public void testSingleValue() {
		JSONArray jsonArray = new JSONArrayImpl();

		jsonArray.put("value1");

		JoinFunction joinFunction = new JoinFunction();

		String actual = joinFunction.apply(jsonArray);

		Assert.assertEquals("value1", actual);
	}

}