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

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class CanonicalURLProvider {

	public CanonicalURLProvider(
		HttpServletRequest httpServletRequest,
		LayoutSEOLinkManager layoutSEOLinkManager, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getCanonicalURL() throws PortalException {
		Locale locale = LocaleUtil.fromLanguageId(
			ParamUtil.getString(
				_httpServletRequest, "languageId",
				_themeDisplay.getLanguageId()));

		return _getCanonicalURL(locale);
	}

	private String _getCanonicalURL(Locale locale) throws PortalException {
		String completeURL = _portal.getCurrentCompleteURL(_httpServletRequest);

		String canonicalURL = _portal.getCanonicalURL(
			completeURL, _themeDisplay, _themeDisplay.getLayout(), false,
			false);

		LayoutSEOLink layoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_themeDisplay.getLayout(), locale, canonicalURL,
				_portal.getAlternateURLs(
					canonicalURL, _themeDisplay, _themeDisplay.getLayout()));

		return layoutSEOLink.getHref();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;

}