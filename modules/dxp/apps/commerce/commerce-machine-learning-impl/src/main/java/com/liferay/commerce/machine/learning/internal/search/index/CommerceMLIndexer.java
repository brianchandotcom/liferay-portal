/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.search.index;

import com.liferay.commerce.machine.learning.internal.search.api.IndexNamePatterns;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;

/**
 * @author Riccardo Ferrari
 */
public class CommerceMLIndexer {

	public CommerceMLIndexer(
		String indexMappingFileName, String indexNamePattern) {

		_indexMappingFileName = indexMappingFileName;
		_indexNamePattern = indexNamePattern;
	}

	public void createIndex(
		IndexNameBuilder indexNameBuilder,
		SearchCapabilities searchCapabilities,
		SearchEngineAdapter searchEngineAdapter, long companyId) {

		if (!searchCapabilities.isCommerceSupported()) {
			return;
		}

		String indexName = IndexNamePatterns.getIndexName(
			indexNameBuilder, _indexNamePattern, companyId);

		if (_indicesExists(searchEngineAdapter, indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Index %s already exist", indexName));
			}

			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		createIndexRequest.setMappings(_readJSON(_indexMappingFileName));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		searchEngineAdapter.execute(createIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Index %s created successfully", indexName));
		}
	}

	public void dropIndex(
		IndexNameBuilder indexNameBuilder,
		SearchCapabilities searchCapabilities,
		SearchEngineAdapter searchEngineAdapter, long companyId) {

		if (!searchCapabilities.isCommerceSupported()) {
			return;
		}

		String indexName = IndexNamePatterns.getIndexName(
			indexNameBuilder, _indexNamePattern, companyId);

		if (!_indicesExists(searchEngineAdapter, indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Index %s does not exist", indexName));
			}

			return;
		}

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		searchEngineAdapter.execute(deleteIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Index %s dropped successfully", indexName));
		}
	}

	private boolean _indicesExists(
		SearchEngineAdapter searchEngineAdapter, String indexName) {

		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private String _readJSON(String fileName) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					CommerceMLIndexer.class, "/META-INF/search/" + fileName));

			return jsonObject.toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLIndexer.class);

	private final String _indexMappingFileName;
	private final String _indexNamePattern;

}