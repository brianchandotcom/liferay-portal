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

package com.liferay.journal.web.internal.util;

import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.comparator.ArticleCreateDateComparator;
import com.liferay.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.ArticleIDComparator;
import com.liferay.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.ArticleReviewDateComparator;
import com.liferay.journal.util.comparator.ArticleTitleComparator;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Eduardo García
 */
public class JournalPortletUtil {

	public static String getAddMenuFavItemKey(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(portletRequest, "folderId");

		String key =
			"journal-add-menu-fav-items-" + themeDisplay.getScopeGroupId();

		folderId = _getAddMenuFavItemFolderId(folderId);

		if (folderId <= 0) {
			return key;
		}

		return key + StringPool.DASH + folderId;
	}

	public static OrderByComparator<JournalArticle> getArticleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<JournalArticle> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new ArticleCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new ArticleDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("id")) {
			orderByComparator = new ArticleIDComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new ArticleModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("review-date")) {
			orderByComparator = new ArticleReviewDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new ArticleTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("version")) {
			orderByComparator = new ArticleVersionComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static List<BreadcrumbEntry> getPortletBreadcrumbEntries(
			JournalFolder folder, HttpServletRequest httpServletRequest,
			PortletURL portletURL)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(LanguageUtil.get(httpServletRequest, "home"));

		portletURL.setParameter(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		breadcrumbEntry.setURL(portletURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		if (folder == null) {
			return breadcrumbEntries;
		}

		List<JournalFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		for (JournalFolder ancestorFolder : ancestorFolders) {
			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			folderBreadcrumbEntry.setTitle(ancestorFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		if (folder.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			JournalFolder unescapedFolder = folder.toUnescapedModel();

			folderBreadcrumbEntry.setTitle(unescapedFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(folder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		return breadcrumbEntries;
	}

	private static long _getAddMenuFavItemFolderId(long folderId)
		throws PortalException {

		if (folderId <= 0) {
			return 0;
		}

		JournalFolder folder = JournalFolderLocalServiceUtil.fetchFolder(
			folderId);

		while (folder != null) {
			int restrictionType = JournalHelperUtil.getRestrictionType(
				folder.getFolderId());

			if (restrictionType ==
					JournalFolderConstants.
						RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {

				return folder.getFolderId();
			}

			folder = folder.getParentFolder();
		}

		return 0;
	}

}