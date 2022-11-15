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

package com.liferay.content.dashboard.journal.internal.item.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class PreviewJournalArticleContentDashboardItemVersionAction
	implements ContentDashboardItemVersionAction {

	public PreviewJournalArticleContentDashboardItemVersionAction(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		Language language,
		LayoutDisplayPageProviderRegistry layoutDisplayPageProviderRegistry,
		LayoutLocalService layoutLocalService,
		LayoutSEOLinkManager layoutSEOLinkManager, Portal portal,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_language = language;
		_layoutDisplayPageProviderRegistry = layoutDisplayPageProviderRegistry;
		_layoutLocalService = layoutLocalService;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_portal = portal;
		_requestBackedPortletURLFactory = requestBackedPortletURLFactory;
	}

	@Override
	public String getIcon() {
		return "view";
	}

	@Override
	public String getLabel(Locale locale) {
		if (_journalArticle.isDraft()) {
			return _language.get(locale, "preview-draft");
		}

		return _language.get(locale, "preview");
	}

	@Override
	public String getName() {
		return "preview";
	}

	public Type getType() {
		return Type.BLANK;
	}

	@Override
	public String getURL() {
		ViewJournalArticleContentDashboardItemAction
			viewJournalArticleContentDashboardItemAction =
				new ViewJournalArticleContentDashboardItemAction(
					_assetDisplayPageFriendlyURLProvider, _httpServletRequest,
					_journalArticle, _language,
					_layoutDisplayPageProviderRegistry, _layoutLocalService,
					_layoutSEOLinkManager, _portal);

		String displayPageTemplateURL =
			viewJournalArticleContentDashboardItemAction.getURL();

		if (Validator.isNotNull(displayPageTemplateURL)) {
			displayPageTemplateURL = HttpComponentsUtil.addParameter(
				displayPageTemplateURL, "p_l_mode", Constants.PREVIEW);

			return HttpComponentsUtil.addParameter(
				displayPageTemplateURL, "version",
				_journalArticle.getVersion());
		}

		if (Validator.isNull(_journalArticle.getDDMTemplateKey())) {
			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE));

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return PortletURLBuilder.create(
			_requestBackedPortletURLFactory.createRenderURL(
				JournalPortletKeys.JOURNAL)
		).setMVCPath(
			"/preview_article_content.jsp"
		).setRedirect(
			portletURL
		).setBackURL(
			portletURL.toString()
		).setParameter(
			"articleId", _journalArticle.getArticleId()
		).setParameter(
			"groupId", _journalArticle.getGroupId()
		).setParameter(
			"showTitle", true
		).setParameter(
			"version", _journalArticle.getVersion()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory;

}