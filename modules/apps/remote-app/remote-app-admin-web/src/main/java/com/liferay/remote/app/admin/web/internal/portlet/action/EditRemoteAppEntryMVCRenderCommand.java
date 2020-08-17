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

package com.liferay.remote.app.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminWebKeys;
import com.liferay.remote.app.admin.web.internal.display.context.RemoteAppAdminDisplayContext;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/edit_remote_app_entry"
	},
	service = MVCRenderCommand.class
)
public class EditRemoteAppEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			RemoteAppAdminWebKeys.REMOTE_APP_ADMIN_DISPLAY_CONTEXT,
			new RemoteAppAdminDisplayContext(
				renderRequest, renderResponse, _remoteAppEntryLocalService));

		try {
			long remoteAppEntryId = ParamUtil.getLong(
				renderRequest, "remoteAppEntryId");

			if (remoteAppEntryId > 0) {
				renderRequest.setAttribute(
					RemoteAppAdminWebKeys.REMOTE_APP_ENTRY,
					_remoteAppEntryLocalService.getRemoteAppEntry(
						remoteAppEntryId));
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException) {
				SessionErrors.add(renderRequest, exception.getClass());

				return "/admin/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/admin/edit_remote_app_entry.jsp";
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}