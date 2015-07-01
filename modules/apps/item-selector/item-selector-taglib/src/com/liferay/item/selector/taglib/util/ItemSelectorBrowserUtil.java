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

import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Locale;

/**
 * @author Ambrin Chaudhary
 */
public class ItemSelectorBrowserUtil {

	public static JSONObject getItemMetadataJSONObject(
			FileEntry fileEntry, Locale locale)
		throws PortalException {

		String title = DLUtil.getTitleWithExtension(fileEntry);
		FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

		JSONObject firstTabJSONObject = JSONFactoryUtil.createJSONObject();

		firstTabJSONObject.put("title", LanguageUtil.get(locale, "file-info"));

		JSONArray firstTabDataJSONArray = JSONFactoryUtil.createJSONArray();

		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "format"),
				HtmlUtil.escape(latestFileVersion.getExtension())));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "size"),
				TextFormatter.formatStorageSize(fileEntry.getSize(), locale)));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "name"), HtmlUtil.escape(title)));
		firstTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "modified"),
				LanguageUtil.format(
					locale, "x-ago-by-x",
					new Object[] {
						LanguageUtil.getTimeDescription(
							locale, System.currentTimeMillis() -
								fileEntry.getModifiedDate().getTime(),
							true),
						HtmlUtil.escape(fileEntry.getUserName())
					}
				)));

		firstTabJSONObject.put("data", firstTabDataJSONArray);

		JSONObject secondTabJSONObject = JSONFactoryUtil.createJSONObject();

		secondTabJSONObject.put("title", LanguageUtil.get(locale, "version"));

		JSONArray secondTabDataJSONArray = JSONFactoryUtil.createJSONArray();

		secondTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "version"),
				HtmlUtil.escape(latestFileVersion.getVersion())));
		secondTabDataJSONArray.put(
			getDataJSONObject(
				LanguageUtil.get(locale, "status"),
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