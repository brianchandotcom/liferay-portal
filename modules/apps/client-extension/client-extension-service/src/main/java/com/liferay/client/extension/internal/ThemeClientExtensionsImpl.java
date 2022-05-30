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

package com.liferay.client.extension.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.client.extension.ThemeCSSURLs;
import com.liferay.portal.kernel.client.extension.ThemeClientExtensions;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = ThemeClientExtensions.class)
public class ThemeClientExtensionsImpl implements ThemeClientExtensions {

	@Override
	public String getFaviconURL(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout != null) {
			String favicon = layout.getFavicon();

			if (Validator.isNotNull(favicon)) {
				return favicon;
			}
		}

		return themeDisplay.getPathThemeImages() + StringPool.SLASH +
			PropsValues.THEME_SHORTCUT_ICON;
	}

	@Override
	public ThemeCSSURLs getThemeCSSURLs(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new ThemeCSSURLsImpl(
			HtmlUtil.escapeAttribute(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeCss() + "/main.css")),
			HtmlUtil.escapeAttribute(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeCss() + "/clay.css")));
	}

	@Override
	public List<String> getThemeJSURLs(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return Collections.singletonList(
			HtmlUtil.escape(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeJavaScript() + "/main.js")));
	}

	@Reference
	private Portal _portal;

}