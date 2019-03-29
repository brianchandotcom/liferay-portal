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

package com.liferay.portal.odata.internal.sort;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.odata.sort.SortField;

import java.util.Collections;
import java.util.List;
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
	public void testGetEntityFieldOptional() {
		Optional<EntityField> entityFieldOptional =
			_sortParserImpl.getEntityFieldOptional(
				_entityModel.getEntityFieldsMap(), "fieldExternal");

		Assert.assertTrue(entityFieldOptional.isPresent());

		EntityField entityField = entityFieldOptional.get();

		Assert.assertEquals("fieldExternal", entityField.getName());
	}

	@Test
	public void testGetEntityFieldOptionalWithComplexType() {
		Optional<EntityField> entityFieldOptional =
			_sortParserImpl.getEntityFieldOptional(
				_entityModel.getEntityFieldsMap(),
				"complexFieldExternal/fieldInsideComplexFieldExternal");

		Assert.assertTrue(entityFieldOptional.isPresent());

		EntityField entityField = entityFieldOptional.get();

		Assert.assertEquals(
			"fieldInsideComplexFieldExternal", entityField.getName());
	}

	@Test
	public void testGetEntityFieldOptionalWithInvalidType() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParserImpl.getEntityFieldOptional(
				_entityModel.getEntityFieldsMap(), "fieldExternal2/invalidType")
		).isInstanceOf(
			InvalidSortException.class
		);

		exception.hasMessageEndingWith("is not a complex property");
	}

	@Test
	public void testGetSortFieldOptionalAsc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional("fieldExternal:asc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

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
	public void testGetSortFieldOptionalWithComplexType() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional(
				"complexFieldExternal/fieldInsideComplexFieldExternal");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInsideComplexFieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalWithComplexTypeAsc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional(
				"complexFieldExternal/fieldInsideComplexFieldExternal:asc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInsideComplexFieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalWithComplexTypeDesc() {
		Optional<SortField> sortFieldOptional =
			_sortParserImpl.getSortFieldOptional(
				"complexFieldExternal/fieldInsideComplexFieldExternal:desc");

		Assert.assertTrue(sortFieldOptional.isPresent());

		SortField sortField = sortFieldOptional.get();

		Assert.assertEquals(
			"fieldInsideComplexFieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(!sortField.isAscending());
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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));
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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(LocaleUtil.getDefault()));

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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(LocaleUtil.getDefault()));

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
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(!sortField2.isAscending());
	}

	private EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
				new ComplexEntityField(
					"complexFieldExternal",
					Collections.singletonList(
						new StringEntityField(
							"fieldInsideComplexFieldExternal",
							locale -> "fieldInsideComplexFieldInternal"))),
				new StringEntityField(
					"fieldExternal", locale -> "fieldInternal"),
				new StringEntityField(
					"fieldExternal1", locale -> "fieldInternal1"),
				new StringEntityField(
					"fieldExternal2", locale -> "fieldInternal2")
			).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
			);
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private final SortParserImpl _sortParserImpl = new SortParserImpl(
		_entityModel);

}