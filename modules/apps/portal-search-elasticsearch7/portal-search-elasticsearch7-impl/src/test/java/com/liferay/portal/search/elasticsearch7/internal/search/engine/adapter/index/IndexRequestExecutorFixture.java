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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class IndexRequestExecutorFixture {

	public IndexRequestExecutor getIndexRequestExecutor() {
		return _indexRequestExecutor;
	}

	public void setUp() {
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator =
			new IndexRequestShardFailureTranslatorImpl();

		IndicesOptionsTranslator indicesOptionsTranslator =
			new IndicesOptionsTranslatorImpl();

		_indexRequestExecutor = new ElasticsearchIndexRequestExecutor() {
			{
				setAnalyzeIndexRequestExecutor(
					createAnalyzeIndexRequestExecutor(
						_elasticsearchClientResolver));
				setCloseIndexRequestExecutor(
					createCloseIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setCreateIndexRequestExecutor(
					createCreateIndexRequestExecutor(
						_elasticsearchClientResolver));
				setDeleteIndexRequestExecutor(
					createDeleteIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setFlushIndexRequestExecutor(
					createFlushIndexRequestExecutor(
						indexRequestShardFailureTranslator,
						_elasticsearchClientResolver));
				setGetFieldMappingIndexRequestExecutor(
					createGetFieldMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setGetIndexIndexRequestExecutor(
					createGetIndexIndexRequestExecutor(
						_elasticsearchClientResolver));
				setGetMappingIndexRequestExecutor(
					createGetMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setIndicesExistsIndexRequestExecutor(
					createIndexExistsIndexRequestExecutor(
						_elasticsearchClientResolver));
				setOpenIndexRequestExecutor(
					createOpenIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setPutMappingIndexRequestExecutor(
					createPutMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setRefreshIndexRequestExecutor(
					createRefreshIndexRequestExecutor(
						indexRequestShardFailureTranslator,
						_elasticsearchClientResolver));
				setUpdateIndexSettingsIndexRequestExecutor(
					createUpdateIndexSettingsIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
			}
		};
	}

	protected static AnalyzeIndexRequestExecutor
		createAnalyzeIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new AnalyzeIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static CloseIndexRequestExecutor createCloseIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CloseIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	protected static CreateIndexRequestExecutor
		createCreateIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CreateIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DeleteIndexRequestExecutor
		createDeleteIndexRequestExecutor(
			IndicesOptionsTranslator indicesOptionsTranslator,
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new DeleteIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	protected static FlushIndexRequestExecutor createFlushIndexRequestExecutor(
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new FlushIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndexRequestShardFailureTranslator(
					indexRequestShardFailureTranslator);
			}
		};
	}

	protected static GetFieldMappingIndexRequestExecutor
		createGetFieldMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetFieldMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static GetIndexIndexRequestExecutor
		createGetIndexIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetIndexIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static GetMappingIndexRequestExecutor
		createGetMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static IndicesExistsIndexRequestExecutor
		createIndexExistsIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new IndicesExistsIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static OpenIndexRequestExecutor createOpenIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new OpenIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	protected static PutMappingIndexRequestExecutor
		createPutMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new PutMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static RefreshIndexRequestExecutor
		createRefreshIndexRequestExecutor(
			IndexRequestShardFailureTranslator
				indexRequestShardFailureTranslator,
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new RefreshIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndexRequestShardFailureTranslator(
					indexRequestShardFailureTranslator);
			}
		};
	}

	protected static UpdateIndexSettingsIndexRequestExecutor
		createUpdateIndexSettingsIndexRequestExecutor(
			IndicesOptionsTranslator indicesOptionsTranslator,
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new UpdateIndexSettingsIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexRequestExecutor _indexRequestExecutor;

}