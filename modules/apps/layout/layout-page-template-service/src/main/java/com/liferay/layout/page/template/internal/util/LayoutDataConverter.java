/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.util;

import com.liferay.layout.page.template.internal.constants.LayoutDataItemTypesConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.UUID;

/**
 * @author Rubén Pulido
 */
public class LayoutDataConverter {

	public static String convert(String data) {
		if (data == null) {
			return "";
		}

		JSONObject inputDataJSONObject = null;

		try {
			inputDataJSONObject = JSONFactoryUtil.createJSONObject(data);
		}
		catch (JSONException jsone) {
			throw new RuntimeException(jsone);
		}

		int version = inputDataJSONObject.getInt("version");

		int latestVersion = 1;

		if (version == latestVersion) {
			return data;
		}

		UUID mainUUID = UUID.randomUUID();

		JSONObject mainJSONObject = JSONUtil.put(
			"children", JSONFactoryUtil.createJSONArray()
		).put(
			"config", JSONFactoryUtil.createJSONObject()
		).put(
			"itemId", mainUUID.toString()
		).put(
			"parentId", StringPool.BLANK
		).put(
			"type", LayoutDataItemTypesConstants.ITEM_TYPE_ROOT
		);

		JSONObject itemsJSONObject = JSONUtil.put(
			mainUUID.toString(), mainJSONObject);

		JSONObject outputDataJSONObject = JSONUtil.put(
			"items", itemsJSONObject
		).put(
			"rootItems", JSONUtil.put("main", mainUUID.toString())
		).put(
			"version", latestVersion
		);

		return outputDataJSONObject.toJSONString();
	}

}