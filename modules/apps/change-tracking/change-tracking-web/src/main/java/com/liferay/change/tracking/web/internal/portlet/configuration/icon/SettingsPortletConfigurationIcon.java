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

package com.liferay.change.tracking.web.internal.portlet.configuration.icon;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
	service = PortletConfigurationIcon.class
)
public class SettingsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(getLocale(portletRequest), "settings");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				portletRequest, CTPortletKeys.PUBLICATIONS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/change_tracking/view_settings"
		).buildString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			return _portletPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				CTPortletKeys.PUBLICATIONS, ActionKeys.CONFIGURATION);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsPortletConfigurationIcon.class);

	@Reference
	private PortletPermission _portletPermission;

}