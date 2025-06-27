/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.search.opensearch2.internal.legacy.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.FieldSort;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;

/**
 * @author Rodrigo Guedes de Souza
 */
public class SortTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSortTranslatorWithCustomFieldAscOrder() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_PK, Sort.LONG_TYPE,
				false));

		SortTranslator sortTranslator = new SortTranslator();

		List<SortOptions> sortOptionsList = sortTranslator.translateSorts(
			sortList.toArray(Sort[]::new));

		_assetFirstFieldSort(
			sortOptionsList, Field.ENTRY_CLASS_PK, SortOrder.Asc);
	}

	@Test
	public void testSortTranslatorWithCustomFieldDescOrder() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_PK, Sort.LONG_TYPE,
				true));

		SortTranslator sortTranslator = new SortTranslator();

		List<SortOptions> sortOptionsList = sortTranslator.translateSorts(
			sortList.toArray(Sort[]::new));

		_assetFirstFieldSort(
			sortOptionsList, Field.ENTRY_CLASS_PK, SortOrder.Desc);
	}

	@Test
	public void testSortTranslatorWithEntryClassNameField() {
		List<Sort> sortList = List.of(
			new Sort(
				Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_NAME,
				Sort.STRING_TYPE, true));

		SortTranslator sortTranslator = new SortTranslator();

		List<SortOptions> sortOptionsList = sortTranslator.translateSorts(
			sortList.toArray(Sort[]::new));

		_assetFirstFieldSort(
			sortOptionsList, Field.ENTRY_CLASS_NAME, SortOrder.Desc);
	}

	@Test
	public void testSortTranslatorWithoutSorts() {
		SortTranslator sortTranslator = new SortTranslator();

		List<SortOptions> sortOptionsList = sortTranslator.translateSorts(
			new Sort[0]);

		Assert.assertNotNull(sortOptionsList);
		Assert.assertTrue(sortOptionsList.isEmpty());
	}

	@Test
	public void testSortTranslatorWithPriorityField() {
		List<Sort> sortList = List.of(new Sort(Field.PRIORITY, true));

		SortTranslator sortTranslator = new SortTranslator();

		List<SortOptions> sortOptionsList = sortTranslator.translateSorts(
			sortList.toArray(Sort[]::new));

		_assetFirstFieldSort(sortOptionsList, Field.PRIORITY, SortOrder.Desc);
	}

	private void _assetFirstFieldSort(
		List<SortOptions> sortOptionsList, String expectedField,
		SortOrder expectedSort) {

		SortOptions sortOptions = sortOptionsList.get(0);

		Assert.assertNotNull(sortOptions);

		FieldSort fieldSort = sortOptions.field();

		Assert.assertEquals(expectedField, fieldSort.field());

		if (SortOrder.Desc == expectedSort) {
			Assert.assertNotNull(fieldSort.order());
			Assert.assertEquals(
				expectedSort.name(),
				fieldSort.order(
				).name());
		}
	}

}