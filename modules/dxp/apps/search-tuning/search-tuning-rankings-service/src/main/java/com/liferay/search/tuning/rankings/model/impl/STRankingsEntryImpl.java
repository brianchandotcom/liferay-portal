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

package com.liferay.search.tuning.rankings.model.impl;

import com.liferay.json.storage.service.JSONStorageEntryLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bryan Engler
 */
public class STRankingsEntryImpl extends STRankingsEntryBaseImpl {

	@Override
	public List<String> getAliases() {
		return _getStrings("aliases");
	}

	@Override
	public List<String> getHiddenDocumentIds() {
		return _getStrings("hiddenDocumentIds");
	}

	@Override
	public boolean getInactive() {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(getJSON());

			return jsonObject.getBoolean("inactive");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get boolean for key: inactive");
			}

			return false;
		}
	}

	@Override
	public String getIndexName() {
		return _getString("indexName");
	}

	@Override
	public String getJSON() {
		return JSONStorageEntryLocalServiceUtil.getJSON(
			ClassNameLocalServiceUtil.getClassNameId(STRankingsEntry.class),
			getPrimaryKey());
	}

	@Override
	public String getName() {
		return _getString("name");
	}

	@Override
	public Map<Integer, String> getPinnedDocumentIds() {
		Map<Integer, String> pins = new HashMap<>();

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(getJSON());

			JSONArray jsonArray = jsonObject.getJSONArray("pins");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject1 = jsonArray.getJSONObject(i);

				pins.put(
					jsonObject1.getInt("position"),
					jsonObject1.getString("documentId"));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get map for key: pins");
			}

			return null;
		}

		return pins;
	}

	@Override
	public String getQueryString() {
		return _getString("queryString");
	}

	@Override
	public String getRankingDocumentId() {
		return _getString("rankingDocumentId");
	}

	private String _getString(String key) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(getJSON());

			return jsonObject.getString(key);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get string for key: " + key);
			}

			return null;
		}
	}

	private List<String> _getStrings(String key) {
		List<String> strings = new ArrayList<>();

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(getJSON());

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			for (int i = 0; i < jsonArray.length(); i++) {
				strings.add(jsonArray.getString(i));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get strings for key: " + key);
			}

			return null;
		}

		return strings;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		STRankingsEntryImpl.class);

}