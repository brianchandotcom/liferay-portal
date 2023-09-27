/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetFilterReader.class)
public class SynonymSetFilterReaderImpl implements SynonymSetFilterReader {

	@Override
	public String[] getSynonymSets(String companyIndexName, String filterName) {
		GetIndexIndexRequest getIndexIndexRequest = new GetIndexIndexRequest(
			companyIndexName);

		getIndexIndexRequest.setPreferLocalCluster(false);

		GetIndexIndexResponse getIndexIndexResponse =
			searchEngineAdapter.execute(getIndexIndexRequest);

		Map<String, String> settings = getIndexIndexResponse.getSettings();

		JSONObject jsonObject = null;

		try {
			jsonObject = jsonFactory.createJSONObject(
				settings.get(companyIndexName));
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		return JSONUtil.toStringArray(
			jsonObject.getJSONArray(
				"index.analysis.filter." + filterName + ".synonyms"));
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

}