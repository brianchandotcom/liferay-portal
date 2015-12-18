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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.message.boards.web.constants.MBWebKeys;
import com.liferay.message.boards.web.portlet.toolbar.contributor.MBPortletToolbarContributor;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/message_boards/view"
	},
	service = MVCRenderCommand.class
)
public class MBAdminViewMVCRenderCommand extends BaseViewMVCRenderCommand {

	public MBAdminViewMVCRenderCommand() {
		super("/message_boards_admin/view.jsp");
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			MBWebKeys.MESSAGE_BOARDS_PORTLET_TOOLBAR_CONTRIBUTOR,
			_mbPortletToolbarContributor);

		return super.render(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setMBPortletToolbarContributor(
		MBPortletToolbarContributor mbPortletToolbarContributor) {

		_mbPortletToolbarContributor = mbPortletToolbarContributor;
	}

	private volatile MBPortletToolbarContributor _mbPortletToolbarContributor;

}