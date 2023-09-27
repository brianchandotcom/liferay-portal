/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.sort;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.mappings.NestedDDMFieldArrayUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseNestedFieldsSortTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testSort1() throws Exception {
		assertSort("ddm__keyword__41523__Booleantua8_en_US_String_sortable");
	}

	@Test
	public void testSort2() throws Exception {
		assertSort("ddm__keyword__41523__Textggef_en_US");
	}

	@Test
	public void testSort3() throws Exception {
		assertSort("ddm__keyword__41523__Textp47b_en_US");
	}

	protected void assertSort(String fieldName) {
		addDocuments(
			value -> DocumentCreationHelpers.oneDDMField(
				fieldName, "ddmFieldValueKeyword", value),
			"C", "B", "A");

		FieldSort fieldSort = sorts.field("ddmFieldArray.ddmFieldValueKeyword");

		fieldSort.setNestedSort(sorts.nested("ddmFieldArray"));

		String expected = "[A, B, C]";

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(this::fetchSourceIncludes);

				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.sorts(
						fieldSort));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> Assert.assertEquals(
						searchResponse.getRequestString(), expected,
						String.valueOf(
							getDDMFieldValues(fieldName, searchResponse))));
			});
	}

	protected SearchRequestBuilder fetchSourceIncludes(
		SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.fetchSourceIncludes(
			new String[] {"ddmFieldArray.*"});
	}

	protected Object getDDMFieldValue(String fieldName, Document document) {
		List<?> values = document.getValues("ddmFieldArray");

		return NestedDDMFieldArrayUtil.getFieldValue(
			fieldName, (List<Map<String, Object>>)values);
	}

	protected List<?> getDDMFieldValues(
		String fieldName, SearchResponse searchResponse) {

		return TransformUtil.transform(
			searchResponse.getDocuments(),
			document -> getDDMFieldValue(fieldName, document));
	}

}