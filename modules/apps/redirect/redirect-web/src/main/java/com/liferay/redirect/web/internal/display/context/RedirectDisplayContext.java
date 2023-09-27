/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				navigationItem.setActive(isShowRedirectEntries());
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "aliases"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(_isShowRedirectPatterns());
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "navigation",
					"patterns");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "patterns"));
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

	public boolean isShowRedirectEntries() {
		if (_isShowNavigationPanel("aliases")) {
			return true;
		}

		return false;
	}

	public boolean isShowRedirectNotFoundEntries() {
		if (_isShowNavigationPanel("404-urls")) {
			return true;
		}

		return false;
	}

	private boolean _isShowNavigationPanel(String name) {
		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "aliases");

		if (navigation.equals(name)) {
			return true;
		}

		return false;
	}

	private boolean _isShowRedirectPatterns() {
		if (_isShowNavigationPanel("patterns")) {
			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final RedirectConfiguration _redirectConfiguration;
	private final RenderResponse _renderResponse;

}