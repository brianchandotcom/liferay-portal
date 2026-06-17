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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JSONSourceUtil;

import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
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
			!absolutePath.endsWith(
				"object-definition.batch-engine-data.json")) {

			return content;
		}

		try {
			JSONObject jsonObject = new JSONObjectImpl(content);

			if (absolutePath.endsWith("-object-definition.json")) {
				_sortObjectFields(jsonObject);

				return JSONUtil.toString(jsonObject);
			}
			else if (absolutePath.endsWith(
						"object-definition.batch-engine-data.json")) {

				JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

				if (itemsJSONArray == null) {
					return JSONUtil.toString(jsonObject);
				}

				List<Object> items = JSONUtil.toObjectList(itemsJSONArray);

				for (Object item : items) {
					_sortObjectFields((JSONObject)item);
				}

				return JSONUtil.toString(jsonObject);
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return content;
	}

	private void _sortObjectFields(JSONObject jsonObject) {
		JSONArray objectFieldsJSONArray = jsonObject.getJSONArray(
			"objectFields");

		if (objectFieldsJSONArray == null) {
			return;
		}

		jsonObject.put(
			"objectFields",
			JSONSourceUtil.sortJSONArray(
				objectFieldsJSONArray, new ObjectFieldComparator()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONObjectDefinitionFileCheck.class);

	private static class ObjectFieldComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			JSONObject jsonObject1 = (JSONObject)object1;
			JSONObject jsonObject2 = (JSONObject)object2;

			boolean relationship1 = _isRelationship(jsonObject1);
			boolean relationship2 = _isRelationship(jsonObject2);

			if (relationship1 != relationship2) {
				if (relationship1) {
					return 1;
				}

				return -1;
			}

			NaturalOrderStringComparator naturalOrderStringComparator =
				new NaturalOrderStringComparator();

			String name1 = jsonObject1.getString("name");
			String name2 = jsonObject2.getString("name");

			return naturalOrderStringComparator.compare(name1, name2);
		}

		private boolean _isRelationship(JSONObject jsonObject) {
			String businessType = jsonObject.getString("businessType");

			if (businessType.equals("Relationship")) {
				return true;
			}

			String name = jsonObject.getString("name");

			if (Validator.isNotNull(name) && name.startsWith("r_")) {
				return true;
			}

			return false;
		}

	}

}