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
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserNotificationEventLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ControlPanelEntry;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class BasePanelApp implements PanelApp {

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
	public int getNotificationsCount(User user) {
		if (_userNotificationEventLocalService == null) {
			return 0;
		}

		return _userNotificationEventLocalService.getUserNotificationEventCount(
			user.getUserId(), _portlet.getPortletId(),
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		return PortalUtil.getControlPanelPortletURL(
			request, getGroup(request), getPortletId(), 0, 0,
			PortletRequest.RENDER_PHASE);
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		try {
			ControlPanelEntry controlPanelEntry = getControlPanelEntry();

			if (controlPanelEntry == null) {
				return true;
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

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return false;
	}

	@Override
	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	protected ControlPanelEntry getControlPanelEntry() {
		Portlet portlet = getPortlet();

		if (portlet == null) {
			return null;
		}

		return portlet.getControlPanelEntryInstance();
	}

	protected Group getGroup(HttpServletRequest request) {
		return null;
	}

	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	private Portlet _portlet;
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}