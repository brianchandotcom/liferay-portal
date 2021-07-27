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
import com.liferay.remote.app.admin.web.internal.display.context.RemoteCustomElementAdminDisplayContext;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.service.RemoteCustomElementEntryLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_CUSTOM_ELEMENT_ADMIN,
		"mvc.command.name=/remote_custom_element_admin/edit_remote_custom_element_entry"
	},
	service = MVCRenderCommand.class
)
public class EditRemoteCustomElementEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			RemoteAppAdminWebKeys.REMOTE_CUSTOM_ELEMENT_ADMIN_DISPLAY_CONTEXT,
			new RemoteCustomElementAdminDisplayContext(
				renderRequest, renderResponse,
				_remoteCustomElementEntryLocalService));

		try {
			long remoteCustomElementEntryId = ParamUtil.getLong(
				renderRequest, "remoteCustomElementEntryId");

			if (remoteCustomElementEntryId > 0) {
				renderRequest.setAttribute(
					RemoteAppAdminWebKeys.REMOTE_CUSTOM_ELEMENT_ENTRY,
					_remoteCustomElementEntryLocalService.
						getRemoteCustomElementEntry(
							remoteCustomElementEntryId));
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException) {
				SessionErrors.add(renderRequest, exception.getClass());

				return "/remote_custom_element_admin/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/remote_custom_element_admin" +
			"/edit_remote_custom_element_entry.jsp";
	}

	@Reference
	private RemoteCustomElementEntryLocalService
		_remoteCustomElementEntryLocalService;

}