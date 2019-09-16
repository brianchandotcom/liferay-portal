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

package com.liferay.product.navigation.personal.menu.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfiguration;
import com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfigurationTracker;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Samuel Trong Tran
 */
public class PersonalApplicationURLUtil {

	public static String getPersonalApplicationURL(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		User user = PortalUtil.getUser(httpServletRequest);

		Group group = user.getGroup();

		boolean controlPanelLayout = false;
		boolean privateLayout = true;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PersonalMenuConfiguration personalMenuConfiguration =
			_getPersonalMenuConfigurationTracker().
				getCompanyPersonalMenuConfiguration(
					themeDisplay.getCompanyId());

		String personalApplicationsLookAndFeel =
			personalMenuConfiguration.personalApplicationsLookAndFeel();

		if (personalApplicationsLookAndFeel.equals("current-site")) {
			group = GroupLocalServiceUtil.getGroup(
				PortalUtil.getScopeGroupId(httpServletRequest));

			if (group.isStagingGroup()) {
				group = group.getLiveGroup();
			}

			Layout currentLayout = themeDisplay.getLayout();

			if (currentLayout.isTypeControlPanel()) {
				controlPanelLayout = true;
			}

			if (currentLayout.isPublicLayout()) {
				privateLayout = false;
			}

			user = UserLocalServiceUtil.getDefaultUser(
				themeDisplay.getCompanyId());
		}

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				group.getGroupId(), privateLayout,
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);
		}
		catch (NoSuchLayoutException nsle) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsle, nsle);
			}

			layout = _addEmbeddedPersonalApplicationLayout(
				user.getUserId(), group.getGroupId(), privateLayout);
		}

		if ((controlPanelLayout && !group.isControlPanel()) ||
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout, true,
				ActionKeys.VIEW)) {

			Group controlPanelGroup = themeDisplay.getControlPanelGroup();

			layout = new VirtualLayout(
				LayoutLocalServiceUtil.getFriendlyURLLayout(
					controlPanelGroup.getGroupId(), privateLayout,
					PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL),
				themeDisplay.getScopeGroup());
		}

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			httpServletRequest, portletId, layout, PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private static Layout _addEmbeddedPersonalApplicationLayout(
			long userId, long groupId, boolean privateLayout)
		throws PortalException {

		String friendlyURL = FriendlyURLNormalizerUtil.normalize(
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			userId, groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.CONTROL_PANEL_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, true, true,
			friendlyURL, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			userId, "1_column_dynamic", false);

		return LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	private static PersonalMenuConfigurationTracker
		_getPersonalMenuConfigurationTracker() {

		return _serviceTracker.getService();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersonalApplicationURLUtil.class);

	private static final ServiceTracker
		<PersonalMenuConfigurationTracker, PersonalMenuConfigurationTracker>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PersonalMenuConfigurationTracker.class);

		ServiceTracker
			<PersonalMenuConfigurationTracker, PersonalMenuConfigurationTracker>
				serviceTracker = new ServiceTracker<>(
					bundle.getBundleContext(),
					PersonalMenuConfigurationTracker.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}