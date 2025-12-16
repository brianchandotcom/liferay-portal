/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class IndexRequestExecutorFixture {

	public IndexRequestExecutor getIndexRequestExecutor() {
		return _indexRequestExecutor;
	}

	public void setUp() {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		_indexRequestExecutor = new ElasticsearchIndexRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_analyzeIndexRequestExecutor",
			new AnalyzeIndexRequestExecutor(_elasticsearchClientResolver));

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_closeIndexRequestExecutor",
			new CloseIndexRequestExecutor(_elasticsearchClientResolver));

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_createIndexRequestExecutor",
			new CreateIndexRequestExecutor(
				_elasticsearchClientResolver, jsonFactory));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_deleteIndexRequestExecutor",
			new DeleteIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_flushIndexRequestExecutor",
			new FlushIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getFieldMappingIndexRequestExecutor",
			new GetFieldMappingIndexRequestExecutor(
				_elasticsearchClientResolver, jsonFactory));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getIndexIndexRequestExecutor",
			new GetIndexIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getMappingIndexRequestExecutor",
			new GetMappingIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_indicesExistsIndexRequestExecutor",
			new IndicesExistsIndexRequestExecutor(
				_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_openIndexRequestExecutor",
			new OpenIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_putMappingIndexRequestExecutor",
			new PutMappingIndexRequestExecutor(
				_elasticsearchClientResolver, jsonFactory));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_refreshIndexRequestExecutor",
			new RefreshIndexRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_updateIndexSettingsIndexRequestExecutor",
			new UpdateIndexSettingsIndexRequestExecutor(
				_elasticsearchClientResolver));
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexRequestExecutor _indexRequestExecutor;

}