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

package com.liferay.search.tuning.synonyms.model.impl;

import com.liferay.json.storage.service.JSONStorageEntryLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;

/**
 * @author Bryan Engler
 */
public class STSynonymsEntryImpl extends STSynonymsEntryBaseImpl {

	@Override
	public String getJSON() {
		return JSONStorageEntryLocalServiceUtil.getJSON(
			ClassNameLocalServiceUtil.getClassNameId(STSynonymsEntry.class),
			getPrimaryKey());
	}

	@Override
	public String getSynonyms() {
		return _getString("synonyms");
	}

	@Override
	public String getSynonymSetDocumentId() {
		return _getString("synonymSetDocumentId");
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

	private static final Log _log = LogFactoryUtil.getLog(
		STSynonymsEntryImpl.class);

}