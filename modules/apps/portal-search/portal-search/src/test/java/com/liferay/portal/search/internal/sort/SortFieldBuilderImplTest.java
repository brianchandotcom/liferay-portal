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

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
public class SortFieldBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_sortFieldBuilderImpl = new SortFieldBuilderImpl();

		Mockito.when(
			_indexerRegistry.getIndexer(Object.class)
		).thenAnswer(
			invocation -> _indexer
		);

		ReflectionTestUtil.setFieldValue(
			_sortFieldBuilderImpl, "_indexerRegistry", _indexerRegistry);
		ReflectionTestUtil.setFieldValue(
			_sortFieldBuilderImpl, "_props",
			PropsTestUtil.setProps(
				"index.sortable.text.fields",
				new String[] {
					"firstName", "jobTitle", "lastName", "name", "screenName",
					"title"
				}));

		_bundleContext = SystemBundleUtil.getBundleContext();

		_sortFieldBuilderImpl.activate(_bundleContext);
	}

	@After
	public void tearDown() {
		_sortFieldBuilderImpl.deactivate();
	}

	@Test
	public void testGetSortFieldWithNoSortFieldTranslator() {
		Mockito.when(
			_indexer.getSortField(Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(0, String.class)
		);

		String sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField");

		Assert.assertEquals("testField", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "firstName");

		Assert.assertEquals(
			Field.getSortableFieldName("firstName"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.DOUBLE_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.FLOAT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.INT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.LONG_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);
	}

	@Test
	public void testGetSortFieldWithSortFieldTranslator() {
		ServiceRegistration<SortFieldNameTranslator> serviceRegistration =
			_bundleContext.registerService(
				SortFieldNameTranslator.class,
				new SortFieldNameTranslator() {

					@Override
					public Class<?> getEntityClass() {
						return Object.class;
					}

					@Override
					public String getSortFieldName(String orderByCol) {
						return StringUtil.upperCaseFirstLetter(orderByCol);
					}

				},
				null);

		Mockito.when(
			_indexer.getSortField(Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(0, String.class)
		);

		String sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField");

		Assert.assertEquals("TestField", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "firstName");

		Assert.assertEquals("FirstName", sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.DOUBLE_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.FLOAT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.INT_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		sortFieldName = _sortFieldBuilderImpl.getSortField(
			Object.class, "testField", Sort.LONG_TYPE);

		Assert.assertEquals(
			Field.getSortableFieldName("testField"), sortFieldName);

		serviceRegistration.unregister();
	}

	private BundleContext _bundleContext;
	private final Indexer<?> _indexer = Mockito.mock(Indexer.class);
	private final IndexerRegistry _indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	private SortFieldBuilderImpl _sortFieldBuilderImpl;

}