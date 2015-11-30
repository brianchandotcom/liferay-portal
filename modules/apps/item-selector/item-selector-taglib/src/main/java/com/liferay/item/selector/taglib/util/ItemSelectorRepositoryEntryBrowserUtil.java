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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ambrin Chaudhary
 * @author Roberto Díaz
 */
public class ItemSelectorRepositoryEntryBrowserUtil {

	public static void addPortletBreadcrumbEntries(
			long folderId, String displayStyle, HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL portletURL)
		throws Exception {

		addGroupSelectorBreadcrumbEntry(
			request, liferayPortletResponse, portletURL);

		portletURL.setParameter("displayStyle", displayStyle);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		addPortletBreadcrumbEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, request,
			scopeGroup.getDescriptiveName(request.getLocale()), portletURL);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			List<Folder> ancestorFolders = folder.getAncestors();

			Collections.reverse(ancestorFolders);

			for (Folder ancestorFolder : ancestorFolders) {
				addPortletBreadcrumbEntry(
					ancestorFolder.getFolderId(), request,
					ancestorFolder.getName(), portletURL);
			}

			addPortletBreadcrumbEntry(
				folder.getFolderId(), request, folder.getName(), portletURL);
		}
	}

	public static JSONObject getItemMetadataJSONObject(
			FileEntry fileEntry, Locale locale)
		throws PortalException {

		JSONObject itemMetadataJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray groupsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONObject firstTabJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray firstTabDataJSONArray = JSONFactoryUtil.createJSONArray();

		FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

		firstTabDataJSONArray.put(
			_createJSONObject(
				LanguageUtil.get(locale, "format"),
				HtmlUtil.escape(latestFileVersion.getExtension())));

		firstTabDataJSONArray.put(
			_createJSONObject(
				LanguageUtil.get(locale, "size"),
				TextFormatter.formatStorageSize(fileEntry.getSize(), locale)));
		firstTabDataJSONArray.put(
			_createJSONObject(
				LanguageUtil.get(locale, "name"),
				HtmlUtil.escape(DLUtil.getTitleWithExtension(fileEntry))));
		firstTabDataJSONArray.put(
			_createJSONObject(
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

		firstTabJSONObject.put("title", LanguageUtil.get(locale, "file-info"));

		groupsJSONArray.put(firstTabJSONObject);

		JSONObject secondTabJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray secondTabDataJSONArray = JSONFactoryUtil.createJSONArray();

		secondTabDataJSONArray.put(
			_createJSONObject(
				LanguageUtil.get(locale, "version"),
				HtmlUtil.escape(latestFileVersion.getVersion())));
		secondTabDataJSONArray.put(
			_createJSONObject(
				LanguageUtil.get(locale, "status"),
				WorkflowConstants.getStatusLabel(
					latestFileVersion.getStatus())));

		secondTabJSONObject.put("data", secondTabDataJSONArray);

		secondTabJSONObject.put("title", LanguageUtil.get(locale, "version"));

		groupsJSONArray.put(secondTabJSONObject);

		itemMetadataJSONObject.put("groups", groupsJSONArray);

		return itemMetadataJSONObject;
	}

	protected static void addGroupSelectorBreadcrumbEntry(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL portletURL)
		throws PortalException, PortletException {

		PortletURL viewGroupSelectorURL = PortletURLUtil.clone(
			portletURL, liferayPortletResponse);

		viewGroupSelectorURL.setParameter(
			"showGroupSelector", Boolean.TRUE.toString());

		PortalUtil.addPortletBreadcrumbEntry(
			request, "sites", viewGroupSelectorURL.toString());
	}

	protected static void addPortletBreadcrumbEntry(
		long folderId, HttpServletRequest request, String title,
		PortletURL portletURL) {

		portletURL.setParameter("folderId", String.valueOf(folderId));

		PortalUtil.addPortletBreadcrumbEntry(
			request, title, portletURL.toString());
	}

	private static JSONObject _createJSONObject(String key, String value) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("key", key);
		jsonObject.put("value", value);

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorRepositoryEntryBrowserUtil.class);

}