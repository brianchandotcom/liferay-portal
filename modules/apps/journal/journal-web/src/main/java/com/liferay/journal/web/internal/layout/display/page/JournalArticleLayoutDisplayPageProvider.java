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

package com.liferay.journal.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "service.ranking:Integer=200",
	service = LayoutDisplayPageProvider.class
)
public class JournalArticleLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<JournalArticle>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		JournalArticle article = journalArticleLocalService.fetchLatestArticle(
			infoItemReference.getClassPK());

		if ((article == null) || article.isInTrash()) {
			return null;
		}

		try {
			return new JournalArticleLayoutDisplayPageObjectProvider(
				article, assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<JournalArticle>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			JournalArticle article = _getArticle(groupId, urlTitle, null);

			if ((article == null) || article.isInTrash()) {
				return null;
			}

			return new JournalArticleLayoutDisplayPageObjectProvider(
				article, assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<JournalArticle>
		getLayoutDisplayPageObjectProvider(
			long groupId, String urlTitle, String version) {

		try {
			JournalArticle article = _getArticle(groupId, urlTitle, version);

			if ((article == null) || article.isExpired() ||
				article.isInTrash()) {

				return null;
			}

			return new JournalArticleLayoutDisplayPageObjectProvider(
				article, assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE;
	}

	@Reference
	protected AssetHelper assetHelper;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

	@Reference
	protected SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider;

	private JournalArticle _getArticle(
			long groupId, String urlTitle, String version)
		throws PortalException {

		for (long connectedGroupId :
				siteConnectedGroupGroupProvider.
					getCurrentAndAncestorSiteAndDepotGroupIds(groupId)) {

			JournalArticle article = null;

			if (Validator.isNotNull(version)) {
				article = journalArticleLocalService.fetchArticleByUrlTitle(
					connectedGroupId, urlTitle, GetterUtil.getDouble(version));
			}
			else {
				article = journalArticleLocalService.fetchArticleByUrlTitle(
					connectedGroupId, urlTitle);
			}

			if (article != null) {
				return article;
			}
		}

		return null;
	}

}