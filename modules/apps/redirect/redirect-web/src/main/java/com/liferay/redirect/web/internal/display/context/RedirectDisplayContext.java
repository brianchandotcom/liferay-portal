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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.redirect.configuration.RedirectConfiguration;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class RedirectDisplayContext {

	public RedirectDisplayContext(
		HttpServletRequest httpServletRequest,
		RedirectConfiguration redirectConfiguration,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_redirectConfiguration = redirectConfiguration;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(!isShowRedirectNotFoundEntries());
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "redirects"));
			}
		).add(
			_redirectConfiguration::isRedirectNotFoundEnabled,
			navigationItem -> {
				navigationItem.setActive(isShowRedirectNotFoundEntries());
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "navigation",
					"404-urls");
				navigationItem.setLabel(
					LanguageUtil.format(
						_httpServletRequest, "x-urls",
						HttpServletResponse.SC_NOT_FOUND, false));
			}
		).build();
	}

	public boolean isShowRedirectNotFoundEntries() {
		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "redirects");

		if (navigation.equals("404-urls")) {
			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final RedirectConfiguration _redirectConfiguration;
	private final RenderResponse _renderResponse;

}