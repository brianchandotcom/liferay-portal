/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
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

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		JournalArticle article = journalArticleLocalService.fetchLatestArticle(
			classPKInfoItemIdentifier.getClassPK());

		if (!_isShow(article)) {
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

			if (!_isShow(article)) {
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

			if (!_isShow(article)) {
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

	private boolean _isShow(JournalArticle article) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((article == null) || article.isExpired() || article.isInTrash() ||
			(article.isPending() && (permissionChecker != null) &&
			 !permissionChecker.isSignedIn()) ||
			article.isScheduled()) {

			return false;
		}

		return true;
	}

}