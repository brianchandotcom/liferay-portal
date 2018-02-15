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

package com.liferay.portal.search.elasticsearch.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

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
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();
	}

	@Test
	public void testNull() throws Exception {
		assertElasticsearchDocument(null, "{}");
	}

	@Test
	public void testSpaces() throws Exception {
		assertElasticsearchDocument(StringPool.SPACE, "{\"field\":\"\"}");

		assertElasticsearchDocument(
			StringPool.THREE_SPACES, "{\"field\":\"\"}");
	}

	@Test
	public void testStringBlank() throws Exception {
		assertElasticsearchDocument(StringPool.BLANK, "{\"field\":\"\"}");
	}

	@Test
	public void testStringNull() throws Exception {
		assertElasticsearchDocument(StringPool.NULL, "{\"field\":\"null\"}");
	}

	protected void assertElasticsearchDocument(String value, String json)
		throws Exception {

		Document document = new DocumentImpl();

		document.addText("field", new String[] {value});

		Assert.assertEquals(
			json,
			_elasticsearchDocumentFactory.getElasticsearchDocument(document));
	}

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private final ElasticsearchDocumentFactory _elasticsearchDocumentFactory =
		new DefaultElasticsearchDocumentFactory();

}