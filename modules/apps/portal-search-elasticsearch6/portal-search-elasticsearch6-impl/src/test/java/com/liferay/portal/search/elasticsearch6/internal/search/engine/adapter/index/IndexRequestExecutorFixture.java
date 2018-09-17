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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class IndexRequestExecutorFixture {

	public IndexRequestExecutorFixture(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public IndexRequestExecutor createExecutor() {
		return new ElasticsearchIndexRequestExecutor() {
			{
				analyzeIndexRequestExecutor =
					createAnalyzeIndexRequestExecutor();
				createIndexRequestExecutor = createCreateIndexRequestExecutor();
				deleteIndexRequestExecutor = createDeleteIndexRequestExecutor();
				flushIndexRequestExecutor = createFlushIndexRequestExecutor();
				getFieldMappingIndexRequestExecutor =
					createGetFieldMappingIndexRequestExecutor();
				getIndexIndexRequestExecutor =
					createGetIndexIndexRequestExecutor();
				getMappingIndexRequestExecutor =
					createGetMappingIndexRequestExecutor();
				indicesExistsIndexRequestExecutor =
					createIndexExistsIndexRequestExecutor();
				putMappingIndexRequestExecutor =
					createPutMappingIndexRequestExecutor();
				refreshIndexRequestExecutor =
					createRefreshIndexRequestExecutor();
				updateIndexSettingsIndexRequestExecutor =
					createUpdateIndexSettingsIndexRequestExecutor();
			}
		};
	}

	protected AnalyzeIndexRequestExecutor createAnalyzeIndexRequestExecutor() {
		return new AnalyzeIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected CreateIndexRequestExecutor createCreateIndexRequestExecutor() {
		return new CreateIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected DeleteIndexRequestExecutor createDeleteIndexRequestExecutor() {
		return new DeleteIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	protected FlushIndexRequestExecutor createFlushIndexRequestExecutor() {
		return new FlushIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
				indexRequestShardFailureTranslator =
					new IndexRequestShardFailureTranslatorImpl();
			}
		};
	}

	protected GetFieldMappingIndexRequestExecutor
		createGetFieldMappingIndexRequestExecutor() {

		return new GetFieldMappingIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected GetIndexIndexRequestExecutor
		createGetIndexIndexRequestExecutor() {

		return new GetIndexIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected GetMappingIndexRequestExecutor
		createGetMappingIndexRequestExecutor() {

		return new GetMappingIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected IndicesExistsIndexRequestExecutor
		createIndexExistsIndexRequestExecutor() {

		return new IndicesExistsIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected PutMappingIndexRequestExecutor
		createPutMappingIndexRequestExecutor() {

		return new PutMappingIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected RefreshIndexRequestExecutor createRefreshIndexRequestExecutor() {
		return new RefreshIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
				indexRequestShardFailureTranslator =
					new IndexRequestShardFailureTranslatorImpl();
			}
		};
	}

	protected UpdateIndexSettingsIndexRequestExecutor
		createUpdateIndexSettingsIndexRequestExecutor() {

		return new UpdateIndexSettingsIndexRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}