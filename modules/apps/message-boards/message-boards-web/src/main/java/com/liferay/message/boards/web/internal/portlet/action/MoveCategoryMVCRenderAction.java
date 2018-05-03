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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/move_category"
	},
	service = MVCRenderCommand.class
)
public class MoveCategoryMVCRenderAction implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			MBCategory category = ActionUtil.getCategory(renderRequest);

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_CATEGORY, category);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return "/message_boards/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/message_boards/move_category.jsp";
	}

}