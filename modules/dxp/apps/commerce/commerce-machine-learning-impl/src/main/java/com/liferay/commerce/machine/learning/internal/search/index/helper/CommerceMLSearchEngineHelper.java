/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.search.index.helper;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = CommerceMLSearchEngineHelper.class)
public class CommerceMLSearchEngineHelper {

	public void createIndex(String indexName, String indexMappingFileName) {
		if (!_searchCapabilities.isCommerceSupported()) {
			return;
		}

		if (_indicesExists(indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Index %s already exist", indexName));
			}

			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		createIndexRequest.setMappings(_readJSON(indexMappingFileName));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		searchEngineAdapter.execute(createIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Index %s created successfully", indexName));
		}
	}

	public void dropIndex(String indexName) {
		if (!_searchCapabilities.isCommerceSupported()) {
			return;
		}

		if (!_indicesExists(indexName)) {
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

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private boolean _indicesExists(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private String _readJSON(String fileName) {
		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(getClass(), "/META-INF/search/" + fileName));

			return jsonObject.toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLSearchEngineHelper.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SearchCapabilities _searchCapabilities;

}