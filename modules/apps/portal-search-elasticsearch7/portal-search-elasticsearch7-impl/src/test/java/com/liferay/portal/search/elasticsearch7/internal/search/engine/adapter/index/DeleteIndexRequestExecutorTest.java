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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class DeleteIndexRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			DeleteIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();

		_indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME_1, _INDEX_NAME_2);

		IndicesOptions indicesOptions = new IndicesOptions();

		indicesOptions.setAllowNoIndices(true);
		indicesOptions.setExpandToClosedIndices(false);
		indicesOptions.setExpandToOpenIndices(false);
		indicesOptions.setIgnoreUnavailable(true);

		deleteIndexRequest.setIndicesOptions(indicesOptions);

		DeleteIndexRequestExecutorImpl deleteIndexRequestExecutorImpl =
			new DeleteIndexRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
					setIndicesOptionsTranslator(_indicesOptionsTranslator);
				}
			};

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			deleteIndexRequestExecutorImpl.createDeleteIndexRequestBuilder(
				deleteIndexRequest);

		org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
			elasticsearchDeleteIndexRequest =
				deleteIndexRequestBuilder.request();

		String[] indices = elasticsearchDeleteIndexRequest.indices();

		Assert.assertEquals(Arrays.toString(indices), 2, indices.length);
		Assert.assertEquals(_INDEX_NAME_1, indices[0]);
		Assert.assertEquals(_INDEX_NAME_2, indices[1]);

		org.elasticsearch.action.support.IndicesOptions
			elasticsearchIndicesOptions =
				elasticsearchDeleteIndexRequest.indicesOptions();

		Assert.assertEquals(
			indicesOptions.isAllowNoIndices(),
			elasticsearchIndicesOptions.allowNoIndices());
		Assert.assertEquals(
			indicesOptions.isExpandToClosedIndices(),
			elasticsearchIndicesOptions.expandWildcardsClosed());
		Assert.assertEquals(
			indicesOptions.isIgnoreUnavailable(),
			elasticsearchIndicesOptions.ignoreUnavailable());
		Assert.assertEquals(
			indicesOptions.isExpandToOpenIndices(),
			elasticsearchIndicesOptions.expandWildcardsOpen());
		Assert.assertTrue(
			elasticsearchIndicesOptions.allowAliasesToMultipleIndices());
		Assert.assertFalse(elasticsearchIndicesOptions.forbidClosedIndices());
		Assert.assertFalse(elasticsearchIndicesOptions.ignoreAliases());
	}

	private static final String _INDEX_NAME_1 = "test_request_index1";

	private static final String _INDEX_NAME_2 = "test_request_index2";

	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}