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

package com.liferay.change.tracking.change.lists.indicator.web.internal.display.context;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 */
public class ChangeListsIndicatorDisplayContext {

	public ChangeListsIndicatorDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SoyContext getChangeListsIndicatorContext() {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, CTPortletKeys.CHANGE_LISTS,
			PortletRequest.RENDER_PHASE);

		soyContext.put(
			"urlChangeListsOverview", portletURL.toString()
		).put(
			"urlCollectionsBase",
			_themeDisplay.getPortalURL() + "/o/change-tracking/collections"
		).put(
			"urlProductionInformation",
			StringBundler.concat(
				_themeDisplay.getPortalURL(),
				"/o/change-tracking/processes?companyId=",
				_themeDisplay.getCompanyId(), "&published=true")
		);

		return soyContext;
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}