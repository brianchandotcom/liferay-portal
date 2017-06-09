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

package com.liferay.staging.processes.web.internal.portlet.configuration.icon;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + StagingProcessesPortletKeys.STAGING_PROCESSES},
	service = PortletConfigurationIcon.class
)
public class StagingConfigurationPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "staging-configuration");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, StagingProcessesPortletKeys.STAGING_PROCESSES,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		portletURL.setParameter(
			"showStagingConfiguration", Boolean.TRUE.toString());

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 101.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		boolean showStagingConfiguration = GetterUtil.getBoolean(
			portletRequest.getParameter("showStagingConfiguration"));

		if (showStagingConfiguration) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (!group.isStaged()) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		Group liveGroup = _staging.getLiveGroup(group.getGroupId());

		try {
			return GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), liveGroup,
				ActionKeys.MANAGE_STAGING);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingConfigurationPortletConfigurationIcon.class);

	@Reference
	private Portal _portal;

	@Reference
	private Staging _staging;

}