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
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ViewJournalArticleContentDashboardItemAction
	implements ContentDashboardItemAction {

	public ViewJournalArticleContentDashboardItemAction(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		Language language,
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
		LayoutLocalService layoutLocalService,
		LayoutSEOLinkManager layoutSEOLinkManager, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_language = language;
		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
		_layoutLocalService = layoutLocalService;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_portal = portal;
	}

	@Override
	public String getIcon() {
		return "view";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "view");
	}

	@Override
	public String getName() {
		return "view";
	}

	@Override
	public Type getType() {
		return Type.VIEW;
	}

	@Override
	public String getURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getViewURL(themeDisplay.getLocale(), themeDisplay);
	}

	@Override
	public String getURL(Locale locale) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getViewURL(locale, themeDisplay);
	}

	private LayoutDisplayPageObjectProvider<JournalArticle>
		_getLayoutDisplayPageObjectProvider(JournalArticle journalArticle) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					JournalArticle.class.getName());

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		return (LayoutDisplayPageObjectProvider<JournalArticle>)
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
	}

	private Optional<Layout> _getLayoutOptional(
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider) {

		return Optional.ofNullable(
			layoutDisplayPageObjectProvider
		).filter(
			currentLayoutDisplayPageObjectProvider ->
				currentLayoutDisplayPageObjectProvider.getDisplayObject() !=
					null
		).map(
			currentLayoutDisplayPageObjectProvider ->
				AssetDisplayPageUtil.getAssetDisplayPageLayoutPageTemplateEntry(
					layoutDisplayPageObjectProvider.getGroupId(),
					layoutDisplayPageObjectProvider.getClassNameId(),
					layoutDisplayPageObjectProvider.getClassPK(),
					layoutDisplayPageObjectProvider.getClassTypeId())
		).map(
			layoutPageTemplateEntry -> _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid())
		);
	}

	private String _getLocalizedURL(
		Locale locale, List<LayoutSEOLink> localizedLayoutSEOLinks) {

		List<LayoutSEOLink> layoutSEOLinks = new ArrayList<>();

		ListUtil.filter(
			localizedLayoutSEOLinks, layoutSEOLinks,
			seoLink -> Objects.equals(
				LocaleUtil.toW3cLanguageId(locale), seoLink.getHrefLang()));

		LayoutSEOLink localizedLayoutSEOLink = layoutSEOLinks.get(0);

		return localizedLayoutSEOLink.getHref();
	}

	private String _getViewURL(Locale locale, ThemeDisplay themeDisplay) {
		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		Optional<Layout> layoutOptional = _getLayoutOptional(
			_getLayoutDisplayPageObjectProvider(_journalArticle));

		return layoutOptional.map(
			layout -> {
				HttpServletRequest httpServletRequest =
					themeDisplay.getRequest();

				LayoutDisplayPageObjectProvider<?>
					initialLayoutDisplayPageObjectProvider =
						(LayoutDisplayPageObjectProvider<?>)
							httpServletRequest.getAttribute(
								LayoutDisplayPageWebKeys.
									LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

				httpServletRequest.setAttribute(
					LayoutDisplayPageWebKeys.
						LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
					_getLayoutDisplayPageObjectProvider(_journalArticle));

				String completeURL = _portal.getCurrentCompleteURL(
					httpServletRequest);

				try {
					List<LayoutSEOLink> localizedLayoutSEOLinks =
						_layoutSEOLinkManager.getLocalizedLayoutSEOLinks(
							layout,
							_portal.getSiteDefaultLocale(
								_portal.getScopeGroupId(_httpServletRequest)),
							_portal.getCanonicalURL(
								completeURL, themeDisplay, layout, false,
								false),
							Collections.singleton(locale));

					return _getLocalizedURL(locale, localizedLayoutSEOLinks);
				}
				catch (PortalException portalException) {
					_log.error(portalException);

					return StringPool.BLANK;
				}
				finally {
					httpServletRequest.setAttribute(
						LayoutDisplayPageWebKeys.
							LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
						initialLayoutDisplayPageObjectProvider);
				}
			}
		).map(
			url -> {
				String backURL = ParamUtil.getString(
					_httpServletRequest, "backURL");

				if (Validator.isNotNull(backURL)) {
					return HttpComponentsUtil.setParameter(
						url, "p_l_back_url", backURL);
				}

				return HttpComponentsUtil.setParameter(
					url, "p_l_back_url", themeDisplay.getURLCurrent());
			}
		).orElse(
			StringPool.BLANK
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewJournalArticleContentDashboardItemAction.class);

	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;

}