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

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.contributor.ContributorConstants;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Michael C. Han
 */
public class SortFieldBuilderImplTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_sortFieldBuilderImpl = new SortFieldBuilderImpl();

		Mockito.when(
			_indexerRegistry.getIndexer(Mockito.anyString())
		).thenAnswer(
			invocation -> _indexer
		);

		_sortFieldBuilderImpl.indexerRegistry = _indexerRegistry;
		_sortFieldBuilderImpl.props = PropsTestUtil.setProps(
			"index.sortable.text.fields",
			new String[] {
				"firstName", "jobTitle", "lastName", "name", "screenName",
				"title"
			});

		_sortFieldBuilderImpl.activate();
	}

	@Test
	public void testGetSortFieldWithNoSortFieldTranslator() {
		Mockito.when(
			_indexer.getSortField(Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgumentAt(0, String.class)
		);

		String sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField");

		Assert.assertEquals("testField", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "firstName");

		Assert.assertEquals(
			Field.getSortableFieldName("firstName"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.DOUBLE_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.FLOAT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.INT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.LONG_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);
	}

	@Test
	public void testGetSortFieldWithSortFieldTranslator() {
		SortFieldNameTranslator sortFieldNameTranslator = Mockito.mock(
			SortFieldNameTranslator.class);

		Mockito.when(
			sortFieldNameTranslator.getSortFieldName(Mockito.anyString())
		).then(
			invocation -> {
				String orderByCol = invocation.getArgumentAt(0, String.class);

				return StringUtil.upperCaseFirstLetter(orderByCol);
			}
		);

		Map<String, Object> properties = new HashMap<>();

		properties.put(
			ContributorConstants.ENTRY_CLASS_NAME_PROPERTY_KEY,
			"modelClassName");

		_sortFieldBuilderImpl.addSortFieldNameTranslator(
			sortFieldNameTranslator, properties);

		Mockito.when(
			_indexer.getSortField(Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgumentAt(0, String.class)
		);

		String sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField");

		Assert.assertEquals("TestField", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "firstName");

		Assert.assertEquals("FirstName", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.DOUBLE_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.FLOAT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.INT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			"modelClassName", "testField", Sort.LONG_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);
	}

	@Mock
	private Indexer _indexer;

	@Mock
	private IndexerRegistry _indexerRegistry;

	private SortFieldBuilderImpl _sortFieldBuilderImpl;

}