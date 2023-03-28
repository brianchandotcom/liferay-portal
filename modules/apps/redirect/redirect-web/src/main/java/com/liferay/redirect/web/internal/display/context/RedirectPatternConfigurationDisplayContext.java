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
import com.liferay.portal.util.PropsValues;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public Map<String, Object> getRedirectPatterns() {
		return HashMapBuilder.<String, Object>put(
			"actionUrl", _getRedirectPatternConfigurationURL()
		).put(
			"patterns",
			() -> {
				List<Map<String, Object>> list = new ArrayList<>();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				List<RedirectPatternEntry> redirectPatternEntries =
					_redirectPatternConfigurationProvider.
						getRedirectPatternEntries(
							themeDisplay.getScopeGroupId());

				redirectPatternEntries.forEach(
					redirectPatternEntry -> list.add(
						HashMapBuilder.<String, Object>put(
							"destinationURL",
							redirectPatternEntry.getDestinationURL()
						).put(
							"pattern",
							String.valueOf(redirectPatternEntry.getPattern())
						).put(
							"userAgent",
							() -> {
								if (redirectPatternEntry.getUserAgent() ==
										null) {

									return RedirectConstants.USER_AGENT_ALL;
								}

								return redirectPatternEntry.getUserAgent();
							}
						).build()));

				return list;
			}
		).put(
			"portletNamespace", _liferayPortletResponse.getNamespace()
		).put(
			"strings",
			HashMapBuilder.put(
				"absoluteURL", PortalUtil.getPortalURL(_httpServletRequest)
			).put(
				"relativeURL",
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL
			).build()
		).build();
	}

	private String _getRedirectPatternConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/redirect/edit_redirect_patterns"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).buildString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

}