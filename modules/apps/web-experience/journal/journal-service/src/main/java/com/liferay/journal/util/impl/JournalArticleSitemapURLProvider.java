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

package com.liferay.journal.util.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.layouts.admin.kernel.util.SitemapURLProvider;
import com.liferay.layouts.admin.kernel.util.SitemapUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = SitemapURLProvider.class)
public class JournalArticleSitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException {

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticlesByLayoutUuid(
				layoutSet.getGroupId(), layoutUuid);

		visitArticles(element, layoutSet, themeDisplay, journalArticles);
	}

	@Override
	public void visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		List<JournalArticle> journalArticles =
			_journalArticleService.getLayoutArticles(layoutSet.getGroupId());

		visitArticles(element, layoutSet, themeDisplay, journalArticles);
	}

	@Reference(unbind = "-")
	protected void setJournalArticleService(
		JournalArticleService journalArticleService) {

		_journalArticleService = journalArticleService;
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

	protected void visitArticles(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay,
			List<JournalArticle> journalArticles)
		throws PortalException {

		if (journalArticles.isEmpty()) {
			return;
		}

		Set<String> processedArticleIds = new HashSet<>();

		String portalURL = _portal.getPortalURL(layoutSet, themeDisplay);

		for (JournalArticle journalArticle : journalArticles) {
			if (processedArticleIds.contains(journalArticle.getArticleId()) ||
				(journalArticle.getStatus() !=
					WorkflowConstants.STATUS_APPROVED) ||
				!JournalUtil.isHead(journalArticle)) {

				continue;
			}

			Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				journalArticle.getLayoutUuid(), layoutSet.getGroupId(),
				layoutSet.isPrivateLayout());

			if (layout == null) {
				continue;
			}

			String groupFriendlyURL = _portal.getGroupFriendlyURL(
				_layoutSetLocalService.getLayoutSet(
					journalArticle.getGroupId(), false),
				themeDisplay);

			StringBundler sb = new StringBundler(4);

			if (!groupFriendlyURL.startsWith(portalURL)) {
				sb.append(portalURL);
			}

			sb.append(groupFriendlyURL);
			sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);
			sb.append(journalArticle.getUrlTitle());

			String articleURL = _portal.getCanonicalURL(
				sb.toString(), themeDisplay, layout);

			Map<Locale, String> alternateURLs = SitemapUtil.getAlternateURLs(
				articleURL, themeDisplay, layout);

			for (String alternateURL : alternateURLs.values()) {
				SitemapUtil.addURLElement(
					element, alternateURL, null,
					journalArticle.getModifiedDate(), articleURL,
					alternateURLs);
			}

			processedArticleIds.add(journalArticle.getArticleId());
		}
	}

	private JournalArticleService _journalArticleService;
	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

}