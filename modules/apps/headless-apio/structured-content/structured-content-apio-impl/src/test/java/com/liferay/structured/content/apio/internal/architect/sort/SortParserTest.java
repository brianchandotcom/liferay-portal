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

import com.liferay.structured.content.apio.architect.sort.SortQuery;
import com.liferay.structured.content.apio.architect.sort.SortQuery.SortQueryPart;

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
	public void testGetSortQueryPartAsc() {
		Optional<SortQueryPart> sortQueryPartOptional =
			_sortParser.getSortQueryPart("field:asc");

		Assert.assertTrue(sortQueryPartOptional.isPresent());

		SortQueryPart sortQueryPart = sortQueryPartOptional.get();

		Assert.assertEquals("field", sortQueryPart.getFieldName());

		Assert.assertTrue(sortQueryPart.isAscending());
	}

	@Test
	public void testGetSortQueryPartBadSyntax() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParser.getSortQueryPart("field:desc:another")
		).isInstanceOf(
			RuntimeException.class
		);

		exception.hasMessageStartingWith("Unable to parse sort expression");
	}

	@Test
	public void testGetSortQueryPartDesc() {
		Optional<SortQueryPart> sortQueryPartOptional =
			_sortParser.getSortQueryPart("field:desc");

		Assert.assertTrue(sortQueryPartOptional.isPresent());

		SortQueryPart sortQueryPart = sortQueryPartOptional.get();

		Assert.assertEquals("field", sortQueryPart.getFieldName());

		Assert.assertTrue(!sortQueryPart.isAscending());
	}

	@Test
	public void testGetSortQueryPartNoOrder() {
		Optional<SortQueryPart> sortQueryPartOptional =
			_sortParser.getSortQueryPart("field");

		Assert.assertTrue(sortQueryPartOptional.isPresent());

		SortQueryPart sortQueryPart = sortQueryPartOptional.get();

		Assert.assertEquals("field", sortQueryPart.getFieldName());

		Assert.assertTrue(sortQueryPart.isAscending());
	}

	@Test
	public void testGetSortQueryPartNull() {
		Optional<SortQueryPart> sortQueryPartOptional =
			_sortParser.getSortQueryPart(null);

		Assert.assertTrue(!sortQueryPartOptional.isPresent());
	}

	@Test
	public void testIsAscAnotherValue() {
		Assert.assertTrue(_sortParser.isAscending("reverse"));
	}

	@Test
	public void testIsAscAscLower() {
		Assert.assertTrue(_sortParser.isAscending("asc"));
	}

	@Test
	public void testIsAscAscLowerAndUpper() {
		Assert.assertTrue(_sortParser.isAscending("aSC"));
	}

	@Test
	public void testIsAscAscUpper() {
		Assert.assertTrue(_sortParser.isAscending("ASC"));
	}

	@Test
	public void testIsAscDescLower() {
		Assert.assertTrue(!_sortParser.isAscending("desc"));
	}

	@Test
	public void testIsAscDescLowerAndUpper() {
		Assert.assertTrue(!_sortParser.isAscending("dESC"));
	}

	@Test
	public void testIsAscDescUpper() {
		Assert.assertTrue(!_sortParser.isAscending("DESC"));
	}

	@Test
	public void testIsAscEmpty() {
		Assert.assertTrue(_sortParser.isAscending(""));
	}

	@Test
	public void testIsAscNull() {
		Assert.assertTrue(_sortParser.isAscending(null));
	}

	@Test
	public void testSortEmpty() {
		SortQuery sortQuery = _sortParser.parse("");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"No sort keys should be obtained: " + sortQueryParts, 0,
			sortQueryParts.size());
	}

	@Test
	public void testSortOneField() {
		SortQuery sortQuery = _sortParser.parse("field1");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"One sort key should be obtained: " + sortQueryParts, 1,
			sortQueryParts.size());

		SortQueryPart sortQueryPart = sortQueryParts.get(0);

		Assert.assertEquals("field1", sortQueryPart.getFieldName());
	}

	@Test
	public void testSortOnlyComma() {
		SortQuery sortQuery = _sortParser.parse(",");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"No sort keys should be obtained: " + sortQueryParts, 0,
			sortQueryParts.size());
	}

	@Test
	public void testSortTwoFields() {
		SortQuery sortQuery = _sortParser.parse("field1,field2");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"Two sort query parts should be obtained: " + sortQueryParts, 2,
			sortQueryParts.size());

		SortQueryPart sortQueryPart = sortQueryParts.get(0);

		Assert.assertEquals("field1", sortQueryPart.getFieldName());

		Assert.assertTrue(sortQueryPart.isAscending());

		SortQueryPart sortQueryPart2 = sortQueryParts.get(1);

		Assert.assertEquals("field2", sortQueryPart2.getFieldName());

		Assert.assertTrue(sortQueryPart2.isAscending());
	}

	@Test
	public void testSortTwoFieldsAscAndDesc() {
		SortQuery sortQuery = _sortParser.parse("field1:asc,field2:desc");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"Two sort query parts should be obtained: " + sortQueryParts, 2,
			sortQueryParts.size());

		SortQueryPart sortQueryPart = sortQueryParts.get(0);

		Assert.assertEquals("field1", sortQueryPart.getFieldName());

		Assert.assertTrue(sortQueryPart.isAscending());

		SortQueryPart sortQueryPart2 = sortQueryParts.get(1);

		Assert.assertEquals("field2", sortQueryPart2.getFieldName());

		Assert.assertTrue(!sortQueryPart2.isAscending());
	}

	@Test
	public void testSortTwoFieldsDefaultAndDesc() {
		SortQuery sortQuery = _sortParser.parse("field1,field2:desc");

		List<SortQueryPart> sortQueryParts = sortQuery.getSortQueryParts();

		Assert.assertEquals(
			"Two sort query parts should be obtained: " + sortQueryParts, 2,
			sortQueryParts.size());

		SortQueryPart sortQueryPart = sortQueryParts.get(0);

		Assert.assertEquals("field1", sortQueryPart.getFieldName());

		Assert.assertTrue(sortQueryPart.isAscending());

		SortQueryPart sortQueryPart2 = sortQueryParts.get(1);

		Assert.assertEquals("field2", sortQueryPart2.getFieldName());

		Assert.assertTrue(!sortQueryPart2.isAscending());
	}

	private static final SortParserImpl _sortParser = new SortParserImpl();

}