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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a skeletal implementation of the {@link PanelApp} to minimize the
 * effort required to implement this interface.
 *
 * <p>
 * To implement an application, this class should be extended and {@link
 * #include(HttpServletRequest, HttpServletResponse)} should be overridden. The
 * <code>include</code> override method should return <code>true</code> when the
 * application view successfully renders and <code>false</code> otherwise.
 * </p>
 *
 * @author Adolfo Pérez
 * @see    PanelApp
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

		return _userNotificationEventLocalService.
			getUserNotificationEventsCount(
				user.getUserId(), _portlet.getPortletId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, getGroup(httpServletRequest), getPortletId(), 0,
			0, PortletRequest.RENDER_PHASE);

		Group group = groupProvider.getGroup(httpServletRequest);

		if (group == null) {
			return portletURL;
		}

		portletURL.setParameter(
			"p_v_l_s_g_id", String.valueOf(group.getGroupId()));

		return portletURL;
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return false;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		Portlet portlet = _portletLocalService.getPortletById(
			group.getCompanyId(), getPortletId());

		if (!portlet.isActive()) {
			return false;
		}

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
	public void setGroupProvider(GroupProvider groupProvider) {
		this.groupProvider = groupProvider;
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

	protected Group getGroup(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (!group.isControlPanel()) {
			return null;
		}

		Portlet portlet = getPortlet();

		String controlPanelEntryCategory =
			portlet.getControlPanelEntryCategory();

		if (Validator.isNull(controlPanelEntryCategory) ||
			!controlPanelEntryCategory.startsWith(
				PortletCategoryKeys.SITE_ADMINISTRATION)) {

			return null;
		}

		if (groupProvider == null) {
			return null;
		}

		return groupProvider.getGroup(httpServletRequest);
	}

	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	protected GroupProvider groupProvider;

	private Portlet _portlet;
	private PortletLocalService _portletLocalService;
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}