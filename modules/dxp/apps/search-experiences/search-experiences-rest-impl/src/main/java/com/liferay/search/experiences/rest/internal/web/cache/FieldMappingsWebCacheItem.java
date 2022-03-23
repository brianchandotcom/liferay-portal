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

package com.liferay.search.experiences.rest.internal.web.cache;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.search.index.IndexInformation;

/**
 * @author Petteri Karttunen
 */
public class FieldMappingsWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		IndexInformation indexInformation, String indexName) {

		return (JSONObject)WebCachePoolUtil.get(
			FieldMappingsWebCacheItem.class.getName() + StringPool.POUND +
				indexName,
			new FieldMappingsWebCacheItem(indexInformation, indexName));
	}

	public FieldMappingsWebCacheItem(
		IndexInformation indexInformation, String indexName) {

		_indexInformation = indexInformation;
		_indexName = indexName;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			return JSONUtil.getValueAsJSONObject(
				JSONFactoryUtil.createJSONObject(
					_indexInformation.getFieldMappings(_indexName)),
				"JSONObject/" + _indexName, "JSONObject/mappings",
				"JSONObject/properties");
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return JSONFactoryUtil.createJSONObject();
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 30;

	private static final Log _log = LogFactoryUtil.getLog(
		FieldMappingsWebCacheItem.class);

	private final IndexInformation _indexInformation;
	private final String _indexName;

}