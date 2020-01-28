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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.UUID;

/**
 * @author Rubén Pulido
 */
public class LayoutDataConverter {

	public static final int LATEST_VERSION = 1;

	public static String convert(String data) {
		if (Validator.isNull(data)) {
			return StringPool.BLANK;
		}

		JSONObject inputDataJSONObject = null;

		try {
			inputDataJSONObject = JSONFactoryUtil.createJSONObject(data);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		if (isLatestVersion(inputDataJSONObject)) {
			return data;
		}

		JSONArray structureJSONArray = inputDataJSONObject.getJSONArray(
			"structure");

		if (structureJSONArray == null) {
			return data;
		}

		JSONObject itemsJSONObject = JSONFactoryUtil.createJSONObject();
		JSONArray mainChildrenJSONArray = JSONFactoryUtil.createJSONArray();

		UUID dropZoneUUID = null;
		UUID mainUUID = UUID.randomUUID();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject inputRowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = inputRowJSONObject.getJSONArray(
				"columns");

			if (inputRowJSONObject.getInt("type") ==
					FragmentConstants.TYPE_COMPONENT) {

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

						JSONObject fragmentJSONObject = null;

						if (fragmentEntryLinkId.equals(
								LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

							dropZoneUUID = fragmentUUID;

							fragmentJSONObject = _getItemJSONObject(
								JSONFactoryUtil.createJSONArray(),
								JSONFactoryUtil.createJSONObject(),
								dropZoneUUID.toString(), columnUUID.toString(),
								LayoutDataItemTypeConstants.TYPE_DROP_ZONE);
						}
						else {
							fragmentJSONObject = _getItemJSONObject(
								JSONFactoryUtil.createJSONArray(),
								fragmentConfigJSONObject,
								fragmentUUID.toString(), columnUUID.toString(),
								LayoutDataItemTypeConstants.TYPE_FRAGMENT);
						}

						itemsJSONObject.put(
							fragmentUUID.toString(), fragmentJSONObject);
					}

					JSONObject columnConfigJSONObject = JSONUtil.put(
						"size", inputColumnJSONObject.getInt("size"));

					JSONObject columnJSONObject = _getItemJSONObject(
						columnChildrenJSONArray, columnConfigJSONObject,
						columnUUID.toString(), rowUUID.toString(),
						LayoutDataItemTypeConstants.TYPE_COLUMN);

					itemsJSONObject.put(
						columnUUID.toString(), columnJSONObject);
				}

				JSONObject inputRowConfigJSONObject =
					inputRowJSONObject.getJSONObject("config");

				JSONObject rowConfigJSONObject = JSONUtil.put(
					"gutters",
					inputRowConfigJSONObject.getBoolean("columnSpacing")
				).put(
					"verticalAlign", "top"
				);

				JSONObject rowJSONObject = _getItemJSONObject(
					rowChildrenJSONArray, rowConfigJSONObject,
					rowUUID.toString(), containerUUID.toString(),
					LayoutDataItemTypeConstants.TYPE_ROW);

				itemsJSONObject.put(rowUUID.toString(), rowJSONObject);

				JSONObject containerConfigJSONObject = JSONUtil.put(
					"backgroundColorCssClass",
					inputRowConfigJSONObject.getString(
						"backgroundColorCssClass", null)
				).put(
					"backgroundImage",
					_getBackgroundImageJSONObject(inputRowConfigJSONObject)
				).put(
					"paddingBottom",
					inputRowConfigJSONObject.getInt("paddingVertical")
				).put(
					"paddingHorizontal",
					inputRowConfigJSONObject.getInt("paddingHorizontal")
				).put(
					"paddingTop",
					inputRowConfigJSONObject.getInt("paddingVertical")
				).put(
					"type",
					inputRowConfigJSONObject.getString("containerType", null)
				);

				JSONObject containerJSONObject = _getItemJSONObject(
					JSONUtil.put(rowUUID.toString()), containerConfigJSONObject,
					containerUUID.toString(), mainUUID.toString(),
					LayoutDataItemTypeConstants.TYPE_CONTAINER);

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
					LayoutDataItemTypeConstants.TYPE_FRAGMENT);

				itemsJSONObject.put(
					fragmentUUID.toString(), fragmentJSONObject);
			}
		}

		JSONObject mainJSONObject = _getItemJSONObject(
			mainChildrenJSONArray, JSONFactoryUtil.createJSONObject(),
			mainUUID.toString(), StringPool.BLANK,
			LayoutDataItemTypeConstants.TYPE_ROOT);

		itemsJSONObject.put(mainUUID.toString(), mainJSONObject);

		JSONObject rootItemsJSONObject = JSONUtil.put(
			"main", mainUUID.toString());

		if (dropZoneUUID != null) {
			rootItemsJSONObject.put("dropZone", dropZoneUUID.toString());
		}

		JSONObject outputDataJSONObject = JSONUtil.put(
			"items", itemsJSONObject
		).put(
			"rootItems", rootItemsJSONObject
		).put(
			"version", LATEST_VERSION
		);

		return outputDataJSONObject.toJSONString();
	}

	public static boolean isLatestVersion(JSONObject dataJSONObject) {
		int version = dataJSONObject.getInt("version");

		if (version == LATEST_VERSION) {
			return true;
		}

		return false;
	}

	private static JSONObject _getBackgroundImageJSONObject(
		JSONObject inputRowConfigJSONObject) {

		Object backgroundImage = inputRowConfigJSONObject.get(
			"backgroundImage");

		if (backgroundImage instanceof JSONObject) {
			return (JSONObject)backgroundImage;
		}

		return JSONUtil.put("url", backgroundImage);
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