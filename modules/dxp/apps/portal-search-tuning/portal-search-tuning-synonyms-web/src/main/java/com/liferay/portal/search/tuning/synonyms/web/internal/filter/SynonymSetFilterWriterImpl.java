/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetFilterWriter.class)
public class SynonymSetFilterWriterImpl implements SynonymSetFilterWriter {

	@Override
	public void updateSynonymSets(
		String companyIndexName, String filterName, String[] synonymSets,
		boolean deletion) {

		if (ArrayUtil.isEmpty(synonymSets) && !deletion) {
			return;
		}

		_closeIndex(companyIndexName);

		try {
			UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
				new UpdateIndexSettingsIndexRequest(companyIndexName);

			updateIndexSettingsIndexRequest.setSettings(
				_buildSettings(filterName, synonymSets));

			searchEngineAdapter.execute(updateIndexSettingsIndexRequest);
		}
		finally {
			_openIndex(companyIndexName);
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private String _buildSettings(String filterName, String[] synonymSets) {
		return JSONUtil.put(
			"analysis",
			JSONUtil.put(
				"filter",
				JSONUtil.put(
					filterName,
					JSONUtil.put(
						"lenient", true
					).put(
						"synonyms", jsonFactory.createJSONArray(synonymSets)
					).put(
						"type", "synonym_graph"
					)))
		).toString();
	}

	private void _closeIndex(String indexName) {
		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(indexName);

		searchEngineAdapter.execute(closeIndexRequest);
	}

	private void _openIndex(String indexName) {
		OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);

		openIndexRequest.setWaitForActiveShards(1);

		searchEngineAdapter.execute(openIndexRequest);
	}

}