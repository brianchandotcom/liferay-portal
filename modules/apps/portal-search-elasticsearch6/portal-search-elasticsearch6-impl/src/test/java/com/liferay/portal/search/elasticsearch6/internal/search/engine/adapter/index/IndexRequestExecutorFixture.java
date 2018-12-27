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

	public IndexRequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public IndexRequestExecutor createExecutor() {
		return new ElasticsearchIndexRequestExecutor() {
			{
				analyzeIndexRequestExecutor =
					createAnalyzeIndexRequestExecutor();
				closeIndexRequestExecutor = createCloseIndexRequestExecutor();
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
				openIndexRequestExecutor = createOpenIndexRequestExecutor();
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
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected CloseIndexRequestExecutor createCloseIndexRequestExecutor() {
		return new CloseIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	protected CreateIndexRequestExecutor createCreateIndexRequestExecutor() {
		return new CreateIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected DeleteIndexRequestExecutor createDeleteIndexRequestExecutor() {
		return new DeleteIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	protected FlushIndexRequestExecutor createFlushIndexRequestExecutor() {
		return new FlushIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indexRequestShardFailureTranslator =
					new IndexRequestShardFailureTranslatorImpl();
			}
		};
	}

	protected GetFieldMappingIndexRequestExecutor
		createGetFieldMappingIndexRequestExecutor() {

		return new GetFieldMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected GetIndexIndexRequestExecutor
		createGetIndexIndexRequestExecutor() {

		return new GetIndexIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected GetMappingIndexRequestExecutor
		createGetMappingIndexRequestExecutor() {

		return new GetMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected IndicesExistsIndexRequestExecutor
		createIndexExistsIndexRequestExecutor() {

		return new IndicesExistsIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected OpenIndexRequestExecutor createOpenIndexRequestExecutor() {
		return new OpenIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	protected PutMappingIndexRequestExecutor
		createPutMappingIndexRequestExecutor() {

		return new PutMappingIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected RefreshIndexRequestExecutor createRefreshIndexRequestExecutor() {
		return new RefreshIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indexRequestShardFailureTranslator =
					new IndexRequestShardFailureTranslatorImpl();
			}
		};
	}

	protected UpdateIndexSettingsIndexRequestExecutor
		createUpdateIndexSettingsIndexRequestExecutor() {

		return new UpdateIndexSettingsIndexRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				indicesOptionsTranslator = new IndicesOptionsTranslatorImpl();
			}
		};
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}