/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONPageFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws JSONException {

		if (!absolutePath.endsWith("page.json")) {
			return content;
		}

		JSONObject jsonObject = new JSONObjectImpl(content);

		String permissions = jsonObject.getString("permissions");

		if (Validator.isNull(permissions)) {
			return content;
		}

		JSONArray jsonArray = new JSONArrayImpl(permissions);

		List<Object> objects = JSONUtil.toObjectList(jsonArray);

		Collections.sort(objects, new PageComparator());

		jsonArray = new JSONArrayImpl();

		for (Object object : objects) {
			jsonArray.put(object);
		}

		jsonObject.put("permissions", jsonArray);

		return JSONUtil.toString(jsonObject);
	}

	private class PageComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			JSONObject jsonObject1 = (JSONObject)object1;

			String roleNam1 = jsonObject1.getString("roleName");

			JSONObject jsonObject2 = (JSONObject)object2;

			String roleName2 = jsonObject2.getString("roleName");

			return roleNam1.compareTo(roleName2);
		}

	}

}