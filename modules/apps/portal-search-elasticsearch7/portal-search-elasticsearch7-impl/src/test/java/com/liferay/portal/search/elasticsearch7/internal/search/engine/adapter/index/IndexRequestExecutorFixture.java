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
					_createAnalyzeIndexRequestExecutor(
						_elasticsearchClientResolver));
				setCloseIndexRequestExecutor(
					_createCloseIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setCreateIndexRequestExecutor(
					_createCreateIndexRequestExecutor(
						_elasticsearchClientResolver));
				setDeleteIndexRequestExecutor(
					_createDeleteIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setFlushIndexRequestExecutor(
					_createFlushIndexRequestExecutor(
						indexRequestShardFailureTranslator,
						_elasticsearchClientResolver));
				setGetFieldMappingIndexRequestExecutor(
					_createGetFieldMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setGetIndexIndexRequestExecutor(
					_createGetIndexIndexRequestExecutor(
						_elasticsearchClientResolver));
				setGetMappingIndexRequestExecutor(
					_createGetMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setIndicesExistsIndexRequestExecutor(
					_createIndexExistsIndexRequestExecutor(
						_elasticsearchClientResolver));
				setOpenIndexRequestExecutor(
					_createOpenIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
				setPutMappingIndexRequestExecutor(
					_createPutMappingIndexRequestExecutor(
						_elasticsearchClientResolver));
				setRefreshIndexRequestExecutor(
					_createRefreshIndexRequestExecutor(
						indexRequestShardFailureTranslator,
						_elasticsearchClientResolver));
				setUpdateIndexSettingsIndexRequestExecutor(
					_createUpdateIndexSettingsIndexRequestExecutor(
						indicesOptionsTranslator,
						_elasticsearchClientResolver));
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static AnalyzeIndexRequestExecutor
		_createAnalyzeIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new AnalyzeIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static CloseIndexRequestExecutor _createCloseIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CloseIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	private static CreateIndexRequestExecutor _createCreateIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CreateIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static DeleteIndexRequestExecutor _createDeleteIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new DeleteIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	private static FlushIndexRequestExecutor _createFlushIndexRequestExecutor(
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

	private static GetFieldMappingIndexRequestExecutor
		_createGetFieldMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetFieldMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static GetIndexIndexRequestExecutor
		_createGetIndexIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetIndexIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static GetMappingIndexRequestExecutor
		_createGetMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static IndicesExistsIndexRequestExecutor
		_createIndexExistsIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new IndicesExistsIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static OpenIndexRequestExecutor _createOpenIndexRequestExecutor(
		IndicesOptionsTranslator indicesOptionsTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new OpenIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	private static PutMappingIndexRequestExecutor
		_createPutMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new PutMappingIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static RefreshIndexRequestExecutor
		_createRefreshIndexRequestExecutor(
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

	private static UpdateIndexSettingsIndexRequestExecutor
		_createUpdateIndexSettingsIndexRequestExecutor(
			IndicesOptionsTranslator indicesOptionsTranslator,
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new UpdateIndexSettingsIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndicesOptionsTranslator(indicesOptionsTranslator);
			}
		};
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexRequestExecutor _indexRequestExecutor;

}