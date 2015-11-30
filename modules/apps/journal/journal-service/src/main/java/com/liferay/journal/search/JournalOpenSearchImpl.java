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

package com.liferay.journal.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;

import java.util.List;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@Component(immediate = true, service = OpenSearch.class)
public class JournalOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String TITLE = "Liferay Journal Search: ";

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public Indexer<JournalArticle> getIndexer() {
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
			_journalContentSearchLocalService.getArticleContentSearches(
				articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, contentSearch.getGroupId(),
					contentSearch.isPrivateLayout(),
					contentSearch.getLayoutId(), ActionKeys.VIEW)) {

				if (contentSearch.isPrivateLayout()) {
					if (!_groupLocalService.hasUserGroup(
							themeDisplay.getUserId(),
							contentSearch.getGroupId())) {

						continue;
					}
				}

				Layout hitLayout = _layoutLocalService.getLayout(
					contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
					contentSearch.getLayoutId());

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

		JournalArticle article = _journalArticleService.getArticle(
			groupId, articleId);

		if (Validator.isNotNull(article.getLayoutUuid())) {
			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				_layoutSetLocalService.getLayoutSet(
					article.getGroupId(), false),
				themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					article.getUrlTitle());
		}

		Layout layout = themeDisplay.getLayout();

		List<Long> hitLayoutIds =
			_journalContentSearchLocalService.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(), articleId);

		for (Long hitLayoutId : hitLayoutIds) {
			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (LayoutPermissionUtil.contains(
					permissionChecker, layout.getGroupId(),
					layout.isPrivateLayout(), hitLayoutId, ActionKeys.VIEW)) {

				Layout hitLayout = _layoutLocalService.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					hitLayoutId.longValue());

				return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
			}
		}

		String layoutURL = getLayoutURL(themeDisplay, articleId);

		if (layoutURL != null) {
			return layoutURL;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (assetEntry == null) {
			return null;
		}

		portletURL.setParameter(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));
		portletURL.setParameter("groupId", String.valueOf(groupId));
		portletURL.setParameter("articleId", articleId);

		return portletURL.toString();
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleService(
		JournalArticleService journalArticleService) {

		_journalArticleService = journalArticleService;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile JournalArticleService _journalArticleService;
	private volatile JournalContentSearchLocalService
		_journalContentSearchLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile LayoutSetLocalService _layoutSetLocalService;

}