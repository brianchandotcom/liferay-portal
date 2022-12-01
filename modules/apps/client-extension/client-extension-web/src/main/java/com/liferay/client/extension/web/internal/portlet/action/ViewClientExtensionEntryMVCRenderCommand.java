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

package com.liferay.client.extension.web.internal.portlet.action;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminPortletKeys;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebKeys;
import com.liferay.client.extension.web.internal.display.context.ViewClientExtensionEntryDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
		"javax.portlet.name=" + ClientExtensionAdminPortletKeys.CLIENT_EXTENSION_ADMIN,
		"mvc.command.name=/client_extension_admin/view_client_extension_entry"
	},
	service = MVCRenderCommand.class
)
public class ViewClientExtensionEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CET cet = _cetManager.getCET(
				_portal.getCompanyId(renderRequest),
				ParamUtil.getString(renderRequest, "externalReferenceCode"));

			renderRequest.setAttribute(
				ClientExtensionAdminWebKeys.
					VIEW_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT,
				new ViewClientExtensionEntryDisplayContext(cet, renderRequest));

			return "/admin/view_client_extension_entry.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private CETManager _cetManager;

	@Reference
	private Portal _portal;

}