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

package com.liferay.staging.configuration.web.internal.portlet.configuration.icon;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.constants.StagingConfigurationPortletKeys;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIcon.class)
public class StagingPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/staging.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "staging");
	}

	@Override
	public double getWeight() {
		return 16.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		if (group.hasLocalOrRemoteStagingGroup() &&
			!PropsValues.STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED) {

			return false;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String rootPortletId = portletDisplay.getRootPortletId();

		if (rootPortletId.equals(ExportImportPortletKeys.EXPORT) ||
			rootPortletId.equals(ExportImportPortletKeys.EXPORT_IMPORT) ||
			rootPortletId.equals(ExportImportPortletKeys.IMPORT) ||
			rootPortletId.equals(
				StagingConfigurationPortletKeys.STAGING_CONFIGURATION) ||
			rootPortletId.equals(
				StagingProcessesPortletKeys.STAGING_PROCESSES) ||
			!portletDisplay.isShowStagingIcon()) {

			return false;
		}

		Portlet portlet = _portletLocalService.getPortletById(
			portletDisplay.getId());

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if ((portletDataHandler == null) ||
			!portletDataHandler.isConfigurationEnabled()) {

			return false;
		}

		try {
			return _groupPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(), ActionKeys.PUBLISH_PORTLET_INFO);
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingPortletConfigurationIcon.class);

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private Language _language;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.staging.configuration.web)"
	)
	private ServletContext _servletContext;

}