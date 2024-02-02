/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class OpenSearchDocumentFactoryImplTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_openSearchDocumentFactory = new OpenSearchDocumentFactoryImpl();
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();
	}

	@Test
	public void testArrayOfArrays() throws IOException {
		Collection<Object> values = Arrays.asList(
			Arrays.asList("one", "two", "three"),
			Arrays.asList("four", "five", "six"));

		_assertDocument(
			JSONUtil.put("alpha", values),
			documentBuilder -> documentBuilder.setValues("alpha", values));
	}

	@Test
	public void testArrayOfObjects() throws IOException {
		List<Map<String, Object>> values = Arrays.asList(
			HashMapBuilder.<String, Object>put(
				"first", "John"
			).put(
				"last", "Smith"
			).build(),
			HashMapBuilder.<String, Object>put(
				"first", "Alice"
			).put(
				"last", "White"
			).build());

		_assertDocument(
			JSONUtil.put(
				"group", "fans"
			).put(
				"user", JSONFactoryUtil.createJSONArray(values)
			),
			documentBuilder -> documentBuilder.setString(
				"group", "fans"
			).setValue(
				"user", values
			));
	}

	@Test
	public void testInnerObject() throws IOException {
		_assertDocument(
			JSONUtil.put("alpha", JSONUtil.put("position", "1")),
			documentBuilder -> documentBuilder.setValue(
				"alpha", Collections.singletonMap("position", "1")));
	}

	@Test
	public void testMultipleInnerObjects() throws IOException {
		Map<String, Object> values = HashMapBuilder.<String, Object>put(
			"age", 30
		).put(
			"name",
			HashMapBuilder.put(
				"first", "John"
			).put(
				"last", "Smith"
			).build()
		).build();

		_assertDocument(
			JSONUtil.put(
				"manager", JSONFactoryUtil.createJSONObject(values)
			).put(
				"region", "US"
			),
			documentBuilder -> documentBuilder.setString(
				"region", "US"
			).setValue(
				"manager", values
			));
	}

	@Test
	public void testMultipleValuesSetStrings() throws IOException {
		_assertDocument(
			_toJsonData("alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setStrings(
				"alpha", "one", "two", "three"));
	}

	@Test
	public void testMultipleValuesSetValue() throws IOException {
		_assertDocument(
			_toJsonData("alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setValue(
				"alpha", Arrays.asList("one", "two", "three")));
	}

	@Test
	public void testMultipleValuesSetValues() throws IOException {
		_assertDocument(
			_toJsonData("alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setValues(
				"alpha", Arrays.asList("one", "two", "three")));
	}

	@Test
	public void testNull() throws Exception {
		_assertDocumentSameAsLegacy("{}", null);
	}

	@Test
	public void testNullValue() throws Exception {
		_assertDocument(
			"{\"field\":[null]}",
			builder().setValue(_FIELD, Collections.singleton(null)));
	}

	@Test
	public void testNullValues() throws Exception {
		_assertDocument(
			"{\"field\":[null,null]}",
			builder().setValues(_FIELD, Arrays.asList(null, null)));
	}

	@Test
	public void testSpaces() throws Exception {
		_assertDocument("{\"field\":\" \"}", StringPool.SPACE);

		_assertDocument("{\"field\":\"   \"}", StringPool.THREE_SPACES);
	}

	@Test
	public void testSpacesLegacy() throws Exception {
		assertDocumentLegacy("{\"field\":\"\"}", StringPool.SPACE);

		assertDocumentLegacy("{\"field\":\"\"}", StringPool.THREE_SPACES);
	}

	@Test
	public void testStringBlank() throws Exception {
		_assertDocumentSameAsLegacy("{\"field\":\"\"}", StringPool.BLANK);
	}

	@Test
	public void testStringNull() throws Exception {
		_assertDocumentSameAsLegacy("{\"field\":\"null\"}", StringPool.NULL);
	}

	@SuppressWarnings("deprecation")
	protected void assertDocumentLegacy(String expected, String value) {
		com.liferay.portal.kernel.search.Document document = new DocumentImpl();

		document.addText(_FIELD, new String[] {value});

		JsonData jsonData = _openSearchDocumentFactory.getOpenSearchDocument(
			document);

		Assert.assertEquals(
			expected,
			String.valueOf(jsonData.toJson(new JacksonJsonpMapper())));
	}

	protected DocumentBuilder builder() {
		return new DocumentBuilderImpl();
	}

	private void _assertDocument(
		JsonData expectedJsonData,
		Consumer<DocumentBuilder> documentBuilderConsumer) {

		JsonData actualJsonData =
			_openSearchDocumentFactory.getOpenSearchDocument(
				_buildDocument(documentBuilderConsumer));

		Assert.assertEquals(
			String.valueOf(expectedJsonData.toJson(new JacksonJsonpMapper())),
			String.valueOf(actualJsonData.toJson(new JacksonJsonpMapper())));
	}

	private void _assertDocument(
		JSONObject expectedJSONObject,
		Consumer<DocumentBuilder> documentBuilderConsumer) {

		JsonData jsonData = _openSearchDocumentFactory.getOpenSearchDocument(
			_buildDocument(documentBuilderConsumer));

		Assert.assertEquals(
			expectedJSONObject.toString(),
			String.valueOf(jsonData.toJson(new JacksonJsonpMapper())));
	}

	private void _assertDocument(
		String expected, DocumentBuilder documentBuilder) {

		JsonData jsonData = _openSearchDocumentFactory.getOpenSearchDocument(
			documentBuilder.build());

		Assert.assertEquals(
			expected,
			String.valueOf(jsonData.toJson(new JacksonJsonpMapper())));
	}

	private void _assertDocument(String expected, String value) {
		_assertDocument(
			expected, builder().setStrings(_FIELD, new String[] {value}));
	}

	private void _assertDocumentSameAsLegacy(String expected, String value) {
		_assertDocument(expected, value);
		assertDocumentLegacy(expected, value);
	}

	private Document _buildDocument(
		Consumer<DocumentBuilder> documentBuilderConsumer) {

		DocumentBuilder documentBuilder = builder();

		documentBuilderConsumer.accept(documentBuilder);

		return documentBuilder.build();
	}

	private JsonData _toJsonData(String fieldName, String[] values) {
		return JsonData.of(
			HashMapBuilder.put(
				fieldName, values
			).build());
	}

	private static final String _FIELD = "field";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private OpenSearchDocumentFactory _openSearchDocumentFactory;

}