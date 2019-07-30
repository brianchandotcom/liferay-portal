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

package com.liferay.change.tracking.change.lists.configuration.web.internal.display.context;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 */
public class ChangeListsConfigurationDisplayContext {

	public ChangeListsConfigurationDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SoyContext getChangeListsConfigurationContext() {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"navigationItem",
			JSONUtil.put(
				JSONUtil.put(
					"active", true
				).put(
					"href", "#1"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "global-settings")
				).put(
					"visible", true
				))
		).put(
			"navigationItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"active", true
				).put(
					"href", "#1"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "global-settings")
				),
				JSONUtil.put(
					"active", false
				).put(
					"href", "#2"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "user-settings")
				))
		).put(
			"portalURL", _themeDisplay.getPortalURL()
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"urlChangeTrackingConfiguration",
			_themeDisplay.getPortalURL() +
				"/o/change-tracking-legacy/configurations/" +
					_themeDisplay.getCompanyId()
		).put(
			"urlChangeTrackingUserConfiguration",
			_getUserConfigurationURL(_themeDisplay)
		);

		PortletURL configurationPortletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, CTPortletKeys.CHANGE_LISTS_CONFIGURATION,
			PortletRequest.RENDER_PHASE);

		configurationPortletURL.setParameter("configurationSaved", "true");

		soyContext.put("urlConfiguration", configurationPortletURL.toString());

		PortletURL overviewPortletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, CTPortletKeys.CHANGE_LISTS,
			PortletRequest.RENDER_PHASE);

		soyContext.put("urlOverview", overviewPortletURL.toString());

		return soyContext;
	}

	private String _getUserConfigurationURL(ThemeDisplay themeDisplay) {
		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPortalURL());
		sb.append("/o/change-tracking-legacy/configurations/");
		sb.append(themeDisplay.getCompanyId());
		sb.append("/user/");
		sb.append(themeDisplay.getUserId());

		return sb.toString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}