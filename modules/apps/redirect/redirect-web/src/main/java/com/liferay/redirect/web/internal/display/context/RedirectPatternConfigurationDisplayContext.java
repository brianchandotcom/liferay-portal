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

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class RedirectPatternConfigurationDisplayContext {

	public RedirectPatternConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RedirectPatternConfigurationProvider
			redirectPatternConfigurationProvider) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_redirectPatternConfigurationProvider =
			redirectPatternConfigurationProvider;
	}

	public String getRedirectPatternConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/redirect/edit_redirect_patterns"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).buildString();
	}

	public Map<String, Object> getRedirectPatterns() {
		return HashMapBuilder.<String, Object>put(
			"patterns",
			() -> {
				List<Map<String, Object>> list = new ArrayList<>();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				Map<Pattern, String> patternStrings =
					_redirectPatternConfigurationProvider.getPatternStrings(
						themeDisplay.getScopeGroupId());

				patternStrings.forEach(
					(pattern, destinationURL) -> list.add(
						HashMapBuilder.<String, Object>put(
							"destinationURL", destinationURL
						).put(
							"pattern", pattern.toString()
						).build()));

				return list;
			}
		).put(
			"portletNamespace", _liferayPortletResponse.getNamespace()
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

}