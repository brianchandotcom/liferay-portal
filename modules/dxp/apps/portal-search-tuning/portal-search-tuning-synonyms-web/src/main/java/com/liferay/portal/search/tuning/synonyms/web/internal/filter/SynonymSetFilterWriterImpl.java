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
		String companyIndexName, String filterName, String[] synonymSets) {

		closeIndex(companyIndexName);

		try {
			UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
				new UpdateIndexSettingsIndexRequest(companyIndexName);

			String settings = buildSettings(filterName, synonymSets);

			updateIndexSettingsIndexRequest.setSettings(settings);

			searchEngineAdapter.execute(updateIndexSettingsIndexRequest);
		}
		finally {
			openIndex(companyIndexName);
		}
	}

	protected String buildSettings(String filterName, String[] synonymSets) {
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

	protected void closeIndex(String indexName) {
		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(indexName);

		searchEngineAdapter.execute(closeIndexRequest);
	}

	protected void openIndex(String indexName) {
		OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);

		openIndexRequest.setWaitForActiveShards(1);

		searchEngineAdapter.execute(openIndexRequest);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

}