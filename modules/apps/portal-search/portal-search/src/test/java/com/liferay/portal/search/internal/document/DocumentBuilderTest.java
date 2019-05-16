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

package com.liferay.portal.search.internal.document;

import com.liferay.portal.search.document.DocumentBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class DocumentBuilderTest {

	@Test
	public void testDefaults() {
		assertDocument(
			"{double=3.1415, doubles=[3.1415, 142857.0], string=a, " +
				"strings=[a, b], value=x, values=[2147483647, " +
					"9223372036854775807, {foo=bar}]}",
			documentBuilder.setDouble(
				"double", 3.1415
			).setDoubles(
				"doubles", 3.1415, Double.valueOf(142857)
			).setString(
				"string", "a"
			).setStrings(
				"strings", "a", "b"
			).setValue(
				"value", "x"
			).setValues(
				"values",
				Arrays.asList(
					Integer.MAX_VALUE, Long.MAX_VALUE,
					Collections.singletonMap("foo", "bar"))
			));
	}

	@Test
	public void testEmpty() {
		assertDocument(
			"{}",
			documentBuilder.setStrings(
				"strings1"
			).setStrings(
				"strings2", new String[0]
			).setValue(
				"value", Collections.emptyList()
			).setValues(
				"values", Collections.emptyList()
			));
	}

	@Test
	public void testFieldOrderIsStable() {
		assertDocument(
			"{z=26, y=25, x=24, b=2, a=1}",
			documentBuilder.setInteger(
				"z", 26
			).setInteger(
				"y", 25
			).setInteger(
				"x", 24
			).setInteger(
				"b", 2
			).setInteger(
				"a", 1
			));
	}

	@Test
	public void testNull() {
		List<String> nulls = Arrays.asList(null, null);

		assertDocument(
			"{}",
			documentBuilder.setString(
				"string", null
			).setStrings(
				"strings1", nulls.get(0)
			).setStrings(
				"strings2", new String[1]
			).setValue(
				"value", null
			).setValues(
				"values", null
			));
	}

	@Test
	public void testNullValues() {
		assertDocument(
			"{strings1=[null, null], strings2=[null, null], values1=null, " +
				"values2=[null, null]}",
			documentBuilder.setStrings(
				"strings1", null, null
			).setStrings(
				"strings2", new String[2]
			).setValues(
				"values1", Collections.singleton(null)
			).setValues(
				"values2", Arrays.asList(null, null)
			));
	}

	protected static void assertDocument(
		String expected, DocumentBuilder documentBuilder) {

		Assert.assertEquals(
			expected,
			String.valueOf(
				documentBuilder.build(
				).getFields()));
	}

	protected DocumentBuilder documentBuilder = new DocumentBuilderImpl();

}