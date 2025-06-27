/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Guedes de Souza
 */
public class SortTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDefaultSortWithCustomFieldAndAscOrder() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_PK, Sort.LONG_TYPE,
				true));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SortTranslator sortTranslator = new SortTranslator();

		sortTranslator.translate(
			searchSourceBuilder, sortList.toArray(Sort[]::new));

		_assertFirstFieldSort(
			searchSourceBuilder.sorts(), Field.ENTRY_CLASS_PK, "DESC");
	}

	@Test
	public void testDefaultSortWithCustomFieldAndDescOrder() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_PK, Sort.LONG_TYPE,
				false));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SortTranslator sortTranslator = new SortTranslator();

		sortTranslator.translate(
			searchSourceBuilder, sortList.toArray(Sort[]::new));

		_assertFirstFieldSort(
			searchSourceBuilder.sorts(), Field.ENTRY_CLASS_PK, "ASC");
	}

	@Test
	public void testDefaultSortWithEntryClassNameField() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_NAME,
				Sort.STRING_TYPE, true));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SortTranslator sortTranslator = new SortTranslator();

		sortTranslator.translate(
			searchSourceBuilder, sortList.toArray(Sort[]::new));

		_assertFirstFieldSort(
			searchSourceBuilder.sorts(), Field.ENTRY_CLASS_NAME, "DESC");
	}

	@Test
	public void testDefaultSortWithoutSorts() {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SortTranslator sortTranslator = new SortTranslator();

		sortTranslator.translate(searchSourceBuilder, new Sort[0]);

		Assert.assertNull(searchSourceBuilder.sorts());
	}

	@Test
	public void testDefaultSortWithPriorityField() {
		List<Sort> sortList = List.of(new Sort(Field.PRIORITY, true));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SortTranslator sortTranslator = new SortTranslator();

		sortTranslator.translate(
			searchSourceBuilder, sortList.toArray(Sort[]::new));

		_assertFirstFieldSort(
			searchSourceBuilder.sorts(), Field.PRIORITY, "DESC");
	}

	private void _assertFirstFieldSort(
		List<SortBuilder<?>> sorts, String expectedFieldName,
		String expectedOrder) {

		FieldSortBuilder fieldSortBuilder = (FieldSortBuilder)sorts.get(0);

		Assert.assertNotNull(fieldSortBuilder);

		Assert.assertEquals(expectedFieldName, fieldSortBuilder.getFieldName());
		Assert.assertEquals(
			expectedOrder,
			fieldSortBuilder.order(
			).name());
	}

}