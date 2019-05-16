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

package com.liferay.portal.search.elasticsearch6.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.util.Arrays;
import java.util.Collections;

import org.elasticsearch.common.Strings;

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

	@SuppressWarnings("deprecation")
	protected void assertDocumentLegacy(String value, String json) {
		Document document = new DocumentImpl();

		document.addText(_FIELD, new String[] {value});

		Assert.assertEquals(
			json,
			_elasticsearchDocumentFactory.getElasticsearchDocument(document));
	}

	protected void assertDocumentSameAsLegacy(String value, String json) {
		assertDocument(value, json);
		assertDocumentLegacy(value, json);
	}

	protected DocumentBuilderImpl builder() {
		return new DocumentBuilderImpl();
	}

	private static final String _FIELD = "field";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}