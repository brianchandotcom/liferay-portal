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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.exception.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/view_message"
	},
	service = MVCRenderCommand.class
)
public class ViewMessageMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				renderRequest);

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY, messageDisplay);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			if (MBPortletKeys.MESSAGE_BOARDS_ADMIN.equals(
					portletDisplay.getId())) {

				return "/message_boards_admin/view_message.jsp";
			}
			else {
				return "/message_boards/view_message.jsp";
			}
		}
		catch (NoSuchMessageException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/message_boards/error.jsp";
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

}