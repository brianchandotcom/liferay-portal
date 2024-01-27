/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

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

		JSONFactory jsonFactory = new JSONFactoryImpl();

		_indexRequestExecutor = new OpenSearchIndexRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_analyzeIndexRequestExecutor",
			_createAnalyzeIndexRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_closeIndexRequestExecutor",
			_createCloseIndexRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_createIndexRequestExecutor",
			_createCreateIndexRequestExecutor(
				jsonFactory, _openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_deleteIndexRequestExecutor",
			_createDeleteIndexRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_flushIndexRequestExecutor",
			_createFlushIndexRequestExecutor(
				_openSearchConnectionManager,
				indexRequestShardFailureTranslator));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getFieldMappingIndexRequestExecutor",
			_createGetFieldMappingIndexRequestExecutor(
				jsonFactory, _openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getIndexIndexRequestExecutor",
			_createGetIndexIndexRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_getMappingIndexRequestExecutor",
			_createGetMappingIndexRequestExecutor(
				_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_indicesExistsIndexRequestExecutor",
			_createIndexExistsIndexRequestExecutor(
				_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_openIndexRequestExecutor",
			_createOpenIndexRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_putMappingIndexRequestExecutor",
			_createPutMappingIndexRequestExecutor(
				jsonFactory, _openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_refreshIndexRequestExecutor",
			_createRefreshIndexRequestExecutor(
				_openSearchConnectionManager,
				indexRequestShardFailureTranslator));
		ReflectionTestUtil.setFieldValue(
			_indexRequestExecutor, "_updateIndexSettingsIndexRequestExecutor",
			_createUpdateIndexSettingsIndexRequestExecutor(
				_openSearchConnectionManager));
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private AnalyzeIndexRequestExecutor _createAnalyzeIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		AnalyzeIndexRequestExecutor analyzeIndexRequestExecutor =
			new AnalyzeIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			analyzeIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return analyzeIndexRequestExecutor;
	}

	private CloseIndexRequestExecutor _createCloseIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		CloseIndexRequestExecutor closeIndexRequestExecutor =
			new CloseIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			closeIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return closeIndexRequestExecutor;
	}

	private CreateIndexRequestExecutor _createCreateIndexRequestExecutor(
		JSONFactory jsonFactory,
		OpenSearchConnectionManager openSearchConnectionManager) {

		CreateIndexRequestExecutor createIndexRequestExecutor =
			new CreateIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutor, "_jsonFactory", jsonFactory);
		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return createIndexRequestExecutor;
	}

	private DeleteIndexRequestExecutor _createDeleteIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		DeleteIndexRequestExecutor deleteIndexRequestExecutor =
			new DeleteIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return deleteIndexRequestExecutor;
	}

	private FlushIndexRequestExecutor _createFlushIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator) {

		FlushIndexRequestExecutor flushIndexRequestExecutor =
			new FlushIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			flushIndexRequestExecutor, "_indexRequestShardFailureTranslator",
			indexRequestShardFailureTranslator);
		ReflectionTestUtil.setFieldValue(
			flushIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return flushIndexRequestExecutor;
	}

	private GetFieldMappingIndexRequestExecutor
		_createGetFieldMappingIndexRequestExecutor(
			JSONFactory jsonFactory,
			OpenSearchConnectionManager openSearchConnectionManager) {

		GetFieldMappingIndexRequestExecutor
			getFieldMappingIndexRequestExecutor =
				new GetFieldMappingIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getFieldMappingIndexRequestExecutor, "_jsonFactory", jsonFactory);
		ReflectionTestUtil.setFieldValue(
			getFieldMappingIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return getFieldMappingIndexRequestExecutor;
	}

	private GetIndexIndexRequestExecutor _createGetIndexIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		GetIndexIndexRequestExecutor getIndexIndexRequestExecutor =
			new GetIndexIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getIndexIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return getIndexIndexRequestExecutor;
	}

	private GetMappingIndexRequestExecutor
		_createGetMappingIndexRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		GetMappingIndexRequestExecutor getMappingIndexRequestExecutor =
			new GetMappingIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getMappingIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return getMappingIndexRequestExecutor;
	}

	private IndicesExistsIndexRequestExecutor
		_createIndexExistsIndexRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		IndicesExistsIndexRequestExecutor indicesExistsIndexRequestExecutor =
			new IndicesExistsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			indicesExistsIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return indicesExistsIndexRequestExecutor;
	}

	private OpenIndexRequestExecutor _createOpenIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		OpenIndexRequestExecutor openIndexRequestExecutor =
			new OpenIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			openIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return openIndexRequestExecutor;
	}

	private PutMappingIndexRequestExecutor
		_createPutMappingIndexRequestExecutor(
			JSONFactory jsonFactory,
			OpenSearchConnectionManager openSearchConnectionManager) {

		PutMappingIndexRequestExecutor putMappingIndexRequestExecutor =
			new PutMappingIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			putMappingIndexRequestExecutor, "_jsonFactory", jsonFactory);

		ReflectionTestUtil.setFieldValue(
			putMappingIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return putMappingIndexRequestExecutor;
	}

	private RefreshIndexRequestExecutor _createRefreshIndexRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator) {

		RefreshIndexRequestExecutor refreshIndexRequestExecutor =
			new RefreshIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			refreshIndexRequestExecutor, "_indexRequestShardFailureTranslator",
			indexRequestShardFailureTranslator);
		ReflectionTestUtil.setFieldValue(
			refreshIndexRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return refreshIndexRequestExecutor;
	}

	private UpdateIndexSettingsIndexRequestExecutor
		_createUpdateIndexSettingsIndexRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		UpdateIndexSettingsIndexRequestExecutor
			updateIndexSettingsIndexRequestExecutor =
				new UpdateIndexSettingsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateIndexSettingsIndexRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		return updateIndexSettingsIndexRequestExecutor;
	}

	private IndexRequestExecutor _indexRequestExecutor;
	private OpenSearchConnectionManager _openSearchConnectionManager;

}