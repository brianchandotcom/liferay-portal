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

package com.liferay.journal.internal.search;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@Component(service = OpenSearch.class)
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

				if (contentSearch.isPrivateLayout() &&
					!_groupLocalService.hasUserGroup(
						themeDisplay.getUserId(), contentSearch.getGroupId())) {

					continue;
				}

				Layout hitLayout = _layoutLocalService.getLayout(
					contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
					contentSearch.getLayoutId());

				return _portal.getLayoutURL(hitLayout, themeDisplay);
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
			String groupFriendlyURL = _portal.getGroupFriendlyURL(
				_layoutSetLocalService.getLayoutSet(
					article.getGroupId(), false),
				themeDisplay, false, false);

			return StringBundler.concat(
				groupFriendlyURL,
				JournalArticleConstants.CANONICAL_URL_SEPARATOR,
				article.getUrlTitle());
		}

		Layout layout = themeDisplay.getLayout();

		List<Long> hitLayoutIds =
			_journalContentSearchLocalService.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(), articleId);

		for (Long hitLayoutId : hitLayoutIds) {
			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout.getGroupId(),
					layout.isPrivateLayout(), hitLayoutId, ActionKeys.VIEW)) {

				Layout hitLayout = _layoutLocalService.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					hitLayoutId.longValue());

				return _portal.getLayoutURL(hitLayout, themeDisplay);
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

	private AssetEntryLocalService _assetEntryLocalService;
	private GroupLocalService _groupLocalService;
	private JournalArticleService _journalArticleService;
	private JournalContentSearchLocalService _journalContentSearchLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

}