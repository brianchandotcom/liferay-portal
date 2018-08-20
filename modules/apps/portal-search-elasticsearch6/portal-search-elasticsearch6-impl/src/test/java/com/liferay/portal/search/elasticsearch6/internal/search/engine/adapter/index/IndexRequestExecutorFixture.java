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
				flushIndexRequestExecutor = createFlushIndexRequestExecutor();
				getFieldMappingIndexRequestExecutor =
					createGetFieldMappingIndexRequestExecutor();
				getMappingIndexRequestExecutor =
					createGetMappingIndexRequestExecutor();
				putMappingIndexRequestExecutor =
					createPutMappingIndexRequestExecutor();
				refreshIndexRequestExecutor =
					createRefreshIndexRequestExecutor();
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

	protected GetMappingIndexRequestExecutor
		createGetMappingIndexRequestExecutor() {

		return new GetMappingIndexRequestExecutorImpl() {
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

	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}