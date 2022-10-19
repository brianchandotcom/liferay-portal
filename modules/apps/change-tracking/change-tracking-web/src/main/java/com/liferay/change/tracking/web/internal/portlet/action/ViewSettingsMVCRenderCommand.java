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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.PublicationsConfigurationDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_settings"
	},
	service = MVCRenderCommand.class
)
public class ViewSettingsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			_portletPermission.check(
				PermissionThreadLocal.getPermissionChecker(),
				CTPortletKeys.PUBLICATIONS, ActionKeys.CONFIGURATION);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		PublicationsConfigurationDisplayContext
			publicationsConfigurationDisplayContext =
				new PublicationsConfigurationDisplayContext(
					_ctSettingsConfigurationHelper,
					_portal.getHttpServletRequest(renderRequest),
					renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.PUBLICATIONS_CONFIGURATION_DISPLAY_CONTEXT,
			publicationsConfigurationDisplayContext);

		return "/publications/view_settings.jsp";
	}

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

}