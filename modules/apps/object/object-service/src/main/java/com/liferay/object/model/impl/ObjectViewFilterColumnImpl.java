/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marco Leo
 */
public class ObjectViewFilterColumnImpl extends ObjectViewFilterColumnBaseImpl {

	@Override
	public JSONArray getJSONArray() throws JSONException {
		if (StringUtil.equals(getFilterType(), StringPool.BLANK)) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(getJSON());

		return jsonObject.getJSONArray(getFilterType());
	}

}