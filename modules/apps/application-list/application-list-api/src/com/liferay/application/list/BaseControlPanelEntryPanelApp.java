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

package com.liferay.application.list;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseControlPanelEntryPanelApp implements PanelApp {

	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				getPortletId());
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, getPortletId(), themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL;
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		try {
			ControlPanelEntry controlPanelEntry = getControlPanelEntry();

			if (controlPanelEntry == null) {
				return false;
			}

			return controlPanelEntry.hasAccessPermission(
				permissionChecker, group, getPortlet());
		}
		catch (PortalException | RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected ControlPanelEntry getControlPanelEntry() {
		Portlet portlet = getPortlet();

		return portlet.getControlPanelEntryInstance();
	}

	protected Portlet getPortlet() {
		return _portletLocalService.getPortletById(getPortletId());
	}

	protected PortletLocalService _portletLocalService;

}