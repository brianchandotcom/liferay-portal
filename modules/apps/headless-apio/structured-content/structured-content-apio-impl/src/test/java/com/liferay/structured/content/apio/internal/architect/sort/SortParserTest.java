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

package com.liferay.structured.content.apio.internal.architect.sort;

import com.liferay.structured.content.apio.internal.architect.sort.SortParser.SortKey;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class SortParserTest {

	@Test
	public void testGetSortAsc() {
		Optional<SortKey> sortOptional = _sortParser.getSortKey("field:asc");

		Assert.assertTrue(sortOptional.isPresent());

		SortKey sortKey = sortOptional.get();

		Assert.assertEquals("field", sortKey.getFieldName());

		Assert.assertTrue(sortKey.isAsc());
	}

	@Test
	public void testGetSortBadSyntax() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParser.getSortKey("field:desc:another")
		).isInstanceOf(
			RuntimeException.class
		);

		exception.hasMessageStartingWith("Unable to parse sort expression");
	}

	@Test
	public void testGetSortDesc() {
		Optional<SortKey> sortOptional = _sortParser.getSortKey("field:desc");

		Assert.assertTrue(sortOptional.isPresent());

		SortKey sortKey = sortOptional.get();

		Assert.assertEquals("field", sortKey.getFieldName());

		Assert.assertTrue(!sortKey.isAsc());
	}

	@Test
	public void testGetSortNoOrder() {
		Optional<SortKey> sortOptional = _sortParser.getSortKey("field");

		Assert.assertTrue(sortOptional.isPresent());

		SortKey sortKey = sortOptional.get();

		Assert.assertEquals("field", sortKey.getFieldName());

		Assert.assertTrue(sortKey.isAsc());
	}

	@Test
	public void testGetSortNull() {
		Optional<SortKey> sortOptional = _sortParser.getSortKey(null);

		Assert.assertTrue(!sortOptional.isPresent());
	}

	@Test
	public void testIsAscAnotherValue() {
		Assert.assertTrue(_sortParser.isAsc("reverse"));
	}

	@Test
	public void testIsAscAscLower() {
		Assert.assertTrue(_sortParser.isAsc("asc"));
	}

	@Test
	public void testIsAscAscLowerAndUpper() {
		Assert.assertTrue(_sortParser.isAsc("aSC"));
	}

	@Test
	public void testIsAscAscUpper() {
		Assert.assertTrue(_sortParser.isAsc("ASC"));
	}

	@Test
	public void testIsAscDescLower() {
		Assert.assertTrue(!_sortParser.isAsc("desc"));
	}

	@Test
	public void testIsAscDescLowerAndUpper() {
		Assert.assertTrue(!_sortParser.isAsc("dESC"));
	}

	@Test
	public void testIsAscDescUpper() {
		Assert.assertTrue(!_sortParser.isAsc("DESC"));
	}

	@Test
	public void testIsAscEmpty() {
		Assert.assertTrue(_sortParser.isAsc(""));
	}

	@Test
	public void testIsAscNull() {
		Assert.assertTrue(_sortParser.isAsc(null));
	}

	@Test
	public void testSortEmpty() {
		List<SortKey> sortKeys = _sortParser.parse("");

		Assert.assertEquals(
			"No sort keys should be obtained: " + sortKeys, 0, sortKeys.size());
	}

	@Test
	public void testSortOneField() {
		List<SortKey> sortKeys = _sortParser.parse("field1");

		Assert.assertEquals(
			"One sort key should be obtained: " + sortKeys, 1, sortKeys.size());

		SortKey sortKey = sortKeys.get(0);

		Assert.assertEquals("field1", sortKey.getFieldName());
	}

	@Test
	public void testSortOnlyComma() {
		List<SortKey> sortKeys = _sortParser.parse(",");

		Assert.assertEquals(
			"No sort keys should be obtained: " + sortKeys, 0, sortKeys.size());
	}

	@Test
	public void testSortTwoFields() {
		List<SortKey> sortKeys = _sortParser.parse("field1,field2");

		Assert.assertEquals(
			"Two sort keys should be obtained: " + sortKeys, 2,
			sortKeys.size());

		SortKey sortKey = sortKeys.get(0);

		Assert.assertEquals("field1", sortKey.getFieldName());

		Assert.assertTrue(sortKey.isAsc());

		SortKey sortKey2 = sortKeys.get(1);

		Assert.assertEquals("field2", sortKey2.getFieldName());

		Assert.assertTrue(sortKey2.isAsc());
	}

	@Test
	public void testSortTwoFieldsAscAndDesc() {
		List<SortKey> sortKeys = _sortParser.parse("field1:asc,field2:desc");

		Assert.assertEquals(
			"Two sort keys should be obtained: " + sortKeys, 2,
			sortKeys.size());

		SortKey sortKey = sortKeys.get(0);

		Assert.assertEquals("field1", sortKey.getFieldName());

		Assert.assertTrue(sortKey.isAsc());

		SortKey sortKey2 = sortKeys.get(1);

		Assert.assertEquals("field2", sortKey2.getFieldName());

		Assert.assertTrue(!sortKey2.isAsc());
	}

	@Test
	public void testSortTwoFieldsDefaultAndDesc() {
		List<SortKey> sortKeys = _sortParser.parse("field1,field2:desc");

		Assert.assertEquals(
			"Two sort keys should be obtained: " + sortKeys, 2,
			sortKeys.size());

		SortKey sortKey = sortKeys.get(0);

		Assert.assertEquals("field1", sortKey.getFieldName());

		Assert.assertTrue(sortKey.isAsc());

		SortKey sortKey2 = sortKeys.get(1);

		Assert.assertEquals("field2", sortKey2.getFieldName());

		Assert.assertTrue(!sortKey2.isAsc());
	}

	private static final SortParser _sortParser = new SortParser();

}