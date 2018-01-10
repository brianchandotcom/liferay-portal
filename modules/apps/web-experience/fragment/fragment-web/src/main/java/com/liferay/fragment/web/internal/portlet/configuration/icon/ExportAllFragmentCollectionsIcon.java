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

package com.liferay.fragment.web.internal.portlet.configuration.icon;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT},
	service = PortletConfigurationIcon.class
)
public class ExportAllFragmentCollectionsIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return LanguageUtil.get(themeDisplay.getLocale(), "export");
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)_portal.getControlPanelPortletURL(
				portletRequest, FragmentPortletKeys.FRAGMENT,
				PortletRequest.RESOURCE_PHASE);

		liferayPortletURL.setResourceID(
			"/fragment/export_all_fragment_collections");

		return liferayPortletURL.toString();
	}

	@Override
	public double getWeight() {
		return 1;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	@Reference
	private Portal _portal;

}