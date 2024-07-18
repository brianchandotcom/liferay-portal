/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;

/**
 * @author Adam Brandizzi
 */
public class SynonymSetFilterWriterUtil {

	public static String buildSettings(
		String[] filterNames, String[] synonymSets) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String filterName : filterNames) {
			jsonObject.put(
				filterName,
				JSONUtil.put(
					"lenient", true
				).put(
					"synonyms", JSONFactoryUtil.createJSONArray(synonymSets)
				).put(
					"type", "synonym_graph"
				));
		}

		return JSONUtil.put(
			"analysis", JSONUtil.put("filter", jsonObject)
		).toString();
	}

	public static void updateSynonymSets(
		SearchEngineAdapter searchEngineAdapter, String companyIndexName,
		String[] filterNames, String[] synonymSets, boolean deletion) {

		if (ArrayUtil.isEmpty(synonymSets) && !deletion) {
			return;
		}

		_closeIndex(searchEngineAdapter, companyIndexName);

		try {
			UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
				new UpdateIndexSettingsIndexRequest(companyIndexName);

			updateIndexSettingsIndexRequest.setSettings(
				buildSettings(filterNames, synonymSets));

			searchEngineAdapter.execute(updateIndexSettingsIndexRequest);
		}
		finally {
			_openIndex(searchEngineAdapter, companyIndexName);
		}
	}

	private static void _closeIndex(
		SearchEngineAdapter searchEngineAdapter, String indexName) {

		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(indexName);

		searchEngineAdapter.execute(closeIndexRequest);
	}

	private static void _openIndex(
		SearchEngineAdapter searchEngineAdapter, String indexName) {

		OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);

		openIndexRequest.setWaitForActiveShards(1);

		searchEngineAdapter.execute(openIndexRequest);
	}

}