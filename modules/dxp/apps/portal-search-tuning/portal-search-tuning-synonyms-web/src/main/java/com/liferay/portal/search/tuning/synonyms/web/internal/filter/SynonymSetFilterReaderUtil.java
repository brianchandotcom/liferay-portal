/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;

import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class SynonymSetFilterReaderUtil {

	public static String[] getSynonymSets(
		SearchEngineAdapter searchEngineAdapter, String companyIndexName,
		String filterName) {

		GetIndexIndexRequest getIndexIndexRequest = new GetIndexIndexRequest(
			companyIndexName);

		getIndexIndexRequest.setPreferLocalCluster(false);

		GetIndexIndexResponse getIndexIndexResponse =
			searchEngineAdapter.execute(getIndexIndexRequest);

		Map<String, String> settings = getIndexIndexResponse.getSettings();

		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(
				settings.get(companyIndexName));
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		return JSONUtil.toStringArray(
			jsonObject.getJSONArray(
				"index.analysis.filter." + filterName + ".synonyms"));
	}

}