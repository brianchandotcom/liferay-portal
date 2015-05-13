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

package com.liferay.control.panel.menu.web.portlet.provider;

import com.liferay.control.panel.menu.web.constants.ControlPanelMenuPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.admin.util.PortalAdministrationApplicationType;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=" + PortalAdministrationApplicationType.SiteAdmin.CLASS_NAME
	},
	service = ViewPortletProvider.class
)
public class ControlPanelMenuPortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletId() {
		return ControlPanelMenuPortletKeys.CONTROL_PANEL_MENU;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long companyId = themeDisplay.getCompanyId();

			Group userPersonalPanelGroup = _groupLocalService.getGroup(
				companyId, GroupConstants.USER_PERSONAL_PANEL);

			long plid = _layoutLocalService.getDefaultPlid(
				userPersonalPanelGroup.getGroupId(), true);

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, PortletKeys.MY_ACCOUNT, plid,
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL;
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private Portal _portal;

}