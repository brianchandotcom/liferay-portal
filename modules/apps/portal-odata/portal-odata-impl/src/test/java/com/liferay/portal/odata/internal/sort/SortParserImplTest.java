/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.sort;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class SortParserImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetEntityFieldOptional() {
		EntityField entityField = _sortParserImpl.getEntityField(
			_entityModel.getEntityFieldsMap(), "fieldExternal");

		Assert.assertNotNull(entityField);

		Assert.assertEquals("fieldExternal", entityField.getName());
	}

	@Test
	public void testGetEntityFieldOptionalWithComplexType() {
		EntityField entityField = _sortParserImpl.getEntityField(
			_entityModel.getEntityFieldsMap(),
			"complexFieldExternal/fieldInsideComplexFieldExternal");

		Assert.assertNotNull(entityField);

		Assert.assertEquals(
			"fieldInsideComplexFieldExternal", entityField.getName());
	}

	@Test
	public void testGetEntityFieldOptionalWithInvalidType() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParserImpl.getEntityField(
				_entityModel.getEntityFieldsMap(), "fieldExternal2/invalidType")
		).isInstanceOf(
			InvalidSortException.class
		);

		exception.hasMessageEndingWith("is not a complex property");
	}

	@Test
	public void testGetSortFieldOptionalAsc() {
		SortField sortField = _sortParserImpl.getSortField("fieldExternal:asc");

		Assert.assertNotNull(sortField);

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDefault() {
		SortField sortField = _sortParserImpl.getSortField("fieldExternal");

		Assert.assertNotNull(sortField);

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalDesc() {
		SortField sortField = _sortParserImpl.getSortField(
			"fieldExternal:desc");

		Assert.assertNotNull(sortField);

		Assert.assertEquals(
			"fieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(!sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalInvalidSyntax() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _sortParserImpl.getSortField("fieldExternal:desc:another")
		).isInstanceOf(
			InvalidSortException.class
		);

		exception.hasMessageStartingWith("Unable to parse sort string");
	}

	@Test
	public void testGetSortFieldOptionalNull() {
		Assert.assertNull(_sortParserImpl.getSortField(null));
	}

	@Test
	public void testGetSortFieldOptionalWithComplexType() {
		SortField sortField = _sortParserImpl.getSortField(
			"complexFieldExternal/fieldInsideComplexFieldExternal");

		Assert.assertNotNull(sortField);

		Assert.assertEquals(
			"fieldInsideComplexFieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalWithComplexTypeAsc() {
		SortField sortField = _sortParserImpl.getSortField(
			"complexFieldExternal/fieldInsideComplexFieldExternal:asc");

		Assert.assertNotNull(sortField);

		Assert.assertEquals(
			"fieldInsideComplexFieldInternal",
			sortField.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField.isAscending());
	}

	@Test
	public void testGetSortFieldOptionalWithComplexTypeDesc() {
		SortField sortField = _sortParserImpl.getSortField(
			"complexFieldExternal/fieldInsideComplexFieldExternal:desc");

		Assert.assertNotNull(sortField);

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

		SortField sortField1 = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField1.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField1.isAscending());

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

		SortField sortField1 = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField1.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField1.isAscending());

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

		SortField sortField1 = sortFields.get(0);

		Assert.assertEquals(
			"fieldInternal1",
			sortField1.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(sortField1.isAscending());

		SortField sortField2 = sortFields.get(1);

		Assert.assertEquals(
			"fieldInternal2",
			sortField2.getSortableFieldName(LocaleUtil.getDefault()));

		Assert.assertTrue(!sortField2.isAscending());
	}

	private final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return HashMapBuilder.put(
				"complexFieldExternal",
				(EntityField)new ComplexEntityField(
					"complexFieldExternal",
					Collections.singletonList(
						new StringEntityField(
							"fieldInsideComplexFieldExternal",
							locale -> "fieldInsideComplexFieldInternal")))
			).put(
				"fieldExternal",
				new StringEntityField(
					"fieldExternal", locale -> "fieldInternal")
			).put(
				"fieldExternal1",
				new StringEntityField(
					"fieldExternal1", locale -> "fieldInternal1")
			).put(
				"fieldExternal2",
				new StringEntityField(
					"fieldExternal2", locale -> "fieldInternal2")
			).build();
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private final SortParserImpl _sortParserImpl = new SortParserImpl(
		_entityModel);

}