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

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 */
public class EditObjectEntryMVCRenderCommand implements MVCRenderCommand {

	public EditObjectEntryMVCRenderCommand(
		ObjectEntryDisplayContextFactory objectEntryDisplayContextFactory,
		Portal portal) {

		_objectEntryDisplayContextFactory = objectEntryDisplayContextFactory;
		_portal = portal;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			_objectEntryDisplayContextFactory.create(
				_portal.getHttpServletRequest(renderRequest), false));

		return "/object_entries/edit_object_entry.jsp";
	}

	private final ObjectEntryDisplayContextFactory
		_objectEntryDisplayContextFactory;
	private final Portal _portal;

}