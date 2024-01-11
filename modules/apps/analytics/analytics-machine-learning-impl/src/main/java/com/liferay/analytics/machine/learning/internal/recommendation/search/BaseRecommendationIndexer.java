/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.search;

import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
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

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseRecommendationIndexer
	implements RecommendationIndexer {

	public BaseRecommendationIndexer(String name) {
		_name = name;
	}

	@Override
	public void createIndex(long companyId) {
		if (!searchCapabilities.isAnalyticsSupported()) {
			return;
		}

		String indexName = getIndexName(companyId);

		if (_indexExists(indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Index " + indexName + " already exist");
			}

			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		createIndexRequest.setMappings(_readJSON(_getIndexMappingFileName()));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		searchEngineAdapter.execute(createIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug("Index " + indexName + " created successfully");
		}
	}

	@Override
	public void dropIndex(long companyId) {
		if (!searchCapabilities.isAnalyticsSupported()) {
			return;
		}

		String indexName = getIndexName(companyId);

		if (!_indexExists(indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Index " + indexName + " does not exist");
			}

			return;
		}

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		searchEngineAdapter.execute(deleteIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug("Index " + indexName + " dropped successfully");
		}
	}

	@Override
	public String getIndexName(long companyId) {
		return indexNameBuilder.getIndexName(companyId) + "-" + _name;
	}

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SearchCapabilities searchCapabilities;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private String _getIndexMappingFileName() {
		return _name.concat("-mappings.json");
	}

	private boolean _indexExists(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private String _readJSON(String fileName) {
		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				StringUtil.read(getClass(), "/META-INF/search/" + fileName));

			return jsonObject.toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);

			throw new IllegalStateException("Unable to read file " + fileName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRecommendationIndexer.class);

	private final String _name;

}