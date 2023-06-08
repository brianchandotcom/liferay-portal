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

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTPermission;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
	property = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
	service = PortletConfigurationIcon.class
)
public class CTRemotesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "remote-servers");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				portletRequest, CTPortletKeys.PUBLICATIONS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/change_tracking/view_ct_remotes"
		).buildString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return CTPermission.contains(
			PermissionThreadLocal.getPermissionChecker(),
			CTActionKeys.ADD_REMOTE);
	}

	@Reference
	private Language _language;

	@Reference
	private PortletPermission _portletPermission;

}