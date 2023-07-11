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

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.document.library.kernel.util.AudioConverter;
import com.liferay.document.library.kernel.util.VideoConverter;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.PortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + PortletKeys.SERVER_ADMIN, "mvc.command.name=/",
		"mvc.command.name=/server_admin/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			AudioConverter.class.getName(), _audioConverter);
		renderRequest.setAttribute(
			VideoConverter.class.getName(), _videoConverter);

		return "/view.jsp";
	}

	@Reference
	private AudioConverter _audioConverter;

	@Reference
	private VideoConverter _videoConverter;

}