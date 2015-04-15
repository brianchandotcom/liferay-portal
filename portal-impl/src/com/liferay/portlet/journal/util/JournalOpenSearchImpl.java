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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@OSGiBeanProperties
public class JournalOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String TITLE = "Liferay Journal Search: ";

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public Indexer getIndexer() {
		return IndexerRegistryUtil.getIndexer(JournalArticle.class);
	}

	@Override
	public String getSearchPath() {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	protected String getLayoutURL(ThemeDisplay themeDisplay, String articleId)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<JournalContentSearch> contentSearches =
			JournalContentSearchLocalServiceUtil.getArticleContentSearches(
				articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, contentSearch.getGroupId(),
					contentSearch.getLayoutId(), ActionKeys.VIEW)) {

				Layout hitLayout = LayoutLocalServiceUtil.getLayout(
					contentSearch.getGroupId(), contentSearch.getLayoutId());

				return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
		}

		return null;
	}

	@Override
	protected String getURL(
			ThemeDisplay themeDisplay, long groupId, Document result,
			PortletURL portletURL)
		throws Exception {

		String articleId = result.get("articleId");

		JournalArticle article = JournalArticleServiceUtil.getArticle(
			groupId, articleId);

		if (Validator.isNotNull(article.getLayoutUuid())) {
			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				LayoutSetLocalServiceUtil.getLayoutSet(article.getGroupId()),
				themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					article.getUrlTitle());
		}

		Layout layout = themeDisplay.getLayout();

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), articleId);

		for (Long hitLayoutId : hitLayoutIds) {
			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (LayoutPermissionUtil.contains(
					permissionChecker, layout.getGroupId(), hitLayoutId,
					ActionKeys.VIEW)) {

				Layout hitLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), hitLayoutId.longValue());

				return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
		}

		String layoutURL = getLayoutURL(themeDisplay, articleId);

		if (layoutURL != null) {
			return layoutURL;
		}

		portletURL.setParameter(
			"mvcPath", "/html/portlet/journal/view_article.jsp");
		portletURL.setParameter("groupId", String.valueOf(groupId));
		portletURL.setParameter("articleId", articleId);

		String version = result.get("version");

		portletURL.setParameter("version", version);

		return portletURL.toString();
	}

}