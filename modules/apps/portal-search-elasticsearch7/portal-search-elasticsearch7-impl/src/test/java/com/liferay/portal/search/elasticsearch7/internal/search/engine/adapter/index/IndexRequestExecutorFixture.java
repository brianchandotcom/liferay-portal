/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
		_indexRequestExecutor = new ElasticsearchIndexRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_analyzeIndexRequestExecutor",
			new AnalyzeIndexRequestExecutor(_elasticsearchClientResolver));

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_closeIndexRequestExecutor",
			_createCloseIndexRequestExecutor(_elasticsearchClientResolver));

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_createIndexRequestExecutor",
			_createCreateIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_deleteIndexRequestExecutor",
			new DeleteIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_flushIndexRequestExecutor",
			_createFlushIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getFieldMappingIndexRequestExecutor",
			_createGetFieldMappingIndexRequestExecutor(
				_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getIndexIndexRequestExecutor",
			_createGetIndexIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getMappingIndexRequestExecutor",
			new GetMappingIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_indicesExistsIndexRequestExecutor",
			_createIndexExistsIndexRequestExecutor(
				_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_openIndexRequestExecutor",
			new OpenIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_putMappingIndexRequestExecutor",
			_createPutMappingIndexRequestExecutor(
				_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_refreshIndexRequestExecutor",
			_createRefreshIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_updateIndexSettingsIndexRequestExecutor",
			_createUpdateIndexSettingsIndexRequestExecutor(
				_elasticsearchClientResolver));
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private CloseIndexRequestExecutor _createCloseIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CloseIndexRequestExecutor closeIndexRequestExecutor =
			new CloseIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			closeIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return closeIndexRequestExecutor;
	}

	private CreateIndexRequestExecutor _createCreateIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CreateIndexRequestExecutor createIndexRequestExecutor =
			new CreateIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return createIndexRequestExecutor;
	}

	private FlushIndexRequestExecutor _createFlushIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		FlushIndexRequestExecutor flushIndexRequestExecutor =
			new FlushIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			flushIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return flushIndexRequestExecutor;
	}

	private GetFieldMappingIndexRequestExecutor
		_createGetFieldMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		GetFieldMappingIndexRequestExecutor
			getFieldMappingIndexRequestExecutor =
				new GetFieldMappingIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getFieldMappingIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		ReflectionTestUtil.setFieldValue(
			getFieldMappingIndexRequestExecutor, "_jsonFactory",
			new JSONFactoryImpl());

		return getFieldMappingIndexRequestExecutor;
	}

	private GetIndexIndexRequestExecutor _createGetIndexIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		GetIndexIndexRequestExecutor getIndexIndexRequestExecutor =
			new GetIndexIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getIndexIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return getIndexIndexRequestExecutor;
	}

	private IndicesExistsIndexRequestExecutor
		_createIndexExistsIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		IndicesExistsIndexRequestExecutor indicesExistsIndexRequestExecutor =
			new IndicesExistsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			indicesExistsIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return indicesExistsIndexRequestExecutor;
	}

	private PutMappingIndexRequestExecutor
		_createPutMappingIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		PutMappingIndexRequestExecutor putMappingIndexRequestExecutor =
			new PutMappingIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			putMappingIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return putMappingIndexRequestExecutor;
	}

	private RefreshIndexRequestExecutor _createRefreshIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		RefreshIndexRequestExecutor refreshIndexRequestExecutor =
			new RefreshIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			refreshIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return refreshIndexRequestExecutor;
	}

	private UpdateIndexSettingsIndexRequestExecutor
		_createUpdateIndexSettingsIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		UpdateIndexSettingsIndexRequestExecutor
			updateIndexSettingsIndexRequestExecutor =
				new UpdateIndexSettingsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateIndexSettingsIndexRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		return updateIndexSettingsIndexRequestExecutor;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexRequestExecutor _indexRequestExecutor;

}