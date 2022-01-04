/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
@Component(immediate = true, service = SynonymSetFilterWriter.class)
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