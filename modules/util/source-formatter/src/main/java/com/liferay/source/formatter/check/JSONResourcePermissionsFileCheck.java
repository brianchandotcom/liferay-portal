/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.source.formatter.check.util.JsonSourceUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONResourcePermissionsFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws JSONException {

		if (!absolutePath.endsWith("resource-permissions.json")) {
			return content;
		}

		JSONArray jsonArray = new JSONArrayImpl(content);

		List<Object> objects = JSONUtil.toObjectList(jsonArray);

		Collections.sort(objects, new ResourceNameComparator());

		jsonArray = new JSONArrayImpl();

		for (Object object : objects) {
			JSONObject jsonObject = (JSONObject)object;

			JSONArray actionIdsJSONArray = jsonObject.getJSONArray("actionIds");

			if (actionIdsJSONArray != null) {
				jsonObject.put(
					"actionIds",
					JsonSourceUtil.sortJSONArray(
						actionIdsJSONArray, new ActionIdComparator()));
			}

			jsonArray.put(jsonObject);
		}

		return JSONUtil.toString(jsonArray);
	}

	private class ActionIdComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			String actionId1 = object1.toString();
			String actionId2 = object2.toString();

			return actionId1.compareTo(actionId2);
		}

	}

	private class ResourceNameComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			JSONObject jsonObject1 = (JSONObject)object1;

			String resourceName1 = jsonObject1.getString("resourceName");

			JSONObject jsonObject2 = (JSONObject)object2;

			String resourceName2 = jsonObject2.getString("resourceName");

			if (!resourceName1.equals(resourceName2)) {
				return resourceName1.compareTo(resourceName2);
			}

			String roleName1 = jsonObject1.getString("roleName");
			String roleName2 = jsonObject2.getString("roleName");

			return roleName1.compareTo(roleName2);
		}

	}

}