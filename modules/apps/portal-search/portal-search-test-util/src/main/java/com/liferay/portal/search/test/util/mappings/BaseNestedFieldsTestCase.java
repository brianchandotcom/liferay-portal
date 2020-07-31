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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FieldArray;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public abstract class BaseNestedFieldsTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		addDocumentWithDDMFieldArray();
	}

	@Test
	public void testDDMFieldArrayDontMatchUnrelatedKeywordValue()
		throws Exception {

		assertSearch(
			"ddm__text__41523__TextBox7nkv_en_US",
			"ddmFieldArray.fieldValueKeyword", "true", false, 0);
	}

	@Test
	public void testDDMFieldArrayDontMatchUnrelatedTextValue()
		throws Exception {

		assertSearch(
			"ddm__text__41523__TextBox7nkv_en_US",
			"ddmFieldArray.fieldValueText", "bravo", false, 0);
	}

	@Test
	public void testDDMNestedFieldsDontMatchPartialKeyword() throws Exception {
		assertSearch(
			"ddm__keyword__41523__Textggef_en_US",
			"ddmFieldArray.fieldValueKeyword", "alpha", true, 0);
	}

	@Test
	public void testDDMNestedFieldsDontMatchUnrelatedKeywordValue()
		throws Exception {

		assertSearch(
			"ddm__text__41523__TextBox7nkv_en_US",
			"ddmFieldArray.fieldValueKeyword", "true", true, 0);
	}

	@Test
	public void testDDMNestedFieldsDontMatchUnrelatedTextValue()
		throws Exception {

		assertSearch(
			"ddm__text__41523__TextBox7nkv_en_US",
			"ddmFieldArray.fieldValueText", "bravo", true, 0);
	}

	@Test
	public void testDDMNestedFieldsDynamicMapping() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		GetFieldMappingIndexRequest getFieldMappingIndexRequest =
			new GetFieldMappingIndexRequest(
				new String[] {String.valueOf(getCompanyId())}, getMappingName(),
				new String[] {
					"ddmFieldArray.fieldValueText_en_US",
					"ddmFieldArray.fieldValueKeyword"
				});

		GetFieldMappingIndexResponse getFieldMappingIndexResponse =
			searchEngineAdapter.execute(getFieldMappingIndexRequest);

		Map<String, String> fieldMappings =
			getFieldMappingIndexResponse.getFieldMappings();

		String fieldMapping = fieldMappings.get(String.valueOf(getCompanyId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(fieldMapping);

		Assert.assertEquals(fieldMapping, 2, jsonObject.length());

		JSONObject jsonObject2 = jsonObject.getJSONObject(
			"ddmFieldArray.fieldValueText_en_US");

		JSONObject jsonObject3 = jsonObject2.getJSONObject(
			"fieldValueText_en_US");

		String analyzer = jsonObject3.getString("analyzer");

		Assert.assertEquals(fieldMapping, "english", analyzer);
	}

	@Test
	public void testDDMNestedFieldsMatchKeyword() throws Exception {
		assertSearch(
			"ddm__keyword__41523__Textggef_en_US",
			"ddmFieldArray.fieldValueKeyword", "alpha keyword",
			"alpha keyword");
	}

	@Test
	public void testDDMNestedFieldsMatchLocalizedText() throws Exception {
		assertSearch(
			"ddm__text__41523__TextBoxnj7s_en_US",
			"ddmFieldArray.fieldValueText_en_US", "charlie", "charlie text");
	}

	@Test
	public void testDDMNestedFieldsMatchMultipleValues() throws Exception {
		assertSearch(
			"ddm__text__41523__TextBoxo9us_ja_JP",
			"ddmFieldArray.fieldValueText_ja_JP", "作戦大成功",
			Arrays.asList("作戦大成功", "新規作戦"));
	}

	@Test
	public void testDDMNestedFieldsMatchText() throws Exception {
		assertSearch(
			"ddm__text__41523__TextBox7nkv_en_US",
			"ddmFieldArray.fieldValueText", "alpha", "alpha text");
	}

	protected void addDocumentWithDDMFieldArray() {
		FieldArray fieldArray = new FieldArray("ddmFieldArray");

		addNestedField(
			"ddm__keyword__41523__Booleantua8_en_US_String_sortable",
			"fieldValueKeyword", "true", fieldArray);
		addNestedField(
			"ddm__keyword__41523__Textggef_en_US", "fieldValueKeyword",
			"alpha keyword", fieldArray);
		addNestedField(
			"ddm__keyword__41523__Textp47b_en_US", "fieldValueKeyword",
			"bravo keyword", fieldArray);
		addNestedField(
			"ddm__text__41523__TextBox7nkv_en_US", "fieldValueText",
			"alpha text", fieldArray);
		addNestedField(
			"ddm__text__41523__TextBox6yh3_en_US", "fieldValueText",
			"bravo text", fieldArray);
		addNestedField(
			"ddm__text__41523__TextBoxnj7s_en_US", "fieldValueText_en_US",
			"charlie text", fieldArray);
		addNestedField(
			"ddm__text__41523__TextBoxo9us_ja_JP", "fieldValueText_ja_JP",
			new String[] {"作戦大成功", "新規作戦"}, fieldArray);

		addDocument(DocumentCreationHelpers.field(fieldArray));
	}

	protected void addNestedField(
		String name, String valueFieldName, Object value,
		FieldArray fieldArray) {

		fieldArray.addField(
			NestedDDMFieldArrayUtil.createField(name, valueFieldName, value));
	}

	protected void addQuery(
		SearchRequestBuilder searchRequestBuilder, Query query) {

		searchRequestBuilder.addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).query(
				query
			).build());
	}

	protected void assertOneResult(SearchResponse searchResponse) {
		FieldValuesAssert.assertFieldValue(
			Field.GROUP_ID, getGroupId(), searchResponse);
	}

	protected void assertSearch(
		String fieldName, String valueFieldName, String value,
		boolean mappedAsNested, int expectedCount) {

		Query query = getQuery(
			_buildQuery(fieldName, valueFieldName, value, mappedAsNested),
			mappedAsNested);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(this::fetchSourceIncludes);

				indexingTestHelper.defineRequest(
					searchRequestBuilder -> addQuery(
						searchRequestBuilder, query));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertCount(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						fieldName, expectedCount));
			});
	}

	protected void assertSearch(
		String fieldName, String valueFieldName, String value,
		Object expectedValue) {

		Query query = getQuery(
			_buildQuery(fieldName, valueFieldName, value, true), true);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(this::fetchSourceIncludes);

				indexingTestHelper.defineRequest(
					searchRequestBuilder -> addQuery(
						searchRequestBuilder, query));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> {
						assertOneResult(searchResponse);

						Document document = searchResponse.getDocumentsStream(
						).findAny(
						).get();

						List<?> values = document.getValues("ddmFieldArray");

						Optional<Object> optional =
							NestedDDMFieldArrayUtil.getFieldValue(
								fieldName,
								(Stream<Map<String, Object>>)values.stream());

						Assert.assertEquals(
							expectedValue, optional.orElse(null));
					});
			});
	}

	protected void fetchSourceIncludes(
		SearchRequestBuilder searchRequestBuilder) {

		searchRequestBuilder.fetchSourceIncludes(
			new String[] {"ddmFieldArray.*", Field.GROUP_ID});
	}

	protected abstract String getMappingName();

	protected Query getQuery(
		BooleanQuery booleanQuery, boolean mappedAsNested) {

		if (mappedAsNested) {
			return queries.nested("ddmFieldArray", booleanQuery);
		}

		return booleanQuery;
	}

	private BooleanQuery _buildQuery(
		String fieldName, String valueFieldName, String value,
		boolean mappedAsNested) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		if (mappedAsNested) {
			booleanQuery.addMustQueryClauses(
				queries.term("ddmFieldArray.fieldName", fieldName));
		}
		else {
			booleanQuery.addMustQueryClauses(
				queries.match("ddmFieldArray.fieldName", fieldName));
		}

		if (fieldName.startsWith("ddm__keyword")) {
			booleanQuery.addMustQueryClauses(
				queries.term(valueFieldName, value));
		}
		else {
			booleanQuery.addMustQueryClauses(
				queries.match(valueFieldName, value));
		}

		return booleanQuery;
	}

	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();

}