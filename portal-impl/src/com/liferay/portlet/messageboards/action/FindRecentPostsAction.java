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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS_ADMIN,
		"path=/message_boards/find_recent_posts"
	},
	service = StrutsAction.class
)
public class FindRecentPostsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.MESSAGE_BOARDS, plid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view");
			portletURL.setParameter("tabs2", "recent-posts");
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(WindowState.NORMAL);

			response.sendRedirect(portletURL.toString());

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

}