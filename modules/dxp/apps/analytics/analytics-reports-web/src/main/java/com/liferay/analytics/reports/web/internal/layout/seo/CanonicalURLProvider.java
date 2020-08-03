/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.layout.seo;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class CanonicalURLProvider {

	public CanonicalURLProvider(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		HttpServletRequest httpServletRequest,
		InfoDisplayObjectProvider<?> infoDisplayObjectProvider,
		Language language, LayoutSEOLinkManager layoutSEOLinkManager,
		Portal portal) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_httpServletRequest = httpServletRequest;
		_infoDisplayObjectProvider = infoDisplayObjectProvider;
		_language = language;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getCanonicalURL() throws PortalException {
		return _getCanonicalURL(
			_portal.getCanonicalURL(
				_getCanonicalURL(_portal.getLocale(_httpServletRequest)),
				_themeDisplay, _themeDisplay.getLayout(), false, false));
	}

	private Map<Locale, String> _getAlternateURLS() {
		Set<Locale> locales = _language.getAvailableLocales(
			_themeDisplay.getSiteGroupId());

		Stream<Locale> stream = locales.stream();

		return stream.map(
			locale -> {
				try {
					return Optional.of(
						new AbstractMap.SimpleEntry<>(
							locale, _getCanonicalURL(locale)));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return Optional.<Map.Entry<Locale, String>>empty();
				}
			}
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private String _getCanonicalURL(Locale locale) throws PortalException {
		String completeURL = _portal.getCurrentCompleteURL(_httpServletRequest);

		if (_infoDisplayObjectProvider != null) {
			completeURL = _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				_portal.getClassName(
					_infoDisplayObjectProvider.getClassNameId()),
				_infoDisplayObjectProvider.getClassPK(), locale, _themeDisplay);
		}

		return _portal.getCanonicalURL(
			completeURL, _themeDisplay, _themeDisplay.getLayout(), false,
			false);
	}

	private String _getCanonicalURL(String canonicalURL)
		throws PortalException {

		LayoutSEOLink layoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_themeDisplay.getLayout(), _themeDisplay.getLocale(),
				canonicalURL, _getAlternateURLS());

		return layoutSEOLink.getHref();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CanonicalURLProvider.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayObjectProvider<?> _infoDisplayObjectProvider;
	private final Language _language;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;

}