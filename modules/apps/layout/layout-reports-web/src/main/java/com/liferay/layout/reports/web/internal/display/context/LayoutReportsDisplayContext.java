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

package com.liferay.layout.reports.web.internal.display.context;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.portlet.RenderRequest;

/**
 * @author Cristina González
 */
public class LayoutReportsDisplayContext {

	public LayoutReportsDisplayContext(
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		LayoutSEOLinkManager layoutSEOLinkManager, Language language,
		Portal portal, RenderRequest renderRequest) {

		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		Layout layout = _layoutLocalService.fetchLayout(
			_themeDisplay.getPlid());

		_data = Collections.singletonMap(
			"context",
			HashMapBuilder.<String, Object>put(
				"assetsPath",
				_portal.getPathContext(_renderRequest) + "/assets/"
			).put(
				"canonicalURLs", _getCanonicalURLs(layout)
			).put(
				"defaultLanguageId",
				LocaleUtil.toBCP47LanguageId(_getDefaultLocale(layout))
			).put(
				"showButton", false
			).put(
				"validConnection", false
			).build());

		return _data;
	}

	private Map<Locale, String> _getAlternateURLs(
		String currentCompleteURL, Layout layout) {

		try {
			return _portal.getAlternateURLs(
				currentCompleteURL, _themeDisplay, layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return Collections.emptyMap();
	}

	private String _getCanonicalURL(String currentCompleteURL, Layout layout) {
		try {
			return _portal.getCanonicalURL(
				currentCompleteURL, _themeDisplay, layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return StringPool.BLANK;
	}

	private List<Map<String, Object>> _getCanonicalURLs(Layout layout) {
		String currentCompleteURL = _portal.getCurrentCompleteURL(
			_portal.getHttpServletRequest(_renderRequest));

		String canonicalURL = _getCanonicalURL(currentCompleteURL, layout);

		Map<Locale, String> alternateURLs = _getAlternateURLs(
			currentCompleteURL, layout);

		return Optional.ofNullable(
			_groupLocalService.fetchGroup(layout.getGroupId())
		).map(
			Group::getGroupId
		).map(
			_language::getAvailableLocales
		).orElseGet(
			Collections::emptySet
		).stream(
		).map(
			locale -> HashMapBuilder.<String, Object>put(
				"canonicalURL",
				() -> {
					LayoutSEOLink layoutSEOLink =
						_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
							layout, locale, canonicalURL, alternateURLs);

					return layoutSEOLink.getHref();
				}
			).put(
				"languageId", LocaleUtil.toBCP47LanguageId(locale)
			).put(
				"title",
				Optional.ofNullable(
					layout.getTitle(locale)
				).filter(
					Validator::isNotNull
				).orElseGet(
					() -> layout.getName(locale)
				)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private Locale _getDefaultLocale(Layout layout) {
		try {
			return _portal.getSiteDefaultLocale(layout.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return LocaleUtil.getSiteDefault();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsDisplayContext.class);

	private Map<String, Object> _data;
	private final GroupLocalService _groupLocalService;
	private final Language _language;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}