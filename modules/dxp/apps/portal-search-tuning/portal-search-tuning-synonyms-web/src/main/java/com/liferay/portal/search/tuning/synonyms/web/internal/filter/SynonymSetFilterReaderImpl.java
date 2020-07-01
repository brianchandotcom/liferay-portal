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
@Component(immediate = true, service = SynonymSetFilterReader.class)
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