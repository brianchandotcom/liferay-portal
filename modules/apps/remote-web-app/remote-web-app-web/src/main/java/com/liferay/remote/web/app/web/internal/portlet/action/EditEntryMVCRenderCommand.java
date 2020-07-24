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

package com.liferay.remote.web.app.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.remote.web.app.exception.NoSuchEntryException;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.service.RemoteWebAppEntryLocalService;
import com.liferay.remote.web.app.web.internal.constants.RemoteWebAppPortletKeys;
import com.liferay.remote.web.app.web.internal.constants.RemoteWebAppWebKeys;
import com.liferay.remote.web.app.web.internal.display.context.RemoteWebAppAdminDataSetDisplayContext;

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
		"javax.portlet.name=" + RemoteWebAppPortletKeys.REMOTE_WEB_APP_ADMIN,
		"mvc.command.name=/edit_entry"
	},
	service = MVCRenderCommand.class
)
public class EditEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			"remoteWebAppAdminDataSetDisplayContext",
			new RemoteWebAppAdminDataSetDisplayContext(
				renderRequest, renderResponse, _remoteWebAppEntryLocalService));

		try {
			long entryId = ParamUtil.getLong(renderRequest, "entryId");

			if (entryId > 0) {
				RemoteWebAppEntry remoteWebAppEntry =
					_remoteWebAppEntryLocalService.getRemoteWebAppEntry(
						entryId);

				renderRequest.setAttribute(
					RemoteWebAppWebKeys.REMOTE_WEB_APP_ENTRY,
					remoteWebAppEntry);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException) {
				SessionErrors.add(renderRequest, exception.getClass());

				return "/admin/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/admin/edit_entry.jsp";
	}

	@Reference
	private RemoteWebAppEntryLocalService _remoteWebAppEntryLocalService;

}