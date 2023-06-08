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

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTRemoteService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.ViewCTRemotesDisplayContext;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTPermission;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_ct_remotes"
	},
	service = MVCRenderCommand.class
)
public class ViewCTRemotesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		if (!CTPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				CTActionKeys.ADD_REMOTE)) {

			return null;
		}

		ViewCTRemotesDisplayContext viewCTRemotesDisplayContext =
			new ViewCTRemotesDisplayContext(
				_portal.getHttpServletRequest(renderRequest), _language,
				_portal, renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.VIEW_CT_REMOTES_DISPLAY_CONTEXT,
			viewCTRemotesDisplayContext);

		return "/publications/view_ct_remotes.jsp";
	}

	@Reference
	private CTRemoteService _ctRemoteService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

}