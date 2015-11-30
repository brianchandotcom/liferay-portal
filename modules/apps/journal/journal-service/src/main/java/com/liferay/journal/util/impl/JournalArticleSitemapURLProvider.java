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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.layoutsadmin.util.SitemapURLProvider;
import com.liferay.portlet.layoutsadmin.util.SitemapUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		List<JournalArticle> journalArticles =
			_journalArticleService.getArticlesByLayoutUuid(
				layout.getGroupId(), layout.getUuid());

		if (journalArticles.isEmpty()) {
			return;
		}

		Set<String> processedArticleIds = new HashSet<>();

		for (JournalArticle journalArticle : journalArticles) {
			if (processedArticleIds.contains(journalArticle.getArticleId()) ||
				(journalArticle.getStatus() !=
					WorkflowConstants.STATUS_APPROVED) ||
				!JournalUtil.isHead(journalArticle)) {

				continue;
			}

			String portalURL = PortalUtil.getPortalURL(layout, themeDisplay);

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
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

			String articleURL = PortalUtil.getCanonicalURL(
				sb.toString(), themeDisplay, layout);

			SitemapUtil.addURLElement(
				element, articleURL, null, journalArticle.getModifiedDate(),
				articleURL,
				SitemapUtil.getAlternateURLs(articleURL, themeDisplay, layout));

			Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
				layout.getGroupId());

			if (availableLocales.size() > 1) {
				Locale defaultLocale = LocaleUtil.getSiteDefault();

				for (Locale availableLocale : availableLocales) {
					if (!availableLocale.equals(defaultLocale)) {
						String alternateURL = PortalUtil.getAlternateURL(
							articleURL, themeDisplay, availableLocale, layout);

						SitemapUtil.addURLElement(
							element, alternateURL, null,
							journalArticle.getModifiedDate(), articleURL,
							SitemapUtil.getAlternateURLs(
								articleURL, themeDisplay, layout));
					}
				}
			}

			processedArticleIds.add(journalArticle.getArticleId());
		}
	}

	@Reference(unbind = "-")
	protected void setJournalArticleService(
		JournalArticleService journalArticleService) {

		_journalArticleService = journalArticleService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	private volatile JournalArticleService _journalArticleService;
	private volatile LayoutSetLocalService _layoutSetLocalService;

}