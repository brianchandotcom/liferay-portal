/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.source.formatter.check.util.JsonSourceUtil;

import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONRolesFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/roles.json")) {
			return content;
		}

		JSONArray jsonArray = null;

		try {
			jsonArray = new JSONArrayImpl(content);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return content;
		}

		List<Object> objects = JSONUtil.toObjectList(jsonArray);

		jsonArray = new JSONArrayImpl();

		for (Object object : objects) {
			JSONObject jsonObject = (JSONObject)object;

			JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

			if (actionsJSONArray != null) {
				jsonObject.put(
					"actions",
					JsonSourceUtil.sortJSONArray(
						actionsJSONArray, new ResourceComparator()));
			}

			jsonArray.put(jsonObject);
		}

		return JSONUtil.toString(jsonArray);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONRolesFileCheck.class);

	private class ResourceComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			NaturalOrderStringComparator comparator =
				new NaturalOrderStringComparator();

			JSONObject jsonObject1 = (JSONObject)object1;

			String resource1 = jsonObject1.getString("resource");

			JSONObject jsonObject2 = (JSONObject)object2;

			String resource2 = jsonObject2.getString("resource");

			if (!resource1.equals(resource2)) {
				return comparator.compare(resource1, resource2);
			}

			String actionId1 = jsonObject1.getString("actionId");
			String actionId2 = jsonObject2.getString("actionId");

			return comparator.compare(actionId1, actionId2);
		}

	}

}