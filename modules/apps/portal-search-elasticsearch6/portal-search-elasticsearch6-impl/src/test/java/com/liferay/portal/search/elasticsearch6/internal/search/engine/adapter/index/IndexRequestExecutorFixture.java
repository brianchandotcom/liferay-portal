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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class IndexRequestExecutorFixture {

	public IndexRequestExecutor getIndexRequestExecutor() {
		return _indexRequestExecutor;
	}

	public void setUp() {
		ElasticsearchClientResolver elasticsearchClientResolver1 =
			elasticsearchClientResolver;

		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator1 =
			new IndexRequestShardFailureTranslatorImpl();

		IndicesOptionsTranslator indicesOptionsTranslator1 =
			new IndicesOptionsTranslatorImpl();

		_indexRequestExecutor = new ElasticsearchIndexRequestExecutor() {
			{
				analyzeIndexRequestExecutor = createAnalyzeIndexRequestExecutor(
					elasticsearchClientResolver1);
				closeIndexRequestExecutor = createCloseIndexRequestExecutor(
					indicesOptionsTranslator1, elasticsearchClientResolver1);
				createIndexRequestExecutor = createCreateIndexRequestExecutor(
					elasticsearchClientResolver1);
				deleteIndexRequestExecutor = createDeleteIndexRequestExecutor(
					indicesOptionsTranslator1, elasticsearchClientResolver1);
				flushIndexRequestExecutor = createFlushIndexRequestExecutor(
					indexRequestShardFailureTranslator1,
					elasticsearchClientResolver1);
				getFieldMappingIndexRequestExecutor =
					createGetFieldMappingIndexRequestExecutor(
						elasticsearchClientResolver1);
				getIndexIndexRequestExecutor =
					createGetIndexIndexRequestExecutor(
						elasticsearchClientResolver1);
				getMappingIndexRequestExecutor =
					createGetMappingIndexRequestExecutor(
						elasticsearchClientResolver1);
				indicesExistsIndexRequestExecutor =
					createIndexExistsIndexRequestExecutor(
						elasticsearchClientResolver1);
				openIndexRequestExecutor = createOpenIndexRequestExecutor(
					indicesOptionsTranslator1, elasticsearchClientResolver1);
				putMappingIndexRequestExecutor =
					createPutMappingIndexRequestExecutor(
						elasticsearchClientResolver1);
				refreshIndexRequestExecutor = createRefreshIndexRequestExecutor(
					indexRequestShardFailureTranslator1,
					elasticsearchClientResolver1);
				updateIndexSettingsIndexRequestExecutor =
					createUpdateIndexSettingsIndexRequestExecutor(
						indicesOptionsTranslator1,
						elasticsearchClientResolver1);
			}
		};
	}

	protected static AnalyzeIndexRequestExecutor
		createAnalyzeIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new AnalyzeIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static CloseIndexRequestExecutor createCloseIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator1,
		ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new CloseIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indicesOptionsTranslator = indicesOptionsTranslator1;
			}
		};
	}

	protected static CreateIndexRequestExecutor
		createCreateIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new CreateIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static DeleteIndexRequestExecutor
		createDeleteIndexRequestExecutor(
			IndicesOptionsTranslator indicesOptionsTranslator1,
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new DeleteIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indicesOptionsTranslator = indicesOptionsTranslator1;
			}
		};
	}

	protected static FlushIndexRequestExecutor createFlushIndexRequestExecutor(
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator1,
		ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new FlushIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indexRequestShardFailureTranslator =
					indexRequestShardFailureTranslator1;
			}
		};
	}

	protected static GetFieldMappingIndexRequestExecutor
		createGetFieldMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new GetFieldMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static GetIndexIndexRequestExecutor
		createGetIndexIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new GetIndexIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static GetMappingIndexRequestExecutor
		createGetMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new GetMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static IndicesExistsIndexRequestExecutor
		createIndexExistsIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new IndicesExistsIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static OpenIndexRequestExecutor createOpenIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator1,
		ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new OpenIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indicesOptionsTranslator = indicesOptionsTranslator1;
			}
		};
	}

	protected static PutMappingIndexRequestExecutor
		createPutMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new PutMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static RefreshIndexRequestExecutor
		createRefreshIndexRequestExecutor(
			IndexRequestShardFailureTranslator
				indexRequestShardFailureTranslator1,
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new RefreshIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indexRequestShardFailureTranslator =
					indexRequestShardFailureTranslator1;
			}
		};
	}

	protected static UpdateIndexSettingsIndexRequestExecutor
		createUpdateIndexSettingsIndexRequestExecutor(
			IndicesOptionsTranslator indicesOptionsTranslator1,
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new UpdateIndexSettingsIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indicesOptionsTranslator = indicesOptionsTranslator1;
			}
		};
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;

	private IndexRequestExecutor _indexRequestExecutor;

}