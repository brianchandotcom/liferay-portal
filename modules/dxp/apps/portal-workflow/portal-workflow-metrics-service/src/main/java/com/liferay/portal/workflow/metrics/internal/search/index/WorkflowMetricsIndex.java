/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsIndex {

	public static String getIndexName(
		IndexNameBuilder indexNameBuilder, String indexNameSuffix,
		long companyId) {

		return indexNameBuilder.getIndexName(companyId) + indexNameSuffix;
	}

	public WorkflowMetricsIndex(String indexNameSuffix, String indexType) {
		_indexNameSuffix = indexNameSuffix;
		_indexType = indexType;
	}

	public boolean createIndex(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException {

		if (!searchCapabilities.isWorkflowMetricsSupported() ||
			_hasIndex(
				searchEngineAdapter,
				getIndexName(indexNameBuilder, _indexNameSuffix, companyId))) {

			return false;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			getIndexName(indexNameBuilder, _indexNameSuffix, companyId));

		createIndexRequest.setMappings(
			_readJSON(_indexType + "-mappings.json"));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		try {
			searchEngineAdapter.execute(createIndexRequest);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	public boolean deleteAllDocuments(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter, Queries queries,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException {

		if (!searchCapabilities.isWorkflowMetricsSupported() ||
			!_hasIndex(
				searchEngineAdapter,
				getIndexName(indexNameBuilder, _indexNameSuffix, companyId))) {

			return false;
		}

		searchEngineAdapter.execute(
			new DeleteByQueryDocumentRequest(
				queries.matchAll(),
				getIndexName(indexNameBuilder, _indexNameSuffix, companyId)));

		return true;
	}

	public boolean removeIndex(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException {

		if (!searchCapabilities.isWorkflowMetricsSupported() ||
			!_hasIndex(
				searchEngineAdapter,
				getIndexName(indexNameBuilder, _indexNameSuffix, companyId))) {

			return false;
		}

		searchEngineAdapter.execute(
			new DeleteIndexRequest(
				getIndexName(indexNameBuilder, _indexNameSuffix, companyId)));

		return true;
	}

	private boolean _hasIndex(
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
				StringUtil.read(getClass(), "/META-INF/search/" + fileName));

			return jsonObject.toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsIndex.class);

	private final String _indexNameSuffix;
	private final String _indexType;

}