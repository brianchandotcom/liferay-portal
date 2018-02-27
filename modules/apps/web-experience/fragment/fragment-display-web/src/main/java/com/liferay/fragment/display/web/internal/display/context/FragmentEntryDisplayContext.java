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

package com.liferay.fragment.display.web.internal.display.context;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryDisplayContext {

	public FragmentEntryDisplayContext(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortletPreferences portletPreferences,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_portletPreferences = portletPreferences;

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	public FragmentEntryLink getFragmentEntryLink() {
		return _fragmentEntryLinkLocalService.fetchFragmentEntryLink(
			getFragmentEntryLinkId());
	}

	public long getFragmentEntryLinkId() {
		if (_fragmentEntryLinkId == null) {
			_fragmentEntryLinkId = PrefsParamUtil.getLong(
				_portletPreferences, _portletRequest, "fragmentEntryLinkId");
		}

		return _fragmentEntryLinkId;
	}

	public boolean isShowConfigurationLink() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);
	}

	private Long _fragmentEntryLinkId;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;

}