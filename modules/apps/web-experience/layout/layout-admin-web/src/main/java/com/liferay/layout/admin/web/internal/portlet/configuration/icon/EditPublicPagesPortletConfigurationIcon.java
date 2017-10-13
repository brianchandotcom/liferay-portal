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

package com.liferay.layout.admin.web.internal.portlet.configuration.icon;

import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.display.context.ViewLayoutsDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + LayoutAdminPortletKeys.VIEW_LAYOUTS},
	service = PortletConfigurationIcon.class
)
public class EditPublicPagesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "configure");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(portletRequest);

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		ViewLayoutsDisplayContext viewLayoutsDisplayContext =
			new ViewLayoutsDisplayContext(
				liferayPortletRequest, liferayPortletResponse);

		PortletURL editLayoutURL = viewLayoutsDisplayContext.getEditLayoutURL(
			LayoutConstants.DEFAULT_PLID, false);

		editLayoutURL.setParameter("backURL", themeDisplay.getURLCurrent());

		return editLayoutURL.toString();
	}

	@Override
	public double getWeight() {
		return 100.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(portletRequest);

		ViewLayoutsDisplayContext viewLayoutsDisplayContext =
			new ViewLayoutsDisplayContext(liferayPortletRequest, null);

		if (viewLayoutsDisplayContext.isPrivatePages()) {
			return false;
		}

		if (!viewLayoutsDisplayContext.isShowPublicPages()) {
			return false;
		}

		try {
			return GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				viewLayoutsDisplayContext.getSelGroupId(),
				ActionKeys.MANAGE_LAYOUTS);
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Reference
	private Portal _portal;

}