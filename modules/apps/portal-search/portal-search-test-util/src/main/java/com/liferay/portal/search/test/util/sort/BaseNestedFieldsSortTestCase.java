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

package com.liferay.portal.search.test.util.sort;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.mappings.NestedDDMFieldArrayUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	protected void addDocumentWithOneDDMField(
		String name, String valueFieldName, Object value) {

		addDocument(
			DocumentCreationHelpers.oneDDMField(name, valueFieldName, value));
	}

	protected void assertSort(String fieldName) {
		Stream.of(
			"C", "B", "A"
		).forEach(
			value -> addDocumentWithOneDDMField(
				fieldName, "ddmFieldValueKeyword", value)
		);

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

		Optional<Object> optional = NestedDDMFieldArrayUtil.getFieldValue(
			fieldName, (Stream<Map<String, Object>>)values.stream());

		return optional.get();
	}

	protected List<?> getDDMFieldValues(
		String fieldName, SearchResponse searchResponse) {

		Stream<Document> stream = searchResponse.getDocumentsStream();

		return stream.map(
			document -> getDDMFieldValue(fieldName, document)
		).collect(
			Collectors.toList()
		);
	}

}