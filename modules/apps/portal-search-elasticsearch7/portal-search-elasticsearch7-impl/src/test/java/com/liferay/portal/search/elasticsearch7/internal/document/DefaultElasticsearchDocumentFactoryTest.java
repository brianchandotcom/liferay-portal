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

package com.liferay.portal.search.elasticsearch7.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class DefaultElasticsearchDocumentFactoryTest {

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();
	}

	@Test
	public void testArrayOfArrays() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.startArray(
				"alpha"
			).startArray(
			).value(
				"one"
			).value(
				"two"
			).value(
				"three"
			).endArray(
			).startArray(
			).value(
				"four"
			).value(
				"five"
			).value(
				"six"
			).endArray(
			).endArray(),
			documentBuilder -> documentBuilder.setValues(
				"alpha",
				Arrays.asList(
					Arrays.asList("one", "two", "three"),
					Arrays.asList("four", "five", "six"))));
	}

	@Test
	public void testArrayOfObjects() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.field(
				"group", "fans"
			).field(
				"user",
				Arrays.asList(
					HashMapBuilder.<String, Object>put(
						"first", "John"
					).put(
						"last", "Smith"
					).build(),
					HashMapBuilder.<String, Object>put(
						"first", "Alice"
					).put(
						"last", "White"
					).build())
			),
			documentBuilder -> documentBuilder.setString(
				"group", "fans"
			).setValue(
				"user",
				Arrays.asList(
					HashMapBuilder.<String, Object>put(
						"first", "John"
					).put(
						"last", "Smith"
					).build(),
					HashMapBuilder.<String, Object>put(
						"first", "Alice"
					).put(
						"last", "White"
					).build())
			));
	}

	@Test
	public void testInnerObject() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.startObject(
				"alpha"
			).field(
				"position", "1"
			).endObject(),
			documentBuilder -> documentBuilder.setValue(
				"alpha", Collections.singletonMap("position", "1")));
	}

	@Test
	public void testMultipleInnerObjects() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.field(
				"region", "US"
			).field(
				"manager",
				HashMapBuilder.<String, Object>put(
					"age", 30
				).put(
					"name",
					HashMapBuilder.put(
						"first", "John"
					).put(
						"last", "Smith"
					).build()
				).build()
			),
			documentBuilder -> documentBuilder.setString(
				"region", "US"
			).setValue(
				"manager",
				HashMapBuilder.<String, Object>put(
					"age", 30
				).put(
					"name",
					HashMapBuilder.put(
						"first", "John"
					).put(
						"last", "Smith"
					).build()
				).build()
			));
	}

	@Test
	public void testMultipleValuesSetStrings() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.array(
				"alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setStrings(
				"alpha", "one", "two", "three"));
	}

	@Test
	public void testMultipleValuesSetValue() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.array(
				"alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setValue(
				"alpha", Arrays.asList("one", "two", "three")));
	}

	@Test
	public void testMultipleValuesSetValues() throws IOException {
		assertDocument(
			xContentBuilder -> xContentBuilder.array(
				"alpha", new String[] {"one", "two", "three"}),
			documentBuilder -> documentBuilder.setValues(
				"alpha", Arrays.asList("one", "two", "three")));
	}

	@Test
	public void testNull() throws Exception {
		assertDocumentSameAsLegacy(null, "{}");
	}

	@Test
	public void testNullValue() throws Exception {
		assertDocument(
			"{\"field\":[null]}",
			builder().setValue(_FIELD, Collections.singleton(null)));
	}

	@Test
	public void testNullValues() throws Exception {
		assertDocument(
			"{\"field\":[null,null]}",
			builder().setValues(_FIELD, Arrays.asList(null, null)));
	}

	@Test
	public void testSpaces() throws Exception {
		assertDocument(StringPool.SPACE, "{\"field\":\" \"}");

		assertDocument(StringPool.THREE_SPACES, "{\"field\":\"   \"}");
	}

	@Test
	public void testSpacesLegacy() throws Exception {
		assertDocumentLegacy(StringPool.SPACE, "{\"field\":\"\"}");

		assertDocumentLegacy(StringPool.THREE_SPACES, "{\"field\":\"\"}");
	}

	@Test
	public void testStringBlank() throws Exception {
		assertDocumentSameAsLegacy(StringPool.BLANK, "{\"field\":\"\"}");
	}

	@Test
	public void testStringNull() throws Exception {
		assertDocumentSameAsLegacy(StringPool.NULL, "{\"field\":\"null\"}");
	}

	public interface XContentBuilderConsumer {

		public void accept(XContentBuilder xContentBuilder) throws IOException;

	}

	protected void assertDocument(
		String expected, DocumentBuilder documentBuilder) {

		Assert.assertEquals(
			expected,
			Strings.toString(
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					documentBuilder.build())));
	}

	protected void assertDocument(String value, String json) {
		assertDocument(
			json, builder().setStrings(_FIELD, new String[] {value}));
	}

	protected void assertDocument(
		XContentBuilderConsumer expectedXContentBuilderConsumer,
		Consumer<DocumentBuilder> actualDocumentBuilderConsumer) {

		XContentBuilder expectedXContentBuilder = createXContentBuilder(
			expectedXContentBuilderConsumer);

		XContentBuilder actualXContentBuilder =
			_elasticsearchDocumentFactory.getElasticsearchDocument(
				buildDocument(actualDocumentBuilderConsumer));

		Assert.assertEquals(
			Strings.toString(expectedXContentBuilder),
			Strings.toString(actualXContentBuilder));
	}

	@SuppressWarnings("deprecation")
	protected void assertDocumentLegacy(String value, String json) {
		com.liferay.portal.kernel.search.Document document = new DocumentImpl();

		document.addText(_FIELD, new String[] {value});

		Assert.assertEquals(
			json,
			_elasticsearchDocumentFactory.getElasticsearchDocument(document));
	}

	protected void assertDocumentSameAsLegacy(String value, String json) {
		assertDocument(value, json);
		assertDocumentLegacy(value, json);
	}

	protected Document buildDocument(
		Consumer<DocumentBuilder> documentBuilderConsumer) {

		DocumentBuilder documentBuilder = builder();

		documentBuilderConsumer.accept(documentBuilder);

		return documentBuilder.build();
	}

	protected DocumentBuilder builder() {
		return new DocumentBuilderImpl();
	}

	protected XContentBuilder createXContentBuilder(
		XContentBuilderConsumer xContentBuilderConsumer) {

		try {
			XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

			xContentBuilder.startObject();

			xContentBuilderConsumer.accept(xContentBuilder);

			return xContentBuilder.endObject();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final String _FIELD = "field";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}