/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 * @author Joshua Cords
 * @author Tibor Lipusz
 */
@Component(service = SynonymSetIndexCreator.class)
public class SynonymSetIndexCreatorImpl implements SynonymSetIndexCreator {

	@Override
	public void create(SynonymSetIndexName synonymSetIndexName) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			synonymSetIndexName.getIndexName());

		createIndexRequest.setMappings(_readJSON(_INDEX_MAPPINGS_FILE_NAME));
		createIndexRequest.setSettings(_readJSON(_INDEX_SETTINGS_FILE_NAME));

		_searchEngineAdapter.execute(createIndexRequest);
	}

	@Override
	public void delete(SynonymSetIndexName synonymSetIndexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(synonymSetIndexName.getIndexName());

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		if (indicesExistsIndexResponse.isExists()) {
			DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
				synonymSetIndexName.getIndexName());

			_searchEngineAdapter.execute(deleteIndexRequest);
		}
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

	private static final String _INDEX_MAPPINGS_FILE_NAME =
		"liferay-search-tuning-synonyms-mappings.json";

	private static final String _INDEX_SETTINGS_FILE_NAME =
		"liferay-search-tuning-synonyms-settings.json";

	private static final Log _log = LogFactoryUtil.getLog(
		SynonymSetIndexCreatorImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}