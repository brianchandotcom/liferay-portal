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

package com.liferay.message.boards.web.internal.struts;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"path=/message_boards/find_recent_posts"
	},
	service = StrutsAction.class
)
public class FindRecentPostsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(httpServletRequest, "p_l_id");

			httpServletResponse.sendRedirect(
				PortletURLBuilder.create(
					PortletURLFactoryUtil.create(
						httpServletRequest, MBPortletKeys.MESSAGE_BOARDS, plid,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/message_boards/view_recent_posts"
				).setPortletMode(
					PortletMode.VIEW
				).setWindowState(
					WindowState.NORMAL
				).buildString());

			return null;
		}
		catch (Exception exception) {
			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	@Reference
	private Portal _portal;

}