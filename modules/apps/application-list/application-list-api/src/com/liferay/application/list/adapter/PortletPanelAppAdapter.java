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

package com.liferay.application.list.adapter;

import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.PortletConfigFactoryUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PortletPanelAppAdapter implements PanelApp {

	public PortletPanelAppAdapter(String portletId) {
		_portletId = portletId;
	}

	@Override
	public String getKey() {
		return "portlet-adapter-" + getPortletId();
	}

	@Override
	public String getLabel(Locale locale) {
		PortletConfig portletConfig = PortletConfigFactoryUtil.get(
			getPortletId());

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		Portlet portlet = getPortlet();

		String key =
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				portlet.getPortletName();

		String value = LanguageUtil.get(resourceBundle, key);

		if (!key.equals(value)) {
			return value;
		}

		value = LanguageUtil.get(locale, key);

		if (!key.equals(value)) {
			return value;
		}

		String displayName = portlet.getDisplayName();

		if (!displayName.equals(portlet.getPortletName())) {
			return displayName;
		}

		return key;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortalUtil.getControlPanelPortletURL(
			request, getPortletId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		try {
			Portlet portlet = getPortlet();

			ControlPanelEntry controlPanelEntry =
				portlet.getControlPanelEntryInstance();

			return controlPanelEntry.hasAccessPermission(
				permissionChecker, group, portlet);
		}
		catch (PortalException | RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected Portlet getPortlet() {
		return PortletLocalServiceUtil.getPortletById(getPortletId());
	}

	private final String _portletId;

}