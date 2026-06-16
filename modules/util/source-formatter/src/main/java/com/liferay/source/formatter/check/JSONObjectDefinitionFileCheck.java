/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.source.formatter.check.util.JSONSourceUtil;

import java.util.Comparator;

/**
 * @author Brian Chan
 */
public class JSONObjectDefinitionFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("-object-definition.json") &&
			!absolutePath.endsWith("object-definition.batch-engine-data.json")) {

			return content;
		}

		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObjectImpl(content);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return content;
		}

		_sortObjectFields(jsonObject);

		return JSONUtil.toString(jsonObject);
	}

	private void _sortObjectFields(JSONObject jsonObject) {
		JSONArray objectFieldsJSONArray = jsonObject.getJSONArray(
			"objectFields");

		if (objectFieldsJSONArray != null) {
			jsonObject.put(
				"objectFields",
				JSONSourceUtil.sortJSONArray(
					objectFieldsJSONArray, new ObjectFieldComparator()));
		}

		for (String key : jsonObject.keySet()) {
			Object value = jsonObject.get(key);

			if (value instanceof JSONObject) {
				_sortObjectFields((JSONObject)value);
			}
			else if (value instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray)value;

				for (int i = 0; i < jsonArray.length(); i++) {
					Object object = jsonArray.get(i);

					if (object instanceof JSONObject) {
						_sortObjectFields((JSONObject)object);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONObjectDefinitionFileCheck.class);

	private class ObjectFieldComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			JSONObject jsonObject1 = (JSONObject)object1;
			JSONObject jsonObject2 = (JSONObject)object2;

			boolean relationship1 = _isRelationship(jsonObject1);
			boolean relationship2 = _isRelationship(jsonObject2);

			if (relationship1 != relationship2) {
				return relationship1 ? 1 : -1;
			}

			return _comparator.compare(
				jsonObject1.getString("name"), jsonObject2.getString("name"));
		}

		private boolean _isRelationship(JSONObject jsonObject) {
			if ("Relationship".equals(jsonObject.getString("businessType"))) {
				return true;
			}

			String name = jsonObject.getString("name");

			return name.startsWith("r_");
		}

		private final NaturalOrderStringComparator _comparator =
			new NaturalOrderStringComparator();

	}

}
