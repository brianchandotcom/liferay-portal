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

import com.liferay.structured.content.apio.architect.sort.SortField;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class SortParserImplTest {

	@Test
	public void testGetSortFieldOptionalAsc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("field:asc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals("field", sortField.getFieldName());

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDefault() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("field");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals("field", sortField.getFieldName());

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDesc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("field:desc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals("field", sortField.getFieldName());

		Assert.assertTrue(!sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalInvalidSyntax() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParserImpl.getSortFieldOptional("field:desc:another")
		).isInstanceOf(
			RuntimeException.class
		);

		exception.hasMessageStartingWith("Unable to parse sort string");
	}

	@Test
	public void testGetSortFieldOptionalNull() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional(null);

		Assert.assertTrue(!sortFieldOptional.isPresent());
	}

	@Test
	public void testIsAscendingAnotherValue() {
		Assert.assertTrue(_sortParserImpl.isAscending("reverse"));
	}

	@Test
	public void testIsAscendingAscLower() {
		Assert.assertTrue(_sortParserImpl.isAscending("asc"));
	}

	@Test
	public void testIsAscendingAscLowerAndUpper() {
		Assert.assertTrue(_sortParserImpl.isAscending("aSC"));
	}

	@Test
	public void testIsAscendingAscUpper() {
		Assert.assertTrue(_sortParserImpl.isAscending("ASC"));
	}

	@Test
	public void testIsAscendingDescLower() {
		Assert.assertTrue(!_sortParserImpl.isAscending("desc"));
	}

	@Test
	public void testIsAscendingDescLowerAndUpper() {
		Assert.assertTrue(!_sortParserImpl.isAscending("dESC"));
	}

	@Test
	public void testIsAscendingDescUpper() {
		Assert.assertTrue(!_sortParserImpl.isAscending("DESC"));
	}

	@Test
	public void testIsAscendingEmpty() {
		Assert.assertTrue(_sortParserImpl.isAscending(""));
	}

	@Test
	public void testIsAscendingNull() {
		Assert.assertTrue(_sortParserImpl.isAscending(null));
	}

	@Test
	public void testParseEmpty() {
		List<SortField> sortFields = _sortParserImpl.parse("");

		Assert.assertEquals(
			"No sort keys should be obtained: " + sortFields, 0,
			sortFields.size());
	}

	@Test
	public void testParseOneField() {
		List<SortField> sortFields = _sortParserImpl.parse("field1");

		Assert.assertEquals(
			"One sort field should be obtained: " + sortFields, 1,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals("field1", sortField.getFieldName());
	}

	@Test
	public void testParseOnlyComma() {
		List<SortField> sortFields = _sortParserImpl.parse(",");

		Assert.assertEquals(
			"No sort fields should be obtained: " + sortFields, 0,
			sortFields.size());
	}

	@Test
	public void testParseTwoFields() {
		List<SortField> sortFields = _sortParserImpl.parse("field1,field2");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals("field1", sortField.getFieldName());

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals("field2", sortField2.getFieldName());

		Assert.assertTrue(sortField2.isAscending());
	}

	@Test
	public void testParseTwoFieldsAscAndDesc() {
		List<SortField> sortFields = _sortParserImpl.parse(
			"field1:asc,field2:desc");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals("field1", sortField.getFieldName());

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals("field2", sortField2.getFieldName());

		Assert.assertTrue(!sortField2.isAscending());
	}

	@Test
	public void testParseTwoFieldsDefaultAndDesc() {
		List<SortField> sortFields = _sortParserImpl.parse(
			"field1,field2:desc");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals("field1", sortField.getFieldName());

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals("field2", sortField2.getFieldName());

		Assert.assertTrue(!sortField2.isAscending());
	}

	private static final SortParserImpl _sortParserImpl = new SortParserImpl();

}