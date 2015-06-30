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

package com.liferay.item.selector.taglib.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ambrin Chaudhary
 */
public class ItemSelectorBrowserUtil {

	public static JSONObject getItemMetadataJSONObject(
		HttpServletRequest request, FileEntry fileEntry,
		FileVersion latestFileVersion, String title) {

		JSONObject firstTabJSONObject = JSONFactoryUtil.createJSONObject();
		firstTabJSONObject.put("title", LanguageUtil.get(request, "file-info"));

		JSONArray firstTabDataJSONArray = JSONFactoryUtil.createJSONArray();
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(request, "format"),
				HtmlUtil.escape(latestFileVersion.getExtension())));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(request, "size"),
				TextFormatter.formatStorageSize(
					fileEntry.getSize(), request.getLocale())));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(request, "name"), HtmlUtil.escape(title)));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(request, "modified"),
				LanguageUtil.format(request, "x-ago-by-x", new Object[] {
					LanguageUtil.getTimeDescription(
						request.getLocale(), System.currentTimeMillis() -
							fileEntry.getModifiedDate().getTime(),
						true),
					HtmlUtil.escape(fileEntry.getUserName())
				})));

		firstTabJSONObject.put("data", firstTabDataJSONArray);

		JSONObject secondTabJSONObject = JSONFactoryUtil.createJSONObject();
		secondTabJSONObject.put("title", LanguageUtil.get(request, "version"));

		JSONArray secondTabDataJSONArray = JSONFactoryUtil.createJSONArray();
		secondTabDataJSONArray.put(
			getDataJSONObject(LanguageUtil.get(request, "version"),
				HtmlUtil.escape(latestFileVersion.getVersion())));
		secondTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(request, "status"),
				WorkflowConstants.getStatusLabel(
					latestFileVersion.getStatus())));

		secondTabJSONObject.put("data", secondTabDataJSONArray);

		JSONArray groupsJSONArray = JSONFactoryUtil.createJSONArray();
		groupsJSONArray.put(firstTabJSONObject);
		groupsJSONArray.put(secondTabJSONObject);

		JSONObject itemMetadataJSONObject = JSONFactoryUtil.createJSONObject();
		itemMetadataJSONObject.put("groups", groupsJSONArray);

		return itemMetadataJSONObject;
	}

	protected static JSONObject getDataJSONObject(String key, String value) {
		String json = "{key: " + key + ", value: " + value + "}";

		try {
			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (JSONException jsone) {
			_log.error("Unable to create a JSON object from: " + json, jsone);
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorBrowserUtil.class);

}