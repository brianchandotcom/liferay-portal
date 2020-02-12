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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class CreateIndexRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			CreateIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		StringBundler sb = new StringBundler(12);

		sb.append("{\n");
		sb.append("    \"settings\": {\n");
		sb.append("        \"analysis\": {\n");
		sb.append("            \"analyzer\": {\n");
		sb.append("                \"content\": {\n");
		sb.append("                    \"tokenizer\": \"whitespace\",\n");
		sb.append("                    \"type\": \"custom\"\n");
		sb.append("                }\n");
		sb.append("            }\n");
		sb.append("        }\n");
		sb.append("    }\n");
		sb.append("}");

		createIndexRequest.setSource(sb.toString());

		CreateIndexRequestExecutorImpl createIndexRequestExecutorImpl =
			new CreateIndexRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		org.elasticsearch.action.admin.indices.create.CreateIndexRequest
			elasticsearchCreateIndexRequest =
				createIndexRequestExecutorImpl.createCreateIndexRequest(
					createIndexRequest);

		Assert.assertEquals(
			_INDEX_NAME, elasticsearchCreateIndexRequest.index());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}