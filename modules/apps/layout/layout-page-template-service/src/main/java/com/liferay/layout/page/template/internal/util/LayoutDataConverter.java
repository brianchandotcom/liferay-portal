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

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.layout.page.template.internal.constants.LayoutDataItemTypesConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
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

		JSONObject itemsJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray mainChildrenJSONArray = JSONFactoryUtil.createJSONArray();

		UUID mainUUID = UUID.randomUUID();

		JSONArray structureJSONArray = inputDataJSONObject.getJSONArray(
			"structure");

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject inputRowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = inputRowJSONObject.getJSONArray(
				"columns");

			String type = inputRowJSONObject.getString("type");

			if (type.equals(String.valueOf(FragmentConstants.TYPE_COMPONENT))) {
				UUID containerUUID = UUID.randomUUID();

				mainChildrenJSONArray.put(containerUUID);

				UUID rowUUID = UUID.randomUUID();

				JSONArray rowChildrenJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (int j = 0; j < columnsJSONArray.length(); j++) {
					JSONObject inputColumnJSONObject =
						columnsJSONArray.getJSONObject(j);

					UUID columnUUID = UUID.randomUUID();

					rowChildrenJSONArray.put(columnUUID.toString());

					JSONArray columnChildrenJSONArray =
						JSONFactoryUtil.createJSONArray();

					JSONArray fragmentEntryLinksJSONArray =
						inputColumnJSONObject.getJSONArray(
							"fragmentEntryLinkIds");

					for (int k = 0; k < fragmentEntryLinksJSONArray.length();
						 k++) {

						String fragmentEntryLinkId =
							fragmentEntryLinksJSONArray.getString(k);

						UUID fragmentUUID = UUID.randomUUID();

						columnChildrenJSONArray.put(fragmentUUID.toString());

						JSONObject fragmentConfigJSONObject = JSONUtil.put(
							"fragmentEntryLinkId", fragmentEntryLinkId);

						JSONObject fragmentJSONObject = _getItemJSONObject(
							JSONFactoryUtil.createJSONArray(),
							fragmentConfigJSONObject, fragmentUUID.toString(),
							columnUUID.toString(),
							LayoutDataItemTypesConstants.ITEM_TYPE_FRAGMENT);

						itemsJSONObject.put(
							fragmentUUID.toString(), fragmentJSONObject);
					}

					JSONObject columnConfigJSONObject = JSONUtil.put(
						"size",
						Integer.valueOf(
							inputColumnJSONObject.getString("size")));

					JSONObject columnJSONObject = _getItemJSONObject(
						columnChildrenJSONArray, columnConfigJSONObject,
						columnUUID.toString(), rowUUID.toString(),
						LayoutDataItemTypesConstants.ITEM_TYPE_COLUMN);

					itemsJSONObject.put(
						columnUUID.toString(), columnJSONObject);
				}

				JSONObject inputRowConfigJSONObject =
					inputRowJSONObject.getJSONObject("config");

				JSONObject rowConfigJSONObject = JSONUtil.put(
					"gutters", inputRowConfigJSONObject.get("columnSpacing")
				).put(
					"verticalAlign", "top"
				);

				JSONObject rowJSONObject = _getItemJSONObject(
					rowChildrenJSONArray, rowConfigJSONObject,
					rowUUID.toString(), containerUUID.toString(),
					LayoutDataItemTypesConstants.ITEM_TYPE_ROW);

				itemsJSONObject.put(rowUUID.toString(), rowJSONObject);

				JSONObject containerConfigJSONObject = JSONUtil.put(
					"backgroundColorCssClass",
					inputRowConfigJSONObject.get("backgroundColorCssClass")
				).put(
					"backgroundImage",
					inputRowConfigJSONObject.get("backgroundImage")
				).put(
					"paddingHorizontal",
					Integer.valueOf(
						inputRowConfigJSONObject.getString("paddingHorizontal"))
				).put(
					"paddingVertical",
					Integer.valueOf(
						inputRowConfigJSONObject.getString("paddingVertical"))
				).put(
					"type",
					inputRowConfigJSONObject.getString("containerType", null)
				);

				JSONObject containerJSONObject = _getItemJSONObject(
					JSONUtil.put(rowUUID.toString()), containerConfigJSONObject,
					containerUUID.toString(), mainUUID.toString(),
					LayoutDataItemTypesConstants.ITEM_TYPE_CONTAINER);

				itemsJSONObject.put(
					containerUUID.toString(), containerJSONObject);
			}
			else {
				UUID fragmentUUID = UUID.randomUUID();

				mainChildrenJSONArray.put(fragmentUUID.toString());

				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(0);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				JSONObject fragmentConfigJSONObject = JSONUtil.put(
					"fragmentEntryLinkId",
					fragmentEntryLinkIdsJSONArray.get(0));

				JSONObject fragmentJSONObject = _getItemJSONObject(
					JSONFactoryUtil.createJSONArray(), fragmentConfigJSONObject,
					fragmentUUID.toString(), mainUUID.toString(),
					LayoutDataItemTypesConstants.ITEM_TYPE_FRAGMENT);

				itemsJSONObject.put(
					fragmentUUID.toString(), fragmentJSONObject);
			}
		}

		JSONObject mainJSONObject = _getItemJSONObject(
			mainChildrenJSONArray, JSONFactoryUtil.createJSONObject(),
			mainUUID.toString(), StringPool.BLANK,
			LayoutDataItemTypesConstants.ITEM_TYPE_ROOT);

		itemsJSONObject.put(mainUUID.toString(), mainJSONObject);

		JSONObject outputDataJSONObject = JSONUtil.put(
			"items", itemsJSONObject
		).put(
			"rootItems", JSONUtil.put("main", mainUUID.toString())
		).put(
			"version", latestVersion
		);

		return outputDataJSONObject.toJSONString();
	}

	private static JSONObject _getItemJSONObject(
		JSONArray childrenJSONArray, JSONObject configJSONObject, String itemId,
		String parentId, String type) {

		return JSONUtil.put(
			"children", childrenJSONArray
		).put(
			"config", configJSONObject
		).put(
			"itemId", itemId
		).put(
			"parentId", parentId
		).put(
			"type", type
		);
	}

}