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

package com.liferay.parser.apio.internal.architect.sort;

import com.liferay.parser.apio.architect.entity.EntityField;
import com.liferay.parser.apio.architect.entity.EntityModel;
import com.liferay.parser.apio.architect.sort.InvalidSortException;
import com.liferay.parser.apio.architect.sort.SortField;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			_sortParserImpl.getSortFieldOptional("fieldExternal:asc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDefault() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("fieldExternal");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDesc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("fieldExternal:desc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(!sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalInvalidSyntax() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParserImpl.getSortFieldOptional(
				"fieldExternal:desc:another")
		).isInstanceOf(
			InvalidSortException.class
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
		List<SortField> sortFields = _sortParserImpl.parse("fieldExternal1");

		Assert.assertEquals(
			"One sort field should be obtained: " + sortFields, 1,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField.getSortableFieldName(Locale.getDefault()));
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
		List<SortField> sortFields = _sortParserImpl.parse(
			"fieldExternal1,fieldExternal2");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField2.isAscending());
	}

	@Test
	public void testParseTwoFieldsAscAndDesc() {
		List<SortField> sortFields = _sortParserImpl.parse(
			"fieldExternal1:asc,fieldExternal2:desc");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(!sortField2.isAscending());
	}

	@Test
	public void testParseTwoFieldsDefaultAndDesc() {
		List<SortField> sortFields = _sortParserImpl.parse(
			"fieldExternal1,fieldExternal2:desc");

		Assert.assertEquals(
			"Two sort fields should be obtained: " + sortFields, 2,
			sortFields.size());

		SortField sortField = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(Locale.getDefault()));

		Assert.assertTrue(!sortField2.isAscending());
	}

	private SortParserImpl _sortParserImpl = new SortParserImpl(
		new EntityModel() {

			@Override
			public Map<String, EntityField> getEntityFieldsMap() {
				return Stream.of(
					new EntityField(
						"fieldExternal", EntityField.Type.STRING,
						locale -> "fieldInternal"),
					new EntityField(
						"fieldExternal1", EntityField.Type.STRING,
						locale -> "fieldInternal1"),
					new EntityField(
						"fieldExternal2", EntityField.Type.STRING,
						locale -> "fieldInternal2")
				).collect(
					Collectors.toMap(EntityField::getName, Function.identity())
				);
			}

			@Override
			public String getName() {
				return "SomeEntityName";
			}

		});

}