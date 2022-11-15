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

package com.liferay.content.dashboard.journal.internal.item.action.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.content.dashboard.journal.internal.item.action.PreviewJournalArticleContentDashboardItemVersionAction;
import com.liferay.content.dashboard.journal.internal.item.action.ViewJournalArticleContentDashboardItemAction;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "service.ranking:Integer=600",
	service = ContentDashboardItemVersionActionProvider.class
)
public class PreviewJournalArticleContentDashboardItemVersionActionProvider
	implements ContentDashboardItemVersionActionProvider<JournalArticle> {

	@Override
	public ContentDashboardItemVersionAction
		getContentDashboardItemVersionAction(
			JournalArticle journalArticle,
			HttpServletRequest httpServletRequest) {

		if (!isShow(journalArticle, httpServletRequest)) {
			return null;
		}

		return new PreviewJournalArticleContentDashboardItemVersionAction(
			_assetDisplayPageFriendlyURLProvider, httpServletRequest,
			journalArticle, _language, _layoutDisplayPageProviderRegistry,
			_layoutLocalService, _layoutSEOLinkManager, _portal,
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest));
	}

	@Override
	public boolean isShow(
		JournalArticle journalArticle, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (!_modelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), journalArticle,
					ActionKeys.VIEW)) {

				return false;
			}

			ViewJournalArticleContentDashboardItemAction
				viewJournalArticleContentDashboardItemAction =
					new ViewJournalArticleContentDashboardItemAction(
						_assetDisplayPageFriendlyURLProvider,
						httpServletRequest, journalArticle, _language,
						_layoutDisplayPageProviderRegistry, _layoutLocalService,
						_layoutSEOLinkManager, _portal);

			if (Validator.isNull(
					viewJournalArticleContentDashboardItemAction.getURL()) &&
				Validator.isNull(journalArticle.getDDMTemplateKey())) {

				return false;
			}

			return true;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreviewJournalArticleContentDashboardItemVersionActionProvider.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle> _modelResourcePermission;

	@Reference
	private Portal _portal;

}