/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.comparator.EntryCreateDateComparator;
import com.liferay.portlet.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.portlet.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.portlet.bookmarks.util.comparator.EntryPriorityComparator;
import com.liferay.portlet.bookmarks.util.comparator.EntryURLComparator;
import com.liferay.portlet.bookmarks.util.comparator.EntryVisitsComparator;
import com.liferay.util.ContentUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksUtil {

	public static void addPortletBreadcrumbEntries(
			BookmarksEntry entry, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		BookmarksFolder folder = entry.getFolder();

		if (folder.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(folder, request, renderResponse);
		}

		BookmarksEntry unescapedEntry = entry.toUnescapedModel();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/bookmarks/view_entry");
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, unescapedEntry.getName(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			BookmarksFolder folder, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		String strutsAction = ParamUtil.getString(request, "struts_action");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (strutsAction.equals("/bookmarks/select_folder")) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setParameter(
				"struts_action", "/bookmarks/select_folder");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("home"), portletURL.toString());
		}
		else {
			portletURL.setParameter("struts_action", "/bookmarks/view");
		}

		List<BookmarksFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		for (BookmarksFolder ancestorFolder : ancestorFolders) {
			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorFolder.getName(), portletURL.toString());
		}

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		if (folder.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BookmarksFolder unescapedFolder = folder.toUnescapedModel();

			PortalUtil.addPortletBreadcrumbEntry(
				request, unescapedFolder.getName(), portletURL.toString());
		}
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			folderId);

		addPortletBreadcrumbEntries(folder, request, renderResponse);
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return themeDisplay.translate("home");
		}

		BookmarksFolder folder =
			BookmarksFolderLocalServiceUtil.fetchBookmarksFolder(folderId);

		List<BookmarksFolder> folders = folder.getAncestors();

		StringBundler sb = new StringBundler((folders.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		Collections.reverse(folders);

		for (BookmarksFolder curFolder : folders) {
			sb.append(StringPool.RAQUO_CHAR);
			sb.append(StringPool.SPACE);
			sb.append(curFolder.getName());
		}

		sb.append(StringPool.RAQUO_CHAR);
		sb.append(StringPool.SPACE);
		sb.append(folder.getName());

		return sb.toString();
	}

	public static String getControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.BOOKMARKS,
			PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/bookmarks/view");
		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	public static Map<Locale, String> getEmailEntryAddedBodyMap(
		PortletPreferences preferences) {

		Map<Locale, String> map = LocalizationUtil.getLocalizationMap(
			preferences, "emailEntryAddedBody");

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultValue = map.get(defaultLocale);

		if (Validator.isNotNull(defaultValue)) {
			return map;
		}

		map.put(
			defaultLocale,
			ContentUtil.get(
				PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_BODY)));

		return map;
	}

	public static boolean getEmailEntryAddedEnabled(
		PortletPreferences preferences) {

		String emailEntryAddedEnabled = preferences.getValue(
			"emailEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailEntryAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_ENABLED));
		}
	}

	public static Map<Locale, String> getEmailEntryAddedSubjectMap(
		PortletPreferences preferences) {

		Map<Locale, String> map = LocalizationUtil.getLocalizationMap(
			preferences, "emailEntryAddedSubject");

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultValue = map.get(defaultLocale);

		if (Validator.isNotNull(defaultValue)) {
			return map;
		}

		map.put(
			defaultLocale,
			ContentUtil.get(
				PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_SUBJECT)));

		return map;
	}

	public static Map<Locale, String> getEmailEntryUpdatedBodyMap(
		PortletPreferences preferences) {

		Map<Locale, String> map = LocalizationUtil.getLocalizationMap(
			preferences, "emailEntryUpdatedBody");

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultValue = map.get(defaultLocale);

		if (Validator.isNotNull(defaultValue)) {
			return map;
		}

		map.put(
			defaultLocale,
			ContentUtil.get(
				PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_BODY)));

		return map;
	}

	public static boolean getEmailEntryUpdatedEnabled(
		PortletPreferences preferences) {

		String emailEntryUpdatedEnabled = preferences.getValue(
			"emailEntryUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailEntryUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailEntryUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_ENABLED));
		}
	}

	public static Map<Locale, String> getEmailEntryUpdatedSubjectMap(
		PortletPreferences preferences) {

		Map<Locale, String> map = LocalizationUtil.getLocalizationMap(
			preferences, "emailEntryUpdatedSubject");

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultValue = map.get(defaultLocale);

		if (Validator.isNotNull(defaultValue)) {
			return map;
		}

		map.put(
			defaultLocale,
			ContentUtil.get(
				PropsUtil.get(
					PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_SUBJECT)));

		return map;
	}

	public static String getEmailFromAddress(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId, PropsValues.BOOKMARKS_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromName(
			preferences, companyId, PropsValues.BOOKMARKS_EMAIL_FROM_NAME);
	}

	public static List<Object> getEntries(Hits hits) {
		List<Object> entries = new ArrayList<Object>();

		for (Document document : hits.getDocs()) {
			String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				Object obj = null;

				if (entryClassName.equals(BookmarksEntry.class.getName())) {
					obj = BookmarksEntryLocalServiceUtil.getEntry(entryClassPK);
				}
				else if (entryClassName.equals(
							BookmarksFolder.class.getName())) {

					obj = BookmarksFolderLocalServiceUtil.getFolder(
						entryClassPK);
				}

				entries.add(obj);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Bookmarks search index is stale and contains entry " +
							entryClassPK);
				}

				continue;
			}
		}

		return entries;
	}

	public static OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new EntryModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new EntryNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new EntryPriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("url")) {
			orderByComparator = new EntryURLComparator(orderByAsc);
		}
		else if (orderByCol.equals("visits")) {
			orderByComparator = new EntryVisitsComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksUtil.class);

}