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

package com.liferay.segments.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.EditPortletProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsResourcePermission;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.segments.model.SegmentsEntry",
	service = EditPortletProvider.class
)
public class SegmentsEntryEditPortletProvider
	extends BasePortletProvider implements EditPortletProvider {

	@Override
	public String getPortletName() {
		return SegmentsPortletKeys.SEGMENTS;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!SegmentsResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES)) {

			return null;
		}

		PortletURL portletURL = super.getPortletURL(httpServletRequest);

		portletURL.setParameter("mvcRenderCommandName", "editSegmentsEntry");

		return portletURL;
	}

}